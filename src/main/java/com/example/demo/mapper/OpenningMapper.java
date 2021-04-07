package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Openning;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpenningMapper extends BaseMapper<Openning> {
    @Select("SELECT * FROM openning WHERE name = #{name} ORDER BY flow ASC")
    List<Openning> selectByName(@Param("name") String name);

}
