package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Pump;
import com.example.demo.entity.PumpRt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PumpMapper extends BaseMapper<Pump> {
    @Select("SELECT * FROM pump")
    List<Pump> selectAll();

    @Select("SELECT * FROM pump WHERE name = #{name}")
    Pump selectByName(@Param("name")String name);

    @Select("SELECT * FROM pump WHERE type = #{type}")
    List<Pump> selectByType(@Param("type")String type);

    @Select("SELECT COUNT(*) FROM pump WHERE type = #{type}")
    int selectCountByType(@Param("type")String type);
}
