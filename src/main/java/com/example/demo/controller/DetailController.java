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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "点位详细信息（易涝点、避难所）")
@RestController
@RequestMapping("/api/detail")
public class DetailController {

    @Autowired
    private FloodPointMapper floodPointMapper;

    @Autowired
    private ShelterMapper shelterMapper;

    @ApiOperation(value = "易涝点--详情", notes = "返回所选易涝点基本信息", produces = "application/json")
    @RequestMapping(value = "/floodpointbyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "易涝点名称", paramType = "query", required = true, dataType = "String")
    public JSONObject floodPointByName(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        FloodPoint floodPoint  = floodPointMapper.selectByName(name);
        if (floodPoint != null) {
            JSONObject jo = new JSONObject();
            jo.put("name", floodPoint.getName());
            jo.put("district", floodPoint.getDistrict());
            jo.put("street", floodPoint.getStreet());
            jo.put("type", floodPoint.getType());
            jo.put("area", floodPoint.getArea());
            jo.put("depth", floodPoint.getDepth());
            jo.put("duration", floodPoint.getDuration());
            jo.put("reason", floodPoint.getReason());
            jo.put("process", floodPoint.getProcess());
            jo.put("flood_type", floodPoint.getFloodType());
            jsonArray.add(jo);
        }

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "避难所--详情", notes = "返回所选避难所基本信息", produces = "application/json")
    @RequestMapping(value = "/shelterbyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "避难所名称", paramType = "query", required = true, dataType = "String")
    public JSONObject shelterByName(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        Shelter shelter  = shelterMapper.selectByName(name);
        if (shelter != null) {
            JSONObject jo = new JSONObject();
            jo.put("name", shelter.getName());
            jo.put("unit", shelter.getUnit());
            jo.put("path", shelter.getPath());
            jo.put("street", shelter.getStreet());
            jo.put("person", shelter.getPerson());
            jo.put("tele", shelter.getTele());
            jo.put("address", shelter.getAddress());
            jo.put("normal_path", shelter.getNormalPath());
            jo.put("recommend_path", shelter.getRecommendPath());
            jsonArray.add(jo);
        }

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }



}
