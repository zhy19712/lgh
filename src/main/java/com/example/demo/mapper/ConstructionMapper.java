package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Construction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//涉河工程
public interface ConstructionMapper extends BaseMapper<ConstructionMapper> {
    @Select("SELECT COUNT(DISTINCT name) FROM construction")
    int selectCount();

    @Select("SELECT DISTINCT name, location FROM construction")
    List<Construction> selectAll();

    @Select("SELECT * FROM construction WHERE name = #{name}")
    List<Construction> selectByName(@Param("name")String name);
}
