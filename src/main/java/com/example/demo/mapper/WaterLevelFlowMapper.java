package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterLevelFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterLevelFlowMapper  extends BaseMapper<WaterLevelFlow> {
    @Select("SELECT * FROM water_level_flow WHERE name = #{name}")
    WaterLevelFlow selectByName(@Param("name")String name);

    @Select("SELECT * FROM water_level_flow")
    List<WaterLevelFlow> selectAll();
}
