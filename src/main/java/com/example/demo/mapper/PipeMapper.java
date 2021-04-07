package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Pipe;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PipeMapper extends BaseMapper<Pipe> {
    @Select("SELECT * FROM pipe WHERE type = #{type}")
    List<Pipe>  selectAllByType(@Param("type")String type);

    @Select("SELECT * FROM pipe")
    List<Pipe>  selectAll();
}
