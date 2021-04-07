package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterLevel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterLevelMapper extends BaseMapper<WaterLevel> {
    @Select("SELECT s.name, MAX(DISTINCT time) AS time, waterlevel, flow, lon, lat FROM water_level as l JOIN water_section as s ON s.name = l.name WHERE s.name = #{name}")
    List<WaterLevel> selectByMyWrapper(@Param("name") String name);

    @Select("SELECT name, time, waterlevel, flow FROM water_level WHERE name = #{name} ORDER BY time DESC LIMIT 100")
    List<WaterLevel> selectByName(@Param("name") String name);

    @Select("SELECT name, time, waterlevel, flow FROM water_level WHERE name = #{name} ORDER BY time DESC LIMIT 1")
    WaterLevel selectRtByName(@Param("name") String name);


}
