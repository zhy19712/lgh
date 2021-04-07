package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Common {

    public static JSONArray string2JsonArray(String s){
        JSONArray jsonArray = JSONArray.parseArray(s);
        return jsonArray;
    }

    public static JSONArray string2JsonArray2dimensions(String s){
        s = s.substring(1,s.length() - 1);
        s = s.replace("],", "];");
        String[] strArr = s.split(";");
        JSONArray array = new JSONArray();
        for(int i=0; i<strArr.length; i++){
            JSONArray temp = JSONArray.parseArray(strArr[i]);
            array.add(temp);
        }
        return array;
    }
}
