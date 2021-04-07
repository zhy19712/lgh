package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.TankRt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TankRtMapper extends BaseMapper<TankRt> {
    @Select("SELECT IFNULL(SUM(water_in),0) FROM tank_rt WHERE to_days(time) = to_days(now())")
    double selectWaterInSumToday();

    @Select("SELECT COUNT(*) FROM tank_rt WHERE to_days(time) = to_days(now())")
    int selectCountToday();

    //所选调蓄池近30天实时数据
    @Select("SELECT * FROM tank_rt WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(time) AND name = #{name}")
    List<TankRt> select30DayByName(@Param("name")String name);
}
