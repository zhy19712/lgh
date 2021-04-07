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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "管养/设施--设施信息（闸坝、泵站、涉河施工、水质净化厂）")
@RestController
@RequestMapping("/api/construction")
public class ConstructionController {
    @Autowired
    private DamMapper damMapper;

    @Autowired
    private ConstructionMapper constructionMapper;

    @Autowired
    private OpenningMapper openningMapper;

    @Autowired
    private PumprtMapper pumprtMapper;

    @Autowired
    private PumpMapper pumpMapper;

    @Autowired
    private WaterClearMapper waterClearMapper;

    @Autowired
    private WaterClearRtMapper waterClearRtMapper;

    @Autowired
    private ServerConfig serverConfig;


    @ApiOperation(value = "涉河施工项目--总览", notes = "加载所有施工项目的名称和坐标", produces = "application/json")
    @GetMapping("/constructionall")
    public JSONObject constructionAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Construction> constructionList = constructionMapper.selectAll();
        for (Construction list : constructionList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            JSONArray location = JSONArray.parseArray(list.getLocation());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "涉河施工项目--详细", notes = "返回所选施工项目的相关信息，返回照片路径", produces = "application/json")
    @RequestMapping(value = "/constructiondetail", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject constructionDetail(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        String server = serverConfig.getUrl();
        List<Construction> constructionList = constructionMapper.selectByName(name);
        JSONObject jo = new JSONObject();
        jo.put("id", constructionList.get(0).getId());
        jo.put("name", constructionList.get(0).getName());
        jo.put("duration", constructionList.get(0).getDuration());
        jo.put("owner", constructionList.get(0).getConstructor());
        jo.put("builder", constructionList.get(0).getBuilder());
        jo.put("detail", constructionList.get(0).getDetail());
        try{
            JSONArray location = JSONArray.parseArray(constructionList.get(0).getLocation());
            jo.put("location", location);
        }catch (Exception e){
            System.out.println(constructionList.get(0).getId()+"数据格式错误");
        }

        JSONArray pics = new JSONArray();
        for (Construction list : constructionList) {
            JSONObject pic = new JSONObject();
            pic.put("time", list.getTime());
            try {
                JSONArray before =  JSONArray.parseArray(list.getPicBefore());
                for(int i=0; i<before.size();i++){
                    before.set(i, server + before.get(i));
                }
                pic.put("pic_before", before);
            } catch (Exception e) {
                System.out.println(list.getId()+"数据格式错误");
            }
            pics.add(pic);
        }
        jo.put("photo", pics);
        jsonArray.add(jo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }



    @ApiOperation(value = "闸坝信息--按名称查询", notes = "返回所选闸坝的信息", produces = "application/json")
    @RequestMapping(value = "/dambyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选闸坝名", paramType = "query", required = true, dataType = "String")
    public JSONObject damByName(String name) {
        Dam dam = damMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        if(dam !=null ){
            JSONObject jo = new JSONObject();
            jo.put("id", dam.getId());
            jo.put("name", dam.getName());
            jo.put("postation", dam.getPostation());
            jo.put("height", dam.getHeight());
            JSONArray location = JSONArray.parseArray(dam.getLocation());
            jo.put("location", location);
            jo.put("length", dam.getLength());
            jo.put("elevation", dam.getElevation());
            jo.put("type", dam.getType());
            jo.put("degre", dam.getDegre());
            jo.put("building", dam.getBuilding());
            jo.put("retain", dam.getRetain());
            jo.put("flush", dam.getFlush());
            jo.put("fill", dam.getFill());
            jo.put("run", dam.getRun());
            jo.put("start", dam.getStart());
            jo.put("finish", dam.getFinish());
            jo.put("dispatch", dam.getDispatch());
            jo.put("manager", dam.getManager());
            jo.put("arriving", dam.getArriving());
            jo.put("status", dam.getStatus());
            jsonArray.add(jo);
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "闸坝信息--统计", notes = "返回闸坝统计信息", produces = "application/json")
    @GetMapping("/damstatistics")
    public JSONObject damStatistics() {
        int paituishuizha = damMapper.selectCount("闸门");
        int xiangjiaoba = damMapper.selectCount("橡胶坝");

        int paituishuizhaOpen = damMapper.selectStatusCount("闸门", 1);
        int xiangjiaobaOpen = damMapper.selectStatusCount("橡胶坝", 1);

        int paituishuizhaArriving = damMapper.selectArrivingCount("闸门", 1);
        int xiangjiaobaArriving = damMapper.selectArrivingCount("橡胶坝", 1);

        JSONArray jsonArray = new JSONArray();

        JSONObject jooo = new JSONObject();
        jooo.put("count", paituishuizha);
        jooo.put("arriving", paituishuizhaArriving);
        jooo.put("open", paituishuizhaOpen);
        jooo.put("name", "闸门");
        jsonArray.add(jooo);

        JSONObject joooo = new JSONObject();
        joooo.put("count", xiangjiaoba);
        joooo.put("arriving", xiangjiaobaArriving);
        joooo.put("open", xiangjiaobaOpen);
        joooo.put("name", "橡胶坝");
        jsonArray.add(joooo);

        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水位-开度-流量曲线", notes = "获取所有水位、开度、流量信息", produces = "application/json")
    @RequestMapping(value = "/levelopenningflow", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "闸坝名称", paramType = "query", required = true, dataType = "String")
    public JSONObject levelOpenningFlow(String name) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Openning> openningList = openningMapper.selectByName("新圳河水闸");
        for (Openning list : openningList) {
            JSONObject jo = new JSONObject();
            jo.put("name", list.getName());
            jo.put("level", list.getLevel());
            jo.put("openning", list.getOpenning());
            jo.put("flow", list.getFlow());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "泵站--详细", notes = "返回所选泵站的基础信息", produces = "application/json")
    @RequestMapping(value = "/pumpbyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "泵站名称", paramType = "query", required = true, dataType = "String")
    public JSONObject pumpByName(String name) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Pump pump = pumpMapper.selectByName(name);
        if(pump != null){
            JSONObject jo = new JSONObject();
            jo.put("name", pump.getName());
            jo.put("start_level", pump.getStartLevel());
            jo.put("stop_level", pump.getStopLevel());
            jo.put("designed_flow", pump.getDesignedFlow());
            jo.put("locatio_name", pump.getLocation());
            JSONArray location = new JSONArray();
            location.add(pump.getLon());
            location.add(pump.getLat());
            jo.put("location", location);
            jo.put("stage", pump.getStage());
            jo.put("type", pump.getType());
            jo.put("num", pump.getNum());
            jo.put("drain", pump.getDrain());
            jo.put("kw", pump.getKw());
            jo.put("area", pump.getArea());
            jo.put("time", pump.getTime());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "泵站实时状态", notes = "返回泵站实时状态", produces = "application/json")
    @GetMapping("/pumprtstatus")
    public JSONObject pumpRtStatus() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PumpRt pumprt = pumprtMapper.selectNewest();
        if(pumprt != null){
            JSONObject jo = new JSONObject();
            jo.put("open", 1);
            jo.put("#1", 1);
            jo.put("#2", 0);
            jo.put("#3", 0);
            jo.put("#4", 0);
            jo.put("#5", 0);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "泵站实时数据", notes = "返回最新一条泵站实时数据", produces = "application/json")
    @GetMapping("/pumprt")
    public JSONObject pumpRt() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PumpRt pumprt = pumprtMapper.selectNewest();
        if(pumprt != null){
            JSONObject jo = new JSONObject();
            jo.put("level", pumprt.getLevel());
            jo.put("flowOut", pumprt.getFlowOut());
            jo.put("flow", pumprt.getFlow());
            jo.put("an", pumprt.getAn());
            jo.put("pho", pumprt.getPho());
            jo.put("cod", pumprt.getCod());
            jo.put("time", pumprt.getTime());
            jo.put("accident", 0);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "泵站统计数据", notes = "返回泵站统计数据", produces = "application/json")
    @GetMapping("/pumpstatistics")
    public JSONObject pumpStatistics() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //泵站数量
        int count1 = pumpMapper.selectCountByType("供水泵站");
        int count2 = pumpMapper.selectCountByType("污水泵站");
        PumpRt pumprt = pumprtMapper.selectNewest();
        JSONObject jo = new JSONObject();
        jo.put("water_supply_pump", count1);
        jo.put("dirty_water_pump", count2);
        jo.put("total_time", "2200h");
        jo.put("current_time", "12h25min");
        jo.put("total_drain", 1.8);
        jo.put("current_drain", 1800.45);
        jsonArray.add(jo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }



    @ApiOperation(value = "水质净化厂--详细", notes = "返回所选水质净化厂的基础信息", produces = "application/json")
    @RequestMapping(value = "/waterclearbyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "水质净化厂名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterClearByName(String name) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        WaterClear waterClear = waterClearMapper.selectByName(name);
        if(waterClear != null){
            JSONObject jo = new JSONObject();
            jo.put("name", waterClear.getName());
            JSONArray location = new JSONArray();
            location.add(waterClear.getLon());
            location.add(waterClear.getLat());
            jo.put("outStandard", waterClear.getOutStandard());
            jo.put("designedStandard", waterClear.getDesignedStandard());
            jo.put("builder", waterClear.getBuilder());
            jo.put("manager", waterClear.getManager());
            jo.put("time", waterClear.getTime());
            jo.put("diameter", waterClear.getDiameter());
            jo.put("waterBody", waterClear.getWaterBody());
            jo.put("model", waterClear.getModel());
            jo.put("modelType", waterClear.getModelType());
            jo.put("consume", waterClear.getConsume());
            jo.put("stage", waterClear.getStage());
            jo.put("alert", waterClear.getAlert());
            jo.put("inNum", waterClear.getInNum());
            jo.put("inPumpNum", waterClear.getInPumpNum());
            jo.put("dirtyPump", waterClear.getDirtyPump());
            jo.put("pumpName", waterClear.getPumpName());
            jo.put("status", waterClear.getStatus());
            jo.put("level", waterClear.getLevel());
            jo.put("device", waterClear.getDevice());
            jo.put("serveArea", waterClear.getServeArea());
            jo.put("dischargeStandard", waterClear.getDischargeStandard());
            jo.put("waterQuality", waterClear.getWaterQuality());
            jo.put("use", waterClear.getUse());
            jo.put("area", waterClear.getArea());
            jo.put("river", waterClear.getRiver());
            jo.put("clearDevice", waterClear.getClearDevice());
            jo.put("station", waterClear.getStation());
            jo.put("qualityStation", waterClear.getQualityStation());
            jo.put("investment", waterClear.getInvestment());
            jo.put("startTime", waterClear.getStartTime());
            jo.put("completeTime", waterClear.getCompleteTime());
            jo.put("serve_people", waterClear.getServePeople());
            jo.put("artifact", waterClear.getArtifact());
            jo.put("manager", waterClear.getManager());
            jo.put("builder", waterClear.getBuilder());
            jo.put("time", waterClear.getTime());
            jo.put("location", location);
            jo.put("diameter", waterClear.getDiameter());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水质净化厂30天监测数据", notes = "返回水质净化厂30天监测数据", produces = "application/json")
    @RequestMapping(value = "/waterclear30day", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "水质净化厂名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterClear30Day(String name) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WaterClearRt> waterClearRtList = waterClearRtMapper.select30DayByName(name);
        for(WaterClearRt list : waterClearRtList){
            JSONObject jo = new JSONObject();
            jo.put("water_in", list.getWaterIn());
            jo.put("water_out", list.getWaterOut());
            jo.put("an", list.getAn());
            jo.put("pho", list.getPho());
            jo.put("cod", list.getCod());
            jo.put("level", list.getLevel());
            jo.put("time", list.getTime());
            jo.put("name", list.getName());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水质净化厂统计", notes = "返回水质净化厂统计数据", produces = "application/json")
    @GetMapping("/waterclearstatistics")
    public JSONObject waterClearStatistics() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
//        int count1 = waterClearMapper.selectCountByStandard("一级A");
//        int count2 = waterClearMapper.selectCountByStandard("准Ⅳ类");
        JSONObject jo = new JSONObject();
        jo.put("standard", "一级A");
        jo.put("num", 7);
        jo.put("normal", 6);
        jo.put("exceed", 1);
        JSONObject joo = new JSONObject();
        joo.put("standard", "准Ⅳ类");
        joo.put("num", 6);
        joo.put("normal", 6);
        joo.put("exceed", 0);
        jsonArray.add(jo);
        jsonArray.add(joo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "排水管网统计", notes = "返回排水管网统计", produces = "application/json")
    @GetMapping("/pipelinestatistics")
    public JSONObject pipeLineStatistics() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("name", "雨水管网");
        jo.put("total_length", 6695);
        jo.put("full_length", 20);
        jo.put("point", 3);
        jo.put("inspection_length", 8);
        JSONObject joo = new JSONObject();
        jo.put("name", "污水管网");
        jo.put("total_length", 7430);
        jo.put("full_length", 25);
        jo.put("point", 6);
        jo.put("inspection_length", 10);
        jsonArray.add(jo);
        jsonArray.add(joo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }







}
