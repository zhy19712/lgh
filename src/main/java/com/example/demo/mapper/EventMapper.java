package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Event;
import com.example.demo.entity.Task;
import com.example.demo.entity.Trail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EventMapper extends BaseMapper<Event> {
    //当天事件上报数
//    @Select("SELECT count(*) FROM event WHERE to_days(time) = to_days(now())")
//    int selectCount();

    @Select("SELECT count(*) FROM event WHERE to_days(time) = to_days('2020-12-07')")
    int selectCount();

    //当天大事件上报数
//    @Select("SELECT count(*) FROM event WHERE big_event = 1 AND to_days(time) = to_days(now())")
//    int selectBig_eventCount();

    @Select("SELECT count(*) FROM event WHERE big_event = 1 AND to_days(time) = to_days('2020-12-07')")
    int selectBig_eventCount();

    //当天事件整改数
//    @Select("SELECT count(*) FROM event WHERE status = '已整改' AND to_days(time) = to_days(now())")
//    int selectCompletion();

    @Select("SELECT count(*) FROM event WHERE status = '已整改' AND to_days(time) = to_days('2020-12-07')")
    int selectCompletion();

    //获取当天全部上报事件信息
//    @Select("SELECT * FROM event WHERE to_days(time) = to_days(now())")
//    List<Event> selectToday();

    @Select("SELECT * FROM event WHERE to_days(time) = to_days('2020-12-07') UNION SELECT * FROM event WHERE big_event = 1")
    List<Event> selectToday();


    //获取所选当天上报事件信息
//    @Select("SELECT * FROM event WHERE to_days(time) = to_days(now()) AND id = #{id}")
//    Event selectTodayById(@Param("id")int id);

    @Select("SELECT * FROM event WHERE id = #{id}")
    Event selectTodayById(@Param("id")int id);

    //获取近7天每天巡查信息
    @Select("select * FROM event WHERE date_sub(#{time}, INTERVAL 7 DAY) <= date(time) AND time < #{time} UNION SELECT * FROM event WHERE  to_days(time) = to_days(#{time})")
    List<Event> select7Day(@Param("time")String time);

    //获取某人某天上报事件
    @Select("SELECT * FROM event WHERE to_days(time) = to_days(#{time}) AND reporter = #{reporter}")
    List<Event> selectEvent(@Param("time") String time, @Param("reporter") String reporter);


    //获取某天的全部事件上报信息
    @Select("SELECT * FROM event WHERE to_days(time) = to_days(#{time})")
    List<Event> selectByTime(@Param("time")String time);

}
