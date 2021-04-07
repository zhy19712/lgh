package com.example.demo.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.ServerConfig;
import com.example.demo.entity.Event;
import com.example.demo.entity.Task;
import com.example.demo.entity.Trail;
import com.example.demo.mapper.EventMapper;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.TrailMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "管养--事件上报")
@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TrailMapper trailMapper;

    @Autowired
    private ServerConfig serverConfig;



    @ApiOperation(value = "当天上报事件--总览", notes = "返回当天事件上报基本信息、坐标", produces = "application/json")
    @GetMapping("/eventtoday")
    public JSONObject eventToday() {
        List<Event> eventList = eventMapper.selectToday();
        JSONArray jsonArray = new JSONArray();
        for (Event list : eventList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            jo.put("time", list.getTime());
            jo.put("event", list.getEvent());
            jo.put("big_event", list.getBigEvent());
            jo.put("type", list.getType());
            jo.put("status",list.getStatus());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "当天上报事件--详细", notes = "返回详细上报信息", produces = "application/json")
    @RequestMapping(value = "/eventdetail", method = RequestMethod.POST)
    @ApiImplicitParam(name = "id", value = "所选点位id", paramType = "query", required = true, dataType = "String")
    public JSONObject eventDetail(int id) {
        Event event = eventMapper.selectTodayById(id);
        JSONArray jsonArray = new JSONArray();
        String server = serverConfig.getUrl();
        if (event != null) {
            JSONObject jo = new JSONObject();
            jo.put("id", event.getId());
            jo.put("name", event.getName());
            jo.put("time", event.getTime());
            jo.put("event", event.getEvent());
            jo.put("type", event.getType());
            jo.put("detail", event.getDetail());
            jo.put("demand", event.getDemand());
            jo.put("result", event.getResult());
            jo.put("person", event.getPerson());
            try {
                JSONArray files =  JSONArray.parseArray(event.getFiles());
                for(int i=0; i<files.size();i++){
                    files.set(i, server + files.get(i));
                }
                jo.put("files", files);
            } catch (Exception e) {
                System.out.println(event.getId()+"数据格式错误");
            }
            try {
                JSONArray before =  JSONArray.parseArray(event.getPicBefore());
                for(int i=0; i<before.size();i++){
                    before.set(i, server + before.get(i));
                }
                jo.put("pic_before", before);
            } catch (Exception e) {
                System.out.println(event.getId()+"数据格式错误");
            }
            try {
                JSONArray after =  JSONArray.parseArray(event.getPicAfter());
                for(int i=0; i<after.size();i++){
                    after.set(i, server + after.get(i));
                }
                jo.put("pic_after", after);
            } catch (Exception e) {
                System.out.println(event.getId()+"数据格式错误");
            }
            jo.put("big_event", event.getBigEvent());
            jo.put("status", event.getStatus());
            jo.put("reporter", event.getReporter());
            jo.put("server", serverConfig.getUrl());
            JSONArray location = new JSONArray();
            location.add(event.getLon());
            location.add(event.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "当天事件上报数统计", notes = "返回当天事件上报数，大事件上报数，完成率", produces = "application/json")
    @GetMapping("/eventStatistics")
    public JSONObject eventStatistics() {
        int count = eventMapper.selectCount();
        int big_eventCount = eventMapper.selectBig_eventCount();
        int completionCount = eventMapper.selectCompletion();
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        if (count > 0) {
            JSONObject jo = new JSONObject();
            jo.put("count", count);
            jo.put("big_eventCount", big_eventCount);
            jo.put("completionRate", completionCount * 100 / count + "%");
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "上报事件、巡查数据--7天数据量", notes = "返回所选时间7天内每天巡查任务数和上报事件数", produces = "application/json")
    @RequestMapping(value = "/7daystatistics", method = RequestMethod.POST)
    @ApiImplicitParam(name = "time", value = "所选时间（e.g. 2020-11-10），如果不填参数，则默认日期为今天", paramType = "query", dataType = "String")
    public JSONObject eventEveryday(String time) {
        String date;
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (time == null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(new Date());
        } else {
            date = time;
        }
        List<Event> eventList = eventMapper.select7Day(date);
        List<Task> taskList = taskMapper.select7Day(date);

        Map<String, Integer> eventMap = new HashMap<>();
        Map<String, Integer> taskMap = new HashMap<>();
        for (Event list : eventList) {
            int i = 1;
            String datetime = list.getTime().substring(0, 10);
            if (eventMap.get(datetime) != null) {
                i = eventMap.get(datetime) + 1;
            }
            eventMap.put(datetime, i);
        }
        for (Task list : taskList) {
            int i = 1;
            String datetime = list.getTime().substring(0, 10);
            if (taskMap.get(datetime) != null) {
                i = taskMap.get(datetime) + 1;
            }
            taskMap.put(datetime, i);
        }
        JSONArray event = new JSONArray();
        JSONArray task = new JSONArray();

        for (Map.Entry<String, Integer> entry : eventMap.entrySet()) {
            JSONObject jo = new JSONObject();
            jo.put("time", entry.getKey());
            jo.put("count", entry.getValue());
            event.add(jo);
        }
        for (Map.Entry<String, Integer> entry : taskMap.entrySet()) {
            JSONObject joo = new JSONObject();
            joo.put("time", entry.getKey());
            joo.put("count", entry.getValue());
            task.add(joo);
        }

        JSONObject event_object = new JSONObject();
        JSONObject task_object = new JSONObject();

        event_object.put("name", "事件上报");
        event_object.put("statistics", event);
        task_object.put("name", "巡查任务");
        task_object.put("statistics", task);
        jsonArray.add(event_object);
        jsonArray.add(task_object);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "7日上报数据统计", notes = "返回所选时间7天内每天上报事件数以及完成事件数", produces = "application/json")
    @RequestMapping(value = "/event7day", method = RequestMethod.POST)
    @ApiImplicitParam(name = "time", value = "所选时间（e.g. 2020-11-10），如果不填参数，则默认日期为今天", paramType = "query", dataType = "String")
    public JSONObject event7Day(String time) {
        String date;
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (time == null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            date = df.format(new Date());
            date = "2020-12-07";
        } else {
            date = time;
        }
        List<Event> eventList = eventMapper.select7Day(date);

        Map<String, Integer> eventMap = new HashMap<>();
        Map<String, Integer> completionMap = new HashMap<>();
        for (Event list : eventList) {
            int i = 1;
            int j = 1;
            String datetime = list.getTime().substring(0, 10);
            int status = list.getStatus();
            if (eventMap.get(datetime) != null) {
                i = eventMap.get(datetime) + 1;
            }
            eventMap.put(datetime, i);
            if(status == 1){
                if(completionMap.get(datetime) != null){
                    j = completionMap.get(datetime) + 1;
                }
                completionMap.put(datetime, j);
            }
        }
        JSONArray event = new JSONArray();
        JSONArray completion = new JSONArray();


        for (Map.Entry<String, Integer> entry : eventMap.entrySet()) {
            JSONObject jo = new JSONObject();
            jo.put("time", entry.getKey());
            jo.put("count", entry.getValue());
            event.add(jo);
        }
        for (Map.Entry<String, Integer> entry : completionMap.entrySet()) {
            JSONObject joo = new JSONObject();
            joo.put("time", entry.getKey());
            joo.put("count", entry.getValue());
            completion.add(joo);
        }


        JSONObject event_object = new JSONObject();
        JSONObject completion_object = new JSONObject();

        event_object.put("name", "事件上报");
        event_object.put("statistics", event);
        completion_object.put("name", "已整改事件");
        completion_object.put("statistics", completion);
        jsonArray.add(event_object);
        jsonArray.add(completion_object);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }



    @ApiOperation(value = "当天某种类型所有人员的巡查过程中发生的事件上报", notes = "返回当天巡查轨迹上发生的事件上报气泡信息", produces = "application/json")
    @RequestMapping(value = "/geteventtoday", method = RequestMethod.POST)
    @ApiImplicitParam(name = "type", value = "巡查类型（与巡查轨迹类型保持一致）", paramType = "query", required = true, dataType = "String")
    public JSONObject getEventToday(String type) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        List<Trail> peopleList = trailMapper.selectPeople("2020-12-07", type);
        for (Trail list : peopleList) {
            List<Event> eventList = eventMapper.selectEvent("2020-12-07", list.getName());
            JSONObject jo = new JSONObject();
            JSONArray event = new JSONArray();
            if (eventList.size() > 0) {
                for (Event llist : eventList) {
                    JSONArray location = new JSONArray();
                    JSONObject joo = new JSONObject();
                    location.add(llist.getLon());
                    location.add(llist.getLat());
                    joo.put("event", llist.getEvent());
                    joo.put("id", llist.getId());
                    joo.put("location", location);
                    jo.put("big_event", llist.getBigEvent());
                    joo.put("status", llist.getStatus());
                    joo.put("time", llist.getTime());
                    event.add(joo);
                }
                jo.put("name", list.getName());
                jo.put("detail", event);
                jsonArray.add(jo);
            }
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }





}
