package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.River;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RiverMapper extends BaseMapper<RiverMapper> {
    @Select("SELECT * FROM river WHERE name != '流域'")
    List<River> selectAll();

    @Select("SELECT * FROM river WHERE name = #{name}")
    River  selectByName(@Param("name")String name);

    @Select("SELECT COUNT(*) FROM river WHERE stage = #{stage}")
    int selectCountByStage(@Param("stage")String stage);

    @Select("SELECT SUM(good_length) FROM river WHERE stage = #{stage}")
    double selectGoodLengthTotalByStage(@Param("stage")String stage);

    @Select("SELECT SUM(bad_length) FROM river WHERE stage = #{stage}")
    double selectBadLengthTotalByStage(@Param("stage")String stage);

}
