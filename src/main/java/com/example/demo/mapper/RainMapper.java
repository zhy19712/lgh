package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Rain;
import com.example.demo.entity.Shelter;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RainMapper extends BaseMapper<Rain> {
    @Select("SELECT * FROM rain")
    List<Rain> selectAll();
}
