package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.ServerConfig;
import com.example.demo.entity.Construction;
import com.example.demo.mapper.ConstructionMapper;
import com.example.demo.mapper.RiverMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "test")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private ConstructionMapper constructionMapper;

    @Autowired
    private ServerConfig serverConfig;


    @ApiOperation(value = "file_path", notes = "return file path", produces = "application/json")
    @RequestMapping(value = "/getfilepath", method = RequestMethod.GET)
    public JSONObject getFlightPoint() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String server = serverConfig.getUrl();
        List<Construction> constructionList = constructionMapper.selectAll();
        for(Construction list : constructionList){
            JSONObject jo = new JSONObject();
            try {
                JSONArray temp =  JSONArray.parseArray(list.getPicBefore());
                for(int i=0; i<temp.size();i++){
                    temp.set(i, server + temp.get(i));
                }
                jo.put("pic",temp);
            } catch (Exception e) {
                System.out.println(list.getId()+"数据格式错误");
            }
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }
}
