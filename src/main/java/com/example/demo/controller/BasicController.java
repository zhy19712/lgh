package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.ServerConfig;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "管养/设施--基础信息（流域、街道、湿地、航飞、摄像头）")
@RestController
@RequestMapping("/api/basic")
public class BasicController {

    @Autowired
    private RiverMapper riverMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private WetlandMapper wetlandMapper;

    @Autowired
    private WetlandRtMapper wetlandRtMapper;

    @Autowired
    private CameraMapper cameraMapper;

    @Autowired
    private ServerConfig serverConfig;

    @ApiOperation(value = "航飞点位", notes = "返回所选月份的航飞点位列表", produces = "application/json")
    @RequestMapping(value = "/getflightpoint", method = RequestMethod.POST)
    @ApiImplicitParam(name = "time", value = "年月，例如\"2020-10\", 不输入参数的话默认当前月份", paramType = "query")
    public JSONObject getFlightPoint(String time) {
        String date;
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (time == null || time.equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            date = df.format(new Date());
        } else {
            date = time;
        }
        List<Flight> pointList = flightMapper.selectPointByMonth(date);
        for (Flight point : pointList) {
            JSONObject jo = new JSONObject();
            JSONArray location = new JSONArray();
            location.add(point.getLon());
            location.add(point.getLat());
            jo.put("point", point.getPoint());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "航飞视频", notes = "返回所选月份所选航飞点位的视频列表", produces = "application/json")
    @RequestMapping(value = "/getflightvideo", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询条件(1.有point有time，返回所选月份所属点位的航飞视频   2.有point无time,返回所选点位本月份的航飞视频  3.无point有time,返回所选月份全部点位的所有视频)    {\"point\":\"A\",\"time\":\"2020-11\"}", paramType = "body", required = true)
    public JSONObject getFlightVideo(@RequestBody JSONObject condition) {
        String date;
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String server = serverConfig.getUrl();
        if (condition.get("point") != null && !condition.getString("point").equals("")) {
            if (condition.get("time") != null && !condition.getString("time").equals("")) {
                date = condition.getString("time");
            } else {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
                date = df.format(new Date());
            }
            List<Flight> videoList = flightMapper.selectVideoByPointAndMonth(condition.getString("point"), date);
            for (Flight video : videoList) {
                JSONObject jo = new JSONObject();
                jo.put("id", video.getId());
                jo.put("point", video.getPoint());
                jo.put("time", video.getTime());
                try {
                    JSONArray path =  JSONArray.parseArray(video.getPath());
                    for(int i=0; i<path.size();i++){
                        path.set(i, server + path.get(i));
                    }
                    jo.put("path", path);
                } catch (Exception e) {
                    System.out.println(video.getId()+"数据格式错误");
                }
                jsonArray.add(jo);
            }
        } else {
            if (condition.get("time") != null && !condition.getString("time").equals("")) {
                date = condition.getString("time");
                List<Flight> videoList = flightMapper.selectVideoByMonth(date);
                for (Flight video : videoList) {
                    JSONObject jo = new JSONObject();
                    jo.put("id", video.getId());
                    jo.put("point", video.getPoint());
                    jo.put("time", video.getTime());
                    try {
                        JSONArray path =  JSONArray.parseArray(video.getPath());
                        for(int i=0; i<path.size();i++){
                            path.set(i, server + path.get(i));
                        }
                        jo.put("path", path);
                    } catch (Exception e) {
                        System.out.println(video.getId()+"数据格式错误");
                    }
                    jsonArray.add(jo);
                }
            }else{
                jsonArray.add("必须至少有一个查询条件");
            }
        }

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "流域范围", notes = "返回流域范围", produces = "application/json")
    @GetMapping("/basin")
    public JSONObject basin() {
        River basin = riverMapper.selectByName("流域");
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (basin != null) {
            JSONObject jo = new JSONObject();
            jo.put("id", basin.getId());
            jo.put("name", basin.getName());
            jo.put("location", JSONArray.parseArray(basin.getLocation()));
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "湿地--详情", notes = "所选湿地详细信息", produces = "application/json")
    @RequestMapping(value = "/wetlanddetail", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "湿地名称", paramType = "query", required = true)
    public JSONObject wetlandDetail(String name) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Wetland wetland = wetlandMapper.selectByName(name);
        if(wetland != null){
            JSONObject jo = new JSONObject();
            jo.put("name", wetland.getName());
            jo.put("standard", wetland.getStandard());
            jo.put("source", wetland.getSource());
            jo.put("craft", wetland.getCraft());
            jo.put("builder", wetland.getBuilder());
            jo.put("manager", wetland.getManager());
            jo.put("time", wetland.getTime());
            jo.put("water_need", wetland.getWaterNeed());
            jo.put("type", wetland.getType());
            jo.put("project", wetland.getProject());
            jsonArray.add(jo);
        }

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;

    }

    @ApiOperation(value = "湿地统计信息", notes = "湿地统计信息", produces = "application/json")
    @GetMapping("/wetlandstatistics")
    public JSONObject wetlandStatistics() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int count = wetlandMapper.selectCount();
        double standard = wetlandMapper.selectSumStandard();
        double flowIn = 0.0;
        WetlandRt wetlandRt = wetlandRtMapper.selectNewestFlowIn();
        if(wetlandRt != null){
            flowIn = wetlandRt.getFlowIn();
        }
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        jo.put("standard", standard);
        jo.put("flow_in", flowIn);
        jsonArray.add(jo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "湿地实时监测数据", notes = "湿地实时监测数据", produces = "application/json")
    @GetMapping("/wetlandrt")
    public JSONObject wetlandRt() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jo = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        jo.put("time", df.format(new Date()));
        jo.put("an", 0.79);
        jo.put("flow_in", 1.6);
        jo.put("pho", 0.1);
        jo.put("cod", 30);
        jsonArray.add(jo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "摄像头--属性", notes = "返回所选摄像头属性信息", produces = "application/json")
    @RequestMapping(value = "/cameralSingel", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject cameraSingle(String name) {
        Camera camera = cameraMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        if(camera !=null) {
            JSONObject jo = new JSONObject();
            jo.put("id", camera.getId());
            jo.put("name", camera.getName());
            jo.put("status", camera.getStatus());
            jo.put("inspection", camera.getInspection());
            jo.put("inspection_location", camera.getInspectionLocation());
            jo.put("time", camera.getTime());
            jo.put("station_location", camera.getStationLocation());
            jo.put("communication", camera.getCommunication());
            jo.put("producer", camera.getProducer());
            jo.put("model", camera.getModel());
            jo.put("system", camera.getSystem());
            jo.put("scene", camera.getScene());
            jo.put("district", camera.getDistrict());
            jo.put("manager", camera.getManager());
            jo.put("person", camera.getPerson());
            jo.put("remark", camera.getRemark());
            jo.put("tele", camera.getTele());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }



}
