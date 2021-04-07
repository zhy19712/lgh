package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Camera;
import com.example.demo.entity.FloodPoint;
import com.example.demo.entity.Shelter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShelterMapper extends BaseMapper<Shelter> {
    @Select("SELECT * FROM shelter")
    List<Shelter> selectAll();

    @Select("SELECT * FROM shelter WHERE name = #{name}")
    Shelter selectByName(@Param("name")String name);
}
