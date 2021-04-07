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

import java.text.DecimalFormat;
import java.util.List;

@Api(tags = "设施--基础信息（河流、调蓄池、水库）")
@RestController
@RequestMapping("/api/basicd")
public class BasicDController {
    @Autowired
    private RiverMapper riverMapper;

    @Autowired
    private TankMapper tankMapper;

    @Autowired
    private TankRtMapper tankRtMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ReservationDischargeMapper reservationDischargeMapper;


    @ApiOperation(value = "所选河流详细信息", notes = "加载所选河流的详细信息", produces = "application/json")
    @RequestMapping(value = "/riverdetail", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "河流名称", paramType = "query", required = true, dataType = "String")
    public JSONObject riverDetail(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        River river = riverMapper.selectByName(name);
        if(river != null) {
            JSONObject jo = new JSONObject();
            jo.put("water_standard", river.getWaterStandard());
            jo.put("detail", river.getDetail());
            jo.put("flood_standard", river.getFloodStandard());
            jo.put("name", river.getName());
            jo.put("id", river.getId());
            jo.put("basin", river.getBasin());
            jo.put("stage", river.getStage());
            jo.put("owner", river.getOwner());
            jo.put("river_length", river.getRiverLenght());
            jo.put("black_length", river.getBlackLenght());
            jo.put("hidden_length", river.getHiddenLenght());
            jo.put("good_length", river.getGoodLength());
            jo.put("bad_length", river.getBadlength());
            jo.put("basin_area", river.getBasinArea());
            jo.put("type", river.getType());
            jo.put("ave_flow", river.getAveFlow());
            jo.put("upper_river", river.getUpperRiver());
            jo.put("start", river.getStart());
            jo.put("end", river.getEnd());
            jo.put("manager", river.getManager());
            jo.put("maintainer", river.getMaintainer());
            jo.put("data_type", river.getDataType());
            jo.put("district", river.getDistrict());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "河流统计信息", notes = "返回河流统计信息", produces = "application/json")
    @RequestMapping(value = "/riverstatistics", method = RequestMethod.GET)
    public JSONObject riverStatistics() {
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        JSONObject result = new JSONObject();
        //干流 1
        //支流
        int count_1 = riverMapper.selectCountByStage("一级支流");
        int count_2 = riverMapper.selectCountByStage("二级支流");
        int count_3 = riverMapper.selectCountByStage("三级支流");
        int count_4 = riverMapper.selectCountByStage("四级支流");
        int count_independent = riverMapper.selectCountByStage("独立支流");
        int total = count_1 + count_2 + count_3 + count_4 + count_independent + 1;

        double goodlength_main = riverMapper.selectGoodLengthTotalByStage("干流");
        double goodlength_1 = riverMapper.selectGoodLengthTotalByStage("一级支流");
        double goodlength_2 = riverMapper.selectGoodLengthTotalByStage("二级支流");
        double goodlength_3 = riverMapper.selectGoodLengthTotalByStage("三级支流");
        double goodlength_4 = riverMapper.selectGoodLengthTotalByStage("四级支流");

        double badlength_main = riverMapper.selectBadLengthTotalByStage("干流");
        double badlength_1 = riverMapper.selectBadLengthTotalByStage("一级支流");
        double badlength_2 = riverMapper.selectBadLengthTotalByStage("二级支流");
        double badlength_3 = riverMapper.selectBadLengthTotalByStage("三级支流");
        double badlength_4 = riverMapper.selectBadLengthTotalByStage("四级支流");

        JSONObject j0 = new JSONObject();
        j0.put("name", "总数");
        j0.put("count", total);
        JSONObject j1 = new JSONObject();
        j1.put("name", "一级支流");
        j1.put("count", count_1);
        JSONObject j2 = new JSONObject();
        j2.put("name", "二级支流");
        j2.put("count", count_2);
        JSONObject j3 = new JSONObject();
        j3.put("name", "三级支流");
        j3.put("count", count_3);
        JSONObject j4 = new JSONObject();
        j4.put("name", "四级支流");
        j4.put("count", count_4);
        jsonArray.add(j0);
        jsonArray.add(j1);
        jsonArray.add(j2);
        jsonArray.add(j3);
        jsonArray.add(j4);

        JSONObject g0 = new JSONObject();
        g0.put("name", "干流");
        g0.put("good_length", goodlength_main);
        g0.put("bad_length", badlength_main);
        JSONObject g1 = new JSONObject();
        g1.put("name", "一级支流");
        g1.put("good_length", goodlength_1);
        g1.put("bad_length", badlength_1);
        JSONObject g2 = new JSONObject();
        g2.put("name", "二级支流");
        g2.put("good_length", goodlength_2);
        g2.put("bad_length", badlength_2);
        JSONObject g3 = new JSONObject();
        g3.put("name", "三级支流");
        g3.put("good_length", goodlength_3);
        g3.put("bad_length", badlength_3);
        JSONObject g4 = new JSONObject();
        g4.put("name", "四级支流");
        g4.put("good_length", goodlength_4);
        g4.put("bad_length", badlength_4);
        jsonArray2.add(g0);
        jsonArray2.add(g1);
        jsonArray2.add(g2);
        jsonArray2.add(g3);
        jsonArray2.add(g4);

        result.put("stream", jsonArray);
        result.put("length", jsonArray2);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "调蓄池--详情", notes = "返回所选调蓄池基本信息", produces = "application/json")
    @RequestMapping(value = "/tankbyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "调蓄池名称", paramType = "query", required = true, dataType = "String")
    public JSONObject tankByName(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        Tank tank  = tankMapper.selectByName(name);
            if (tank != null) {
                JSONObject jo = new JSONObject();
                jo.put("name", tank.getName());
                jo.put("location", tank.getLocation());
                jo.put("level", tank.getLevel());
                jo.put("top_height", tank.getHeightRoof());
                jo.put("bottom_height", tank.getHeightBottom());
                jo.put("water_in", tank.getWaterIn());
                jo.put("water_out", tank.getWaterOut());
                jo.put("water_go", tank.getWaterGo());
                jo.put("standard", tank.getStandard());
                jo.put("district", tank.getDistrict());
                jo.put("run", tank.getRun());
                jo.put("manager", tank.getManager());
                jo.put("builder", tank.getBuilder());
                jo.put("time", tank.getTime());
                jo.put("call", tank.getCallDry()+tank.getCallSmallrain()+tank.getCallBigrain()+tank.getCallAfterrain());
                jsonArray.add(jo);
            }

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "调蓄池统计", notes = "返回当天统计信息", produces = "application/json")
    @GetMapping("/tankstatistics")
    public JSONObject tankStatistics() {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        int count = tankMapper.selectCount();
        double standard = tankMapper.selectStandardSum();
        int use = tankRtMapper.selectCountToday();
        double waterIn = tankRtMapper.selectWaterInSumToday();
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        jo.put("standard", standard);
        jo.put("use", use);
        jo.put("waterIn", waterIn);
        jsonArray.add(jo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "所选调蓄池近一个月监测数据", notes = "返回所选调蓄池30天内监测信息", produces = "application/json")
    @RequestMapping(value = "/tank30daybyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "调蓄池名称", paramType = "query", required = true, dataType = "String")
    public JSONObject tank30DayByNmae(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        List<TankRt> tankRtList  = tankRtMapper.select30DayByName(name);
        for (TankRt list : tankRtList) {
            JSONObject jo = new JSONObject();
            jo.put("name", list.getName());
            jo.put("waterIn", list.getWaterIn());
            jo.put("level", list.getLevel());
            jo.put("flow", list.getFlow());
            jo.put("num", list.getNum());
            jo.put("time", list.getTime());
            jsonArray.add(jo);
        }
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }


    @ApiOperation(value = "水库信息--详细", notes = "返回所选水库池详细信息", produces = "application/json")
    @RequestMapping(value = "/reservationbyname", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "水库名", paramType = "query", required = true, dataType = "String")
    public JSONObject reservationByName(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        Reservation reservation = reservationMapper.selectByName(name);
        JSONObject jo = new JSONObject();
        jo.put("id", reservation.getId());
        jo.put("name", reservation.getName());
        jo.put("checkedLevel", reservation.getCheckedLevel());
        jo.put("designedLevel", reservation.getDesignedLevel());
        jo.put("normalLevel", reservation.getNormalLevel());
        jo.put("floodLevel", reservation.getFloodLevel());
        jo.put("basinArea", reservation.getBasinArea());
        jo.put("model", reservation.getModel());
        jo.put("length", reservation.getLength());
        jo.put("height", reservation.getHeight());
        jo.put("area", reservation.getArea());
        jo.put("manager", reservation.getManager());
        jo.put("builder", reservation.getBuilder());
        jo.put("time", reservation.getTime());
        try {
            jo.put("location", JSONArray.parseArray(reservation.getLocation()));
        } catch (Exception e) {
            System.out.println(reservation.getId());
        }

        jsonArray.add(jo);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水位库容泄流曲线", notes = "水位库容泄流曲线，所有水库用一套数据", produces = "application/json")
    @RequestMapping(value = "/reservationdischarge", method = RequestMethod.POST)
    @ApiImplicitParam(name = "name", value = "水库名", paramType = "query", required = true, dataType = "String")
    public JSONObject reservationDischarge(String name) {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        List<ReservationDischarge> reservationDischargeList = reservationDischargeMapper.selectByName(name);
        if(reservationDischargeList.size() == 0){
            name = "正坑水库";
            reservationDischargeList = reservationDischargeMapper.selectByName(name);
        }

        JSONArray levelDischarge = new JSONArray();
        JSONArray levelVolumn = new JSONArray();
        for (ReservationDischarge list : reservationDischargeList) {
            JSONObject jo = new JSONObject();
            JSONObject joo = new JSONObject();
//            DecimalFormat df = new DecimalFormat("#.00");
//            jo.put("volumn", df.format(list.getVolumn()/10000));

            if(list.getDischarge()==0){
                jo.put("level", list.getLevel());
                jo.put("volumn", list.getVolumn());
                levelVolumn.add(jo);
            }
            if(list.getVolumn()==0){
                joo.put("level", list.getLevel());
                joo.put("discharge", list.getDischarge());
                levelDischarge.add(joo);
            }
        }
        jsonArray.add(levelVolumn);
        jsonArray.add(levelDischarge);
        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }

    @ApiOperation(value = "水库信息--统计", notes = "加载所有水库名称以及坐标", produces = "application/json")
    @GetMapping("/reservationstatistics")
    public JSONObject reservationStatistics() {
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
//        int bigCount = reservationMapper.selectCountByStandard("大型");
//        int midCount = reservationMapper.selectCountByStandard("中型");
//        int smallCount1 = reservationMapper.selectCountByStandard("小（1）");
//        int smallCount2 = reservationMapper.selectCountByStandard("小（2）");
        JSONObject j0 = new JSONObject();
        JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();
        JSONObject j3 = new JSONObject();

        j0.put("name", "大型");
        j0.put("count", 1);
        j0.put("normal", 1);
        j0.put("exceed", 0);
        j0.put("discharge", 0);

        j1.put("name", "中型");
        j1.put("count", 4);
        j1.put("normal", 2);
        j1.put("exceed", 1);
        j1.put("discharge", 1);

        j2.put("name", "小（1）");
        j2.put("count", 12);
        j2.put("normal", 7);
        j2.put("exceed", 3);
        j2.put("discharge", 2);

        j3.put("name", "小（2）");
        j3.put("count", 10);
        j3.put("normal", 4);
        j3.put("exceed", 4);
        j3.put("discharge", 2);

        jsonArray.add(j0);
        jsonArray.add(j1);
        jsonArray.add(j2);
        jsonArray.add(j3);

        result.put("data", jsonArray);
        result.put("code", 1);
        return result;
    }




}
