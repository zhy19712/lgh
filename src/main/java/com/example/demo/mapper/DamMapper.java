package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Dam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DamMapper extends BaseMapper<Dam> {
    @Select("SELECT * FROM dam")
    List<Dam> selectAll();

    @Select("SELECT COUNT(*) FROM dam WHERE type = #{type}")
    int selectCount(@Param("type")String type);

    @Select("SELECT COUNT(*) FROM dam WHERE type = #{type} AND arriving = #{arriving}")
    int selectArrivingCount(@Param("type")String type, @Param("arriving")int arriving);

    @Select("SELECT COUNT(*) FROM dam WHERE type = #{type} AND status = #{status}")
    int selectStatusCount(@Param("type")String type, @Param("status")int status);

    @Select("SELECT * FROM dam WHERE name = #{name}")
    Dam selectByName(@Param("name")String name);
}
