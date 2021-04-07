package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.River;
import com.example.demo.entity.Street;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StreetMapper extends BaseMapper<Street> {
    @Select("SELECT * FROM street")
    List<Street> selectAll();
}
