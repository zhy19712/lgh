package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Camera;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CameraMapper extends BaseMapper<Camera> {
    @Select("SELECT * FROM camera")
    List<Camera> selectAll();

    @Select("SELECT * FROM camera WHERE name = #{name}")
    Camera selectByName(@Param("name")String name);
}
