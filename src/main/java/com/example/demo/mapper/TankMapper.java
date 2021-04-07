package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Tank;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TankMapper extends BaseMapper<TankMapper> {
    @Select("SELECT * FROM tank")
    List<Tank> selectAll();

    @Select("SELECT * FROM tank WHERE name = #{name}")
    Tank selectByName(@Param("name")String name);

    @Select("SELECT COUNT(*) FROM tank")
    int selectCount();

    @Select("SELECT SUM(standard) FROM tank")
    double selectStandardSum();


}
