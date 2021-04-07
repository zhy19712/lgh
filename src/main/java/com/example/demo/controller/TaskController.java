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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "管养--任务计划")
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TrailMapper trailMapper;

    @Autowired
    private ClockMapper clockMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskFixedMapper taskFixedMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ConstructionMapper constructionMapper;

    @Autowired
    private ServerConfig serverConfig;


    @ApiOperation(value = "管养人员统计（当天）", notes = "返回各类人员当班、歇班人数统计", produces = "application/json")
    @GetMapping("/maintainerstatistics")
    public JSONObject maintainerStatistics() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int jidianT = userMapper.selectCountByType("机电");
        int anbaoT = userMapper.selectCountByType("安保");
        int baojieT = userMapper.selectCountByType("保洁");
        int lvhuaT = userMapper.selectCountByType("绿化");

        int jidian = clockMapper.selectClockedCountByType("机电");
        int anbao = clockMapper.selectClockedCountByType("安保");
        int baojie = clockMapper.selectClockedCountByType("保洁");
        int lvhua = clockMapper.selectClockedCountByType("绿化");

        JSONObject jo = new JSONObject();
        JSONObject joo = new JSONObject();
        JSONObject jooo = new JSONObject();
        JSONObject joooo = new JSONObject();

        jo.put("type", "机电");
        jo.put("total", jidianT);
        jo.put("clocked", jidian);
        joo.put("type", "保洁");
        joo.put("total", baojieT);
        joo.put("clocked", baojie);
        jooo.put("type", "安保");
        jooo.put("total", anbaoT);
        jooo.put("clocked", anbao);
        joooo.put("type", "绿化");
        joooo.put("total", lvhuaT);
        joooo.put("clocked", lvhua);

        jsonArray.add(jo);
        jsonArray.add(joo);
        jsonArray.add(jooo);
        jsonArray.add(joooo);

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "管养统计（当月））", notes = "返回当前月份，各任务出动人次，涉河施工总计，暴雨预警次数（天气模块未接入）", produces = "application/json")
    @GetMapping("/taskstatistics")
    public JSONObject taskStatistics() {
        int baojie = clockMapper.selectMonthClockedCountByType("保洁");
        int jidian = clockMapper.selectMonthClockedCountByType("机电");
        int lvhua = clockMapper.selectMonthClockedCountByType("绿化");
        int anbao = clockMapper.selectMonthClockedCountByType("安保");
        int count = constructionMapper.selectCount();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        JSONArray jsonArray = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("month", df.format(new Date()));
        jo.put("baojie", baojie);
        jo.put("jidian", jidian);
        jo.put("lvhua", lvhua);
        jo.put("anbao", anbao);
        jo.put("construction", count);
        jsonArray.add(jo);

        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;

    }

    @ApiOperation(value = "当天巡查任务--总览", notes = "返回当天巡查任务基本信息、坐标", produces = "application/json")
    @GetMapping("/tasktoday")
    public JSONObject taskToday() {
        List<Task> taskList = taskMapper.selectToday();
        JSONArray jsonArray = new JSONArray();
        for (Task list : taskList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("name", list.getName());
            jo.put("executor", list.getExecutor());
            jo.put("time", list.getTime());
            jo.put("task", list.getTask());
            jo.put("type", list.getType());
            jo.put("status", list.getStatus());
            jo.put("demand", list.getDemand());
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

    @ApiOperation(value = "当天某种类型的任务（固定任务+每日巡查任务）--总览", notes = "返回当天某种类型的任务基本信息、坐标", produces = "application/json")
    @RequestMapping(value = "/tasktodaytype", method = RequestMethod.POST)
    @ApiImplicitParam(name = "type", value = "任务类型", paramType = "query", required = true, dataType = "String")
    public JSONObject taskTodayType(String type) {
        List<Task> taskList = taskMapper.selectTodayByType(type);
        List<TaskFixed> taskFixedList = taskFixedMapper.selectFixedTaskByType(type);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        JSONArray jsonArray = new JSONArray();
        for (Task list : taskList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("task", list.getTask());
            jo.put("type", list.getType());
            jo.put("detail", list.getDetail());
            jo.put("time",list.getTime());
            jo.put("name",list.getName());
            jo.put("executor",list.getExecutor());
            jo.put("person", list.getPerson());
            jo.put("status", list.getStatus());
            jo.put("fixed", list.getFixed());
            jo.put("demand", list.getDemand());
            JSONArray location = new JSONArray();
            location.add(list.getLon());
            location.add(list.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        for (TaskFixed list : taskFixedList) {
            JSONObject jo = new JSONObject();
            jo.put("id", list.getId());
            jo.put("task", list.getTask());
            jo.put("type", list.getType());
            jo.put("detail", "每日固定任务");
            jo.put("person", list.getPerson());
            jo.put("time", df.format(new Date()));
            jo.put("stauts", list.getStatus());
            jo.put("fixed", list.getFixed());
            jo.put("demand", list.getDemand());
            jsonArray.add(jo);
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "7日某种类型的任务数据统计", notes = "返回所选时间7天内任务完成统计（固定任务数量以及已完成数量还未加入）", produces = "application/json")
    @RequestMapping(value = "/task7day", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询条件    {\"type\":\"机电\",\"time\":\"2020-09-10\"} ,如果时间为\"\"，则默认日期为今天", paramType = "body", required = true)
    public JSONObject task7Day(@RequestBody JSONObject condition) {
        String date;
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (condition.getString("time").equals("")&&condition.get("time")!=null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            date = df.format(new Date());
            date = "2020-12-07";
        } else {
            date = condition.getString("time");
        }

        List<Task> taskList = taskMapper.select7DayByType(date, condition.getString("type"));
//        int fixTaskCount = taskFixedMapper.selectCountByType(condition.getString("type"));

        Map<String, Integer> taskMap = new HashMap<>();
        Map<String, Integer> completionMap = new HashMap<>();
        for (Task list : taskList) {
            int i = 1;
            int j = 1;
            String datetime = list.getTime().substring(0, 10);
            int status = list.getStatus();
            if (taskMap.get(datetime) != null) {
                i = taskMap.get(datetime) + 1;
            }
            taskMap.put(datetime, i);
            if (status == 1) {
                if (completionMap.get(datetime) != null) {
                    j = completionMap.get(datetime) + 1;
                }
                completionMap.put(datetime, j);
            }
        }
        JSONArray task = new JSONArray();
        JSONArray completion = new JSONArray();

        for (Map.Entry<String, Integer> entry : taskMap.entrySet()) {
//            jo.put(entry.getKey(), entry.getValue() + fixTaskCount);
            JSONObject jo = new JSONObject();

            jo.put("time", entry.getKey());
            jo.put("count", entry.getValue());
            task.add(jo);

        }
        for (Map.Entry<String, Integer> entry : completionMap.entrySet()) {
            JSONObject joo = new JSONObject();

            joo.put("time", entry.getKey());
            joo.put("count", entry.getValue());
            completion.add(joo);

        }


        JSONObject event_object = new JSONObject();
        JSONObject completion_object = new JSONObject();

        event_object.put("name", "计划任务");
        event_object.put("statistics", task);
        completion_object.put("name", "已整改任务");
        completion_object.put("statistics", completion);
        jsonArray.add(event_object);
        jsonArray.add(completion_object);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "当天计划任务--详细", notes = "返回所选任务的详细信息（固定任务暂时没有详细信息，前端过滤掉fixed = 1的任务）", produces = "application/json")
    @RequestMapping(value = "/taskdetail", method = RequestMethod.POST)
    @ApiImplicitParam(name = "id", value = "所选点位id", paramType = "query", required = true, dataType = "String")
    public JSONObject taskDetail(int id) {
        Task task = taskMapper.selectTodayById(id);
        String server = serverConfig.getUrl();
        JSONArray jsonArray = new JSONArray();
        if (null != task) {
            JSONObject jo = new JSONObject();
            jo.put("id", task.getId());
            jo.put("name", task.getName());
            jo.put("time", task.getTime());
            jo.put("task", task.getTask());
            jo.put("type", task.getType());
            jo.put("fixed", task.getFixed());
            jo.put("detail", task.getDetail());
            jo.put("person", task.getPerson());
            try {
                JSONArray before =  JSONArray.parseArray(task.getPicBefore());
                for(int i=0; i<before.size();i++){
                    before.set(i, server + before.get(i));
                }
                jo.put("pic_before", before);
            } catch (Exception e) {
                System.out.println(task.getId()+"数据格式错误");
            }
            try {
                JSONArray after =  JSONArray.parseArray(task.getPicAfter());
                for(int i=0; i<after.size();i++){
                    after.set(i, server + after.get(i));
                }
                jo.put("pic_after", after);
            } catch (Exception e) {
                System.out.println(task.getId()+"数据格式错误");
            }
            jo.put("status", task.getStatus());
            jo.put("executor", task.getExecutor());
            jo.put("demand", task.getDemand());
            JSONArray location = new JSONArray();
            location.add(task.getLon());
            location.add(task.getLat());
            jo.put("location", location);
            jsonArray.add(jo);
        }
        JSONObject result = new JSONObject();
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "当天某种类型所有人员的巡查轨迹", notes = "根据分类返回当天该类别的所有人员的轨迹", produces = "application/json")
    @RequestMapping(value = "/gettrailtoday", method = RequestMethod.POST)
    @ApiImplicitParam(name = "type", value = "巡查类型", paramType = "query", required = true, dataType = "String")
    public JSONObject getTrailToday(String type) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        List<Trail> peopleList = trailMapper.selectPeople(df.format(new Date()), type);
        List<Trail> peopleList = trailMapper.selectPeople("2020-12-07", type);
        for (Trail list : peopleList) {
//            List<Trail> trailList = trailMapper.selectTrail(df.format(new Date()), type, list.getName());
            List<Trail> trailList = trailMapper.selectTrail("2020-12-07", type, list.getName());
            JSONObject jo = new JSONObject();
            JSONArray trail = new JSONArray();
            for (Trail llist : trailList) {
                JSONArray location = new JSONArray();
                location.add(llist.getLon());
                location.add(llist.getLat());
                trail.add(location);
            }
            jo.put("name", list.getName());
            jo.put("trail", trail);
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "巡查历史轨迹--人员", notes = "根据时间、分类返回可选人员", produces = "application/json")
    @RequestMapping(value = "/gettrailperson", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询条件    {\"type\":\"机电\",\"time\":\"2020-09-10\"}", paramType = "body", required = true)
    public JSONObject getTrailPeople(@RequestBody JSONObject condition) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        List<Trail> peopleList = trailMapper.selectPeople(condition.getString("time"), condition.getString("type"));
        for (Trail list : peopleList) {
            jsonArray.add(list.getName());
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "巡查历史轨迹--轨迹", notes = "返回某一类型所选人员某个时间的巡查轨迹", produces = "application/json")
    @RequestMapping(value = "/gettrail", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询条件（时间、类型必须，如果人员为空，则返回该类型该时间全部人员的轨迹）   {\"names\":[\"龙马\",\"唐山\"],\"type\":\"机电\",\"time\":\"2020-09-10\"}", paramType = "body", required = true)
    public JSONObject getTrail(@RequestBody JSONObject condition) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(condition.get("names")!=null&&JSONArray.parseArray(JSONObject.toJSONString(condition.get("names"))).size() > 0){
            JSONArray arr = JSONArray.parseArray(JSONObject.toJSONString(condition.get("names")));
            for (int i = 0; i < arr.size(); i++) {
                List<Trail> trailList = trailMapper.selectTrail(condition.getString("time"), condition.getString("type"), arr.getString(i));
                JSONObject jo = new JSONObject();
                JSONArray trail = new JSONArray();
                for (Trail list : trailList) {
                    JSONArray location = new JSONArray();
                    location.add(list.getLon());
                    location.add(list.getLat());
                    trail.add(location);
                }
                jo.put("name", arr.getString(i));
                jo.put("trail", trail);
                jsonArray.add(jo);
            }
        }else{
            List<Trail> peopleList = trailMapper.selectPeople(condition.getString("time"), condition.getString("type"));
            for(Trail people : peopleList){
                List<Trail> trailList = trailMapper.selectTrail(condition.getString("time"), condition.getString("type"), people.getName());
                JSONObject jo = new JSONObject();
                JSONArray trail = new JSONArray();
                for (Trail list : trailList) {
                    JSONArray location = new JSONArray();
                    location.add(list.getLon());
                    location.add(list.getLat());
                    trail.add(location);
                }
                jo.put("name", people.getName());
                jo.put("trail", trail);
                jsonArray.add(jo);
            }
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "台账查询", notes = "返回台账查询结果", produces = "application/json")
    @RequestMapping(value = "/getSearch", method = RequestMethod.POST)
    @ApiImplicitParam(name = "condition", value = "查询条件（时间、类型必须，航飞按月查询，其它按天查询。 作业task, 轨迹trail，航飞flight, 事件上报event ）   {\"type\":\"trail\",\"time\":\"2020-12-01\"}   ,    {\"type\":\"flight\",\"time\":\"2020-12\"}", paramType = "body", required = true)
    public JSONObject getSearch(@RequestBody JSONObject condition) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(condition.get("type")!=null&&condition.get("time")!=null&&!condition.getString("type").equals("")&&!condition.getString("time").equals("")){
            String type = condition.getString("type");
            String time = condition.getString("time");
            switch (type){
                case "task" : List<Task> taskList = taskMapper.selectByTime(time);
                for(Task list : taskList){
                    JSONObject jo = new JSONObject();
                    JSONArray location = new JSONArray();
                    location.add(list.getLon());
                    location.add(list.getLat());
                    jo.put("location", location);
                    jo.put("name", list.getName());
                    jo.put("type", list.getType());
                    jo.put("id", list.getId());
                    jsonArray.add(jo);
                }
                break;
                case "event" : List<Event> eventList = eventMapper.selectByTime(time);
                    for(Event list : eventList){
                        JSONObject joo = new JSONObject();
                        JSONArray location = new JSONArray();
                        location.add(list.getLon());
                        location.add(list.getLat());
                        joo.put("location", location);
                        joo.put("name", list.getName());
                        joo.put("type", list.getType());
                        joo.put("id", list.getId());
                        jsonArray.add(joo);
                    }
                    break;
                case "trail" :  List<Trail> peopleList = trailMapper.selectPeopleByTime(time);
                    for(Trail people : peopleList){
                        List<Trail> trailList = trailMapper.selectByTime(time, people.getName());
                        JSONObject joooo = new JSONObject();
                        JSONArray trail = new JSONArray();
                        for (Trail list : trailList) {
                            JSONArray location = new JSONArray();
                            location.add(list.getLon());
                            location.add(list.getLat());
                            trail.add(location);
                        }
                        joooo.put("name", people.getName());
                        joooo.put("type", people.getType());
                        joooo.put("trail", trail);
                        jsonArray.add(joooo);
                    }
                    break;
                case "flight" : List<Flight> flightList = flightMapper.selectByTime(time);
                    for(Flight list : flightList){
                        JSONObject joooo = new JSONObject();
                        JSONArray location = new JSONArray();
                        location.add(list.getLon());
                        location.add(list.getLat());
                        joooo.put("location", location);
                        joooo.put("name", list.getPoint());
                        joooo.put("id", list.getId());
                        joooo.put("path", list.getPath());
                        jsonArray.add(joooo);
                    }
                    break;
                    default: break;

            }

            result.put("data", jsonArray);
            result.put("code", 1);
        }else{
            result.put("data", "参数不能为空");
            result.put("code", 0);
        }
        return result;
    }


}
