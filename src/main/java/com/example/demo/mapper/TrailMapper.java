package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Task;
import com.example.demo.entity.Trail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TrailMapper extends BaseMapper<Trail> {
    //获取某天某类巡查人员
    @Select("SELECT DISTINCT name FROM trail WHERE to_days(time) = to_days(#{time}) AND type = #{type}")
    List<Trail> selectPeople(@Param("time") String time, @Param("type") String type);


    //获取某人某天轨迹
    @Select("SELECT * FROM trail WHERE to_days(time) = to_days(#{time}) AND name = #{name} AND type = #{type}")
    List<Trail> selectTrail(@Param("time") String time, @Param("type") String type, @Param("name") String name);

    //获取某类全部人员某天轨迹
    @Select("SELECT * FROM trail WHERE type = #{type} AND time = #{time}")
    List<Trail> selectTrailAll(@Param("time") String time, @Param("type") String type);


//    //获取当天某种类型的全部巡查轨迹
//    @Select("SELECT * FROM trail WHERE to_days(time) = to_days(now()) AND type = #{type}")
//    List<Trail> selectTrailToday(@Param("type") String type);

    //获取当天某种类型的全部巡查轨迹
    @Select("SELECT * FROM trail WHERE to_days(time) = to_days(now()) AND type = #{type}")
    List<Trail> selectTrailToday(@Param("type") String type);

    //获取某天巡查人员
    @Select("SELECT DISTINCT name, type FROM trail WHERE to_days(time) = to_days(#{time})")
    List<Trail> selectPeopleByTime(@Param("time") String time);


    //获取某人某天的全部轨迹信息
    @Select("SELECT * FROM trail WHERE to_days(time) = to_days(#{time}) AND name = #{name}")
    List<Trail> selectByTime(@Param("time") String time, @Param("name")String name);

}
