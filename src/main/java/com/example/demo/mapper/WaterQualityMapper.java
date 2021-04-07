package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterQuality;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterQualityMapper extends BaseMapper<WaterQuality> {
    @Select("SELECT * FROM water_quality WHERE belong = #{belong}")
    List<WaterQuality> selectPoint(@Param("belong") String belong);

    @Select("SELECT * FROM water_quality")
    List<WaterQuality> selectAll();


    @Select("SELECT * FROM water_quality WHERE name = #{name}")
    WaterQuality selectByName(@Param("name")String name);
}
