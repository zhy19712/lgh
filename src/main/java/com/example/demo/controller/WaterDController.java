package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

@Api(tags = "设施--水质水位")
@RestController
@RequestMapping("/api/waterd")
public class WaterDController {
    @Autowired
    private WaterSectionMapper waterSectionMapper;

    @Autowired
    private WaterLevelMapper waterLevelMapper;

    @Autowired
    private WaterQualityMapper waterQualityMapper;

    @Autowired
    private WaterQualityArtificialMapper waterQualityArtificialMapper;

    @Autowired
    private WaterLevelFlowMapper waterLevelFlowMapper;

    @ApiOperation(value = "水位流量曲线", notes = "返回所选河流所有断面的水位流量数据", produces = "application/json")
    @RequestMapping(value = "/waterlevelflow", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "河流名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterLevelFlow(String name) {
        //返回河流断面list
        List<WaterSection> sectionList = waterSectionMapper.selectSection("龙岗河");
        //利用河流断面list查所有断面的实时水位数据
        JSONArray jsonArray = new JSONArray();
        for(WaterSection section : sectionList){
            WaterLevelFlow waterLevelFlow = waterLevelFlowMapper.selectByName(section.getName());
            WaterLevel waterLevel = waterLevelMapper.selectRtByName(section.getName());
                if(waterLevelFlow != null){
                    JSONObject jo = new JSONObject();
                    jo.put("name", waterLevelFlow.getName());
                    jo.put("level_100",waterLevelFlow.getLevel100());
                    jo.put("level_50",waterLevelFlow.getLevel50());
                    jo.put("level_20",waterLevelFlow.getLevel20());
                    jo.put("flow_100",waterLevelFlow.getFlow100());
                    jo.put("flow_50",waterLevelFlow.getFlow50());
                    jo.put("flow_20",waterLevelFlow.getFlow20());
                    if(waterLevel != null){
                        jo.put("flow_rt",waterLevel.getFlow());
                        jo.put("level_rt",waterLevel.getWaterlevel());
                    }else{
                        jo.put("flow_rt",0);
                        jo.put("level_rt",0);
                    }
                    jsonArray.add(jo);
                }

        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "断面水位--总览", notes = "返回所选河流所有断面的实时水位数据", produces = "application/json")
    @RequestMapping(value = "/waterlevelall", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "河流名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterLevelAll(String name) {
        //返回河流断面list
        List<WaterSection> sectionList = waterSectionMapper.selectSection("龙岗河");
        //利用河流断面list查所有断面的实时水位数据
        JSONArray jsonArray = new JSONArray();
        for(WaterSection section : sectionList){
            List<WaterLevel> waterLevelList = waterLevelMapper.selectByMyWrapper(section.getName());
            for (WaterLevel list : waterLevelList) {
                if(list != null){
                    JSONObject jo = new JSONObject();
                    JSONArray location = new JSONArray();
                    jo.put("name", list.getName());
                    jo.put("time", list.getTime());
                    jo.put("waterlevel", list.getWaterlevel());
                    location.add(list.getLon());
                    location.add(list.getLat());
                    jo.put("location", location);
                    jsonArray.add(jo);
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "断面水位--画图", notes = "返回所选断面最近100条水位数据以及流量数据", produces = "application/json")
    @RequestMapping(value = "/waterlevelsingle", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterLevelSingle(String name) {
        List<WaterLevel> waterLevelList = waterLevelMapper.selectByName(name);
        WaterSection waterSection = waterSectionMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        for (WaterLevel list : waterLevelList) {
            JSONObject jo = new JSONObject();
            jo.put("name", list.getName());
            jo.put("time", list.getTime());
            jo.put("waterlevel", list.getWaterlevel());
            jo.put("left", waterSection.getLeft());
            jo.put("right", waterSection.getRight());
            jo.put("p1", waterSection.getP1());
            jo.put("p2", waterSection.getP2());
            jo.put("height", waterSection.getHeight());
            jo.put("normal", waterSection.getNormal());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "水质监测--总览", notes = "返回所有点位的实时水质数据", produces = "application/json")
    @RequestMapping(value = "/waterqualityall", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "河流名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterQualityAll(String name) {
        List<WaterQuality> pointList = waterQualityMapper.selectPoint("龙岗河");
        JSONArray jsonArray = new JSONArray();
        for(WaterQuality point : pointList){
            List<WaterQualityArtificial> waterQualityArtificialList = waterQualityArtificialMapper.selectByMyWrapper(point.getName());
            for (WaterQualityArtificial list : waterQualityArtificialList) {
                JSONObject jo = new JSONObject();
                JSONArray location = new JSONArray();
                jo.put("name", list.getName());
                jo.put("time", list.getTime());
                jo.put("codcr", list.getCodcr());
                jo.put("ammonia", list.getAmmonia());
                jo.put("phosphorous", list.getPhosphorous());
                jo.put("ph", list.getPh());
                location.add(list.getLon());
                location.add(list.getLat());
                jo.put("location", location);
                jsonArray.add(jo);
            }
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水质监测--画图", notes = "返回所选点位最近100条水质数据", produces = "application/json")
    @RequestMapping(value = "/waterqualitysingle", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterQualitySingle(String name) {
        List<WaterQualityArtificial> waterQualityArtificialList = waterQualityArtificialMapper.selectByName(name);
        WaterQuality waterQuality = waterQualityMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        for (WaterQualityArtificial list : waterQualityArtificialList) {
            JSONObject jo = new JSONObject();
            jo.put("codh", waterQuality.getCodh());
            jo.put("anh", waterQuality.getAnh());
            jo.put("phoh", waterQuality.getPhoh());
            jo.put("phl", waterQuality.getPhl());
            jo.put("phh", waterQuality.getPhh());
            jo.put("id", list.getId());
            jo.put("time", list.getTime());
            jo.put("codcr", list.getCodcr());
            jo.put("ammonia", list.getAmmonia());
            jo.put("phosphorous", list.getPhosphorous());
            jo.put("ph", list.getPh());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水位断面--属性", notes = "返回所选水位断面属性信息", produces = "application/json")
    @RequestMapping(value = "/watersectionsingle", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterSectionSingle(String name) {
        WaterSection waterSection = waterSectionMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        if(waterSection !=null) {
            JSONObject jo = new JSONObject();
            jo.put("id", waterSection.getId());
            jo.put("name", waterSection.getName());
            jo.put("order", waterSection.getOrder());
            jo.put("belong", waterSection.getBelong());
            jo.put("type", waterSection.getType());
            jo.put("status", waterSection.getStatus());
            jo.put("alert_level", waterSection.getAlertLevel());
            jo.put("inspection", waterSection.getInspection());
            jo.put("location", waterSection.getLocation());
            jo.put("station_time", waterSection.getStationTime());
            jo.put("stick", waterSection.getStick());
            jo.put("standard", waterSection.getStandard());
            jo.put("measure", waterSection.getMeasure());
            jo.put("communication", waterSection.getCommunication());
            jo.put("producer", waterSection.getProducer());
            jo.put("model", waterSection.getModel());
            jo.put("system", waterSection.getSystem());
            jo.put("scene", waterSection.getScene());
            jo.put("district", waterSection.getDistrict());
            jo.put("manager", waterSection.getManager());
            jo.put("person", waterSection.getPerson());
            jo.put("remark", waterSection.getRemark());
            jo.put("tele", waterSection.getTele());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水质断面--属性", notes = "返回所选水质断面属性信息", produces = "application/json")
    @RequestMapping(value = "/waterqualitysectionsingle", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterQualitySectionSingle(String name) {
        WaterQuality waterQuality = waterQualityMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        if(waterQuality !=null) {
            JSONObject jo = new JSONObject();
            jo.put("id", waterQuality.getId());
            jo.put("name", waterQuality.getName());
            jo.put("belong", waterQuality.getBelong());
            jo.put("type", waterQuality.getType());
            jo.put("status", waterQuality.getStatus());
            jo.put("inspection", waterQuality.getInspection());
            jo.put("location", waterQuality.getLocation());
            jo.put("station_time", waterQuality.getStationTime());
            jo.put("section", waterQuality.getSection());
            jo.put("measure", waterQuality.getMeasure());
            jo.put("communication", waterQuality.getCommunication());
            jo.put("producer", waterQuality.getProducer());
            jo.put("model", waterQuality.getModel());
            jo.put("system", waterQuality.getSystem());
            jo.put("scene", waterQuality.getScene());
            jo.put("district", waterQuality.getDistrict());
            jo.put("manager", waterQuality.getManager());
            jo.put("person", waterQuality.getPerson());
            jo.put("remark", waterQuality.getRemark());
            jo.put("tele", waterQuality.getTele());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }



}
