package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.FloodPoint;
import com.example.demo.entity.Rain;
import com.example.demo.entity.Tank;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FloodPointMapper extends BaseMapper<FloodPoint> {
    @Select("SELECT * FROM flood_point")
    List<FloodPoint> selectAll();

    @Select("SELECT * FROM flood_point WHERE name = #{name}")
    FloodPoint selectByName(@Param("name")String name);
}
