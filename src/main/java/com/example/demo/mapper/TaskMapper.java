package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Event;
import com.example.demo.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.annotation.security.PermitAll;
import java.util.List;

public interface TaskMapper extends BaseMapper<Task> {

    //获取某月某种任务次数
    @Select("SELECT COUNT(*) FROM task WHERE DATE_FORMAT(time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m') AND type = #{type}")
    int selectCount(String type);

    //获取当天全部巡查任务信息
//    @Select("SELECT * FROM task WHERE to_days(time) = to_days(now())")
//    List<Task> selectToday();

    //获取当天全部巡查任务信息
    @Select("SELECT * FROM task WHERE to_days(time) = to_days('2020-12-07')")
    List<Task> selectToday();

    //获取当天某类型的全部巡查任务信息
//    @Select("SELECT * FROM task WHERE to_days(time) = to_days(now()) AND type = #{type}")
//    List<Task> selectTodayByType(@Param("type")String type);

    //获取当天某类型的全部巡查任务信息
    @Select("SELECT * FROM task WHERE type = #{type} AND to_days(time) = to_days('2020-12-07')")
    List<Task> selectTodayByType(@Param("type")String type);

    //获取所选当天巡查任务信息
//    @Select("SELECT * FROM task WHERE to_days(time) = to_days(now()) AND id = #{id}")
//    Task selectTodayById(@Param("id")int id);

    //获取所选当天巡查任务信息
    @Select("SELECT * FROM task WHERE id = #{id} AND to_days(time) = to_days('2020-12-07')")
    Task selectTodayById(@Param("id")int id);

    //获取近7天每天巡查信息
//    @Select("SELECT * FROM task WHERE date_sub(curdate(), INTERVAL 7 DAY) <= date(time) AND time < #{time} AND type = #{type} UNION SELECT * FROM task WHERE  to_days(time) = to_days(#{time}) AND type = #{type}")
//    List<Task> select7DayByType(@Param("time")String time, @Param("type")String type);

    @Select("SELECT * FROM task WHERE date_sub(#{time}, INTERVAL 7 DAY) <= date(time) AND time < #{time} AND type = #{type} UNION SELECT * FROM task WHERE  to_days(time) = to_days(#{time}) AND type = #{type}")
    List<Task> select7DayByType(@Param("time")String time, @Param("type")String type);

    //获取近7天每天巡查信息
//    @Select("SELECT * FROM task WHERE date_sub(curdate(), INTERVAL 7 DAY) <= date(time) AND time < #{time} UNION SELECT * FROM task WHERE  to_days(time) = to_days(#{time})")
//    List<Task> select7Day(@Param("time")String time);

    @Select("SELECT * FROM task WHERE date_sub(curdate(), INTERVAL 7 DAY) <= date(time) AND time < #{time} UNION SELECT * FROM task WHERE  to_days(time) = to_days(#{time})")
    List<Task> select7Day(@Param("time")String time);

    //获取某天的全部巡查任务信息
    @Select("SELECT * FROM task WHERE to_days(time) = to_days(#{time})")
    List<Task> selectByTime(@Param("time")String time);

}
