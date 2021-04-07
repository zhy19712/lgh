package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Flight;
import com.example.demo.entity.Trail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FlightMapper extends BaseMapper<Flight> {
    //某月所有点位
    @Select("SELECT DISTINCT point, lon, lat FROM flight WHERE DATE_FORMAT(time,'%Y-%m') = #{time} ")
    List<Flight> selectPointByMonth(@Param("time") String time);

    //某点某月视频
    @Select("SELECT * FROM flight WHERE DATE_FORMAT(time,'%Y-%m') = #{time} AND point = #{point}")
    List<Flight> selectVideoByPointAndMonth(@Param("point")String point, @Param("time")String time);

    //全部点某月视频
    @Select("SELECT * FROM flight WHERE DATE_FORMAT(time,'%Y-%m') = #{time}")
    List<Flight> selectVideoByMonth(@Param("time")String time);

    //获取某天的全部航飞记录
    @Select("SELECT * FROM flight WHERE DATE_FORMAT(time,'%Y-%m') = #{time}")
    List<Flight> selectByTime(@Param("time")String time);
}
