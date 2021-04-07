package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterClearRt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterClearRtMapper extends BaseMapper<WaterClearRt> {
    @Select("SELECT * FROM water_clear_rt WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(time) AND name = #{name}")
    List<WaterClearRt> select30DayByName(@Param("name")String name);
}
