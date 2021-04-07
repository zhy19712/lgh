package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterClear;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterClearMapper extends BaseMapper<WaterClear> {
    @Select("SELECT COUNT(*) FROM wetland WHERE out_standard = #{out_standard}")
    int selectCountByStandard(@Param("out_standard")String out_standard);

    @Select("SELECT * FROM water_clear ")
    List<WaterClear> selectAll();

    @Select("SELECT * FROM water_clear WHERE name = #{name} ")
    WaterClear selectByName(@Param("name")String name);
}
