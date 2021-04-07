package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "图层操作")
@RestController
@RequestMapping("/api/map")
public class MapController {
    @Autowired
    private RiverMapper riverMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private WaterClearMapper waterClearMapper;

    @Autowired
    private WetlandMapper wetlandMapper;


    @Autowired
    private StreetMapper streetMapper;


    @Autowired
    private TankMapper tankMapper;

    @Autowired
    private DamMapper damMapper;

    @Autowired
    private PumpMapper pumpMapper;

    @Autowired
    private PipeMapper pipeMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private WaterSectionMapper waterSectionMapper;

    @Autowired
    private WaterQualityMapper waterQualityMapper;

    @Autowired
    private CameraMapper cameraMapper;

    @Autowired
    private ShelterMapper shelterMapper;

    @Autowired
    private RainMapper rainMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private FloodPointMapper floodPointMapper;

    @Autowired
    private PipeInspectionMapper pipeInspectionMapper;

    @ApiOperation(value = "河流--点位", notes = "加载所有河流名称以及坐标（坐标不为空）", produces = "application/json")
    @GetMapping("/riverall")
    public JSONObject riverAll() {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        List<River> riverList = riverMapper.selectAll();
        for (River list : riverList) {
            if (list.getLocation() != null) {
                JSONObject jo = new JSONObject();
                jo.put("id", list.getId());
                jo.put("name", list.getName());
                try {
                    jo.put("location", JSONArray.parseArray(list.getLocation()));
                } catch (Exception e) {
                    System.out.println(list.getId()+"河流坐标数据格式错误");
                }
                jsonArray.add(jo);
            }
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水库--点位", notes = "加载所有水库名称以及坐标", produces = "application/json")
    @GetMapping("/reservationall")
    public JSONObject reservationAll() {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        List<Reservation> reservationList = reservationMapper.selectAll();
        for (Reservation list : reservationList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"水库坐标格式有误");
            }

            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "湿地--点位", notes = "湿地基本信息", produces = "application/json")
    @GetMapping("/wetland")
    public JSONObject wetland() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Wetland> wetlandList = wetlandMapper.selectAll();
        for(Wetland list : wetlandList){
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "街道--点位", notes = "加载所有街道名称以及坐标", produces = "application/json")
    @GetMapping("/streetall")
    public JSONObject streetAll() {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        List<Street> streetList = streetMapper.selectAll();
        for (Street list : streetList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"街道坐标格式有误");
            }

            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "闸坝--点位", notes = "返回所有闸坝的信息", produces = "application/json")
    @GetMapping("/damall")
    public JSONObject damAll() {
        List<Dam> damList = damMapper.selectAll();
        JSONArray jsonArray = new JSONArray();
        for (Dam list : damList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"闸坝坐标格式有误");
            }
            jsonArray.add(jo);
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水质净化厂--点位", notes = "返回水质净化厂统计数据", produces = "application/json")
    @GetMapping("/waterclearall")
    public JSONObject waterClearAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WaterClear> waterClearList = waterClearMapper.selectAll();
        for(WaterClear list : waterClearList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "泵站--点位", notes = "返回泵站点位信息", produces = "application/json")
    @RequestMapping(value = "/pumpall", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询类型（如果数组不存在或者为空，则返回全部类型泵站）   {\"types\":[\"雨水泵站\",\"污水泵站\",\"供水泵站\"]}", paramType = "body")
    public JSONObject pumpAll(@RequestBody JSONObject condition) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(condition.get("types")!=null&&JSONArray.parseArray(JSONObject.toJSONString(condition.get("types"))).size() > 0){
            JSONArray arr = JSONArray.parseArray(JSONObject.toJSONString(condition.get("types")));
            for (int i = 0; i < arr.size(); i++) {
                List<Pump> pumpList = pumpMapper.selectByType(arr.getString(i));
                JSONObject jo = new JSONObject();
                for (Pump list : pumpList) {
                    JSONArray location = new JSONArray();
                    location.add(list.getLon());
                    location.add(list.getLat());
                    jo.put("location",location);
                    jo.put("name", list.getName());
                    jo.put("id", list.getId());
                    jo.put("type", list.getType());
                    jsonArray.add(jo);
                }
            }
        }else{
            List<Pump> pumpList = pumpMapper.selectAll();
            for (Pump list : pumpList) {
                JSONObject jo = new JSONObject();

                JSONArray location = new JSONArray();
                location.add(list.getLon());
                location.add(list.getLat());
                jo.put("location",location);
                jo.put("name", list.getName());
                jo.put("id", list.getId());
                jo.put("type", list.getType());
                jsonArray.add(jo);
            }
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "调蓄池--点位", notes = "返回调蓄池点位信息", produces = "application/json")
    @GetMapping("/tankall")
    public JSONObject tankAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Tank> tankList = tankMapper.selectAll();
        for(Tank list : tankList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "管网--点位", notes = "返回管网点位信息", produces = "application/json")
    @RequestMapping(value = "/pipeall", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询类型（如果数组不存在或者为空，则返回全部类型管网）   {\"types\":[\"雨水管网\",\"污水管网\"]}", paramType = "body")
    public JSONObject pipeAll(@RequestBody JSONObject condition) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(condition.get("types")!=null&&JSONArray.parseArray(JSONObject.toJSONString(condition.get("types"))).size() > 0){
            JSONArray arr = JSONArray.parseArray(JSONObject.toJSONString(condition.get("types")));
            for (int i = 0; i < arr.size(); i++) {
                List<Pipe> pipeList = pipeMapper.selectAllByType(arr.getString(i));
                for (Pipe list : pipeList) {
                    JSONObject jo = new JSONObject();
                    try {
                        jo.put("location", JSONArray.parseArray(list.getLocation()));
                    } catch (Exception e) {
                        System.out.println(list.getId()+"管网坐标格式有误");
                    }
                    jo.put("name", list.getName());
                    jo.put("id", list.getId());
                    jo.put("type", list.getType());
                    jsonArray.add(jo);
                }
            }
        }else{
            List<Pipe> pipeList = pipeMapper.selectAll();
            for (Pipe list : pipeList) {
                JSONObject jo = new JSONObject();
                try {
                    jo.put("location", JSONArray.parseArray(list.getLocation()));
                } catch (Exception e) {
                    System.out.println(list.getId()+"管网坐标格式有误");
                }
                jo.put("name", list.getName());
                jo.put("id", list.getId());
                jo.put("type", list.getType());
                jsonArray.add(jo);
            }
        }
        System.out.println(jsonArray);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "管网监测--点位", notes = "返回管网监测点位信息", produces = "application/json")
    @RequestMapping(value = "/pipeinspectionall", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询类型（如果数组不存在或者为空，则返回全部类型管网监测）   {\"types\":[\"液位站\",\"流量站\"]}", paramType = "body")
    public JSONObject pipeInspectionAll(@RequestBody JSONObject condition) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(condition.get("types")!=null&&JSONArray.parseArray(JSONObject.toJSONString(condition.get("types"))).size() > 0){
            JSONArray arr = JSONArray.parseArray(JSONObject.toJSONString(condition.get("types")));
            for (int i = 0; i < arr.size(); i++) {
                List<PipeInspection> pipeInspectionList = pipeInspectionMapper.selectAllByType(arr.getString(i));
                for (PipeInspection list : pipeInspectionList) {
                    JSONObject jo = new JSONObject();
                    try {
                        System.out.println(list);
                        jo.put("location", JSONArray.parseArray(list.getLocation()));
                    } catch (Exception e) {
                        System.out.println(list.getId()+"管网监测坐标格式有误");
                    }
                    jo.put("name", list.getName());
                    jo.put("id", list.getId());
                    jo.put("type", list.getType());
                    jsonArray.add(jo);
                }
            }
        }else{
            List<PipeInspection> pipeInspectionList = pipeInspectionMapper.selectAll();
            for (PipeInspection list : pipeInspectionList) {
                JSONObject jo = new JSONObject();
                try {
                    System.out.println(list);
                    jo.put("location", JSONArray.parseArray(list.getLocation()));
                } catch (Exception e) {
                    System.out.println(list.getId()+"管网监测坐标格式有误");
                }
                jo.put("name", list.getName());
                jo.put("id", list.getId());
                jo.put("type", list.getType());
                jsonArray.add(jo);
            }
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "箱涵--点位", notes = "返回调箱涵点位信息", produces = "application/json")
    @GetMapping("/boxall")
    public JSONObject boxAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Box> boxList = boxMapper.selectAll();
        for(Box list : boxList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"箱涵坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水位站/流量站--点位", notes = "返回水位站点位信息", produces = "application/json")
    @GetMapping("/watersectionall")
    public JSONObject waterSectionAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WaterSection> sectionList = waterSectionMapper.selectAll();
        for(WaterSection list : sectionList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水质站--点位", notes = "返回水位站点位信息", produces = "application/json")
    @GetMapping("/waterqualityall")
    public JSONObject waterQualityAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WaterQuality> qualityList = waterQualityMapper.selectAll();
        for(WaterQuality list : qualityList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "视屏监控--点位", notes = "返回视屏监控点位信息", produces = "application/json")
    @GetMapping("/camerayall")
    public JSONObject cameraAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Camera> cameraList = cameraMapper.selectAll();
        for(Camera list : cameraList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"视频监控点位坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "应急避难所--点位", notes = "返回应急避难所点位信息", produces = "application/json")
    @GetMapping("/shelterall")
    public JSONObject shelterAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Shelter> shelterList = shelterMapper.selectAll();
        for(Shelter list : shelterList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"避难所坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "雨量站--点位", notes = "返回雨量站点位信息", produces = "application/json")
    @GetMapping("/rainall")
    public JSONObject rainAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Rain> rainList = rainMapper.selectAll();
        for(Rain list : rainList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"雨量站坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "工情设施--点位", notes = "返回工情设施点位信息", produces = "application/json")
    @GetMapping("/deviceall")
    public JSONObject deviceAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Device> deviceList = deviceMapper.selectAll();
        for(Device list : deviceList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"工情设施坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "三防物资--点位", notes = "返回三防物资点位信息", produces = "application/json")
    @GetMapping("/goodsall")
    public JSONObject goodsAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Goods> goodsList = goodsMapper.selectAll();
        for(Goods list : goodsList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"三防物资坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "易涝点--点位", notes = "返回易涝点点位信息", produces = "application/json")
    @GetMapping("/floodpointall")
    public JSONObject floodPointAll() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<FloodPoint> floodPointList = floodPointMapper.selectAll();
        for(FloodPoint list : floodPointList){
            JSONObject jo = new JSONObject();
            jo.put("id",list.getId());
            jo.put("name", list.getName());
            try {
                jo.put("location", JSONArray.parseArray(list.getLocation()));
            } catch (Exception e) {
                System.out.println(list.getId()+"易涝点坐标格式有误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

}
