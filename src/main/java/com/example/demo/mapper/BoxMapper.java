package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Box;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoxMapper extends BaseMapper<Box> {
    @Select("SELECT * FROM box")
    List<Box> selectAll();
}
