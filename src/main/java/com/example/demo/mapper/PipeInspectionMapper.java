package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Pipe;
import com.example.demo.entity.PipeInspection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PipeInspectionMapper extends BaseMapper<PipeInspection> {
    @Select("SELECT * FROM pipe_inspection WHERE type = #{type}")
    List<PipeInspection>  selectAllByType(@Param("type")String type);

    @Select("SELECT * FROM pipe")
    List<PipeInspection>  selectAll();
}
