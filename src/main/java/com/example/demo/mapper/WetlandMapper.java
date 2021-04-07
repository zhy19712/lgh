package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Wetland;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WetlandMapper extends BaseMapper<Wetland> {
    @Select("SELECT COUNT(*) FROM wetland")
    int selectCount();

    @Select("SELECT IFNULL(SUM(standard),0) FROM wetland")
    double selectSumStandard();

    @Select("SELECT * FROM wetland")
    List<Wetland> selectAll();

    @Select("SELECT * FROM wetland WHERE name = #{name}")
    Wetland selectByName(@Param("name")String name);


}
