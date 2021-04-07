package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterSection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterSectionMapper extends BaseMapper<WaterSection> {
    @Select("SELECT * FROM water_section WHERE belong = #{belong}")
    List<WaterSection> selectSection(@Param("belong") String belong);

    @Select("SELECT * FROM water_section")
    List<WaterSection> selectAll();

    @Select("SELECT * FROM water_section WHERE name=#{name}")
    WaterSection selectByName(@Param("name")String name);

}
