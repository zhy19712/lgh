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

@Api(tags = "管养--水质水位")
@RestController
@RequestMapping("/api/water")
public class WaterController {
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



    @ApiOperation(value = "断面水位--总览", notes = "返回所选河流所有断面的实时水位数据", produces = "application/json")
    @RequestMapping(value = "/waterlevelall", method = RequestMethod.GET)
    public JSONObject waterLevelAll() {
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

    @ApiOperation(value = "断面水位--详细信息", notes = "返回所选断面最近100条水位数据以及流量数据", produces = "application/json")
    @RequestMapping(value = "/waterlevelsingle", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterLevelSingle(String name) {
        List<WaterLevel> waterLevelList = waterLevelMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        for (WaterLevel list : waterLevelList) {
            JSONObject jo = new JSONObject();
            jo.put("name", list.getName());
            jo.put("time", list.getTime());
            jo.put("waterlevel", list.getWaterlevel());
            jo.put("flow", list.getFlow());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }




    @ApiOperation(value = "水质监测--总览", notes = "返回所有点位的实时水质数据", produces = "application/json")
    @GetMapping("/waterqualityall")
    public JSONObject waterQualityAll() {
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

    @ApiOperation(value = "水质监测--点位", notes = "返回所选点位最近100条水质数据", produces = "application/json")
    @RequestMapping(value = "/waterqualitysingle", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "所选点位名称", paramType = "query", required = true, dataType = "String")
    public JSONObject waterQualitySingle(String name) {
        List<WaterQualityArtificial> waterQualityArtificialList = waterQualityArtificialMapper.selectByName(name);
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        for (WaterQualityArtificial list : waterQualityArtificialList) {
            JSONObject jo = new JSONObject();
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



}
