package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.River;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReservationMapper extends BaseMapper<Reservation> {
    @Select("SELECT * FROM reservation")
    List<Reservation> selectAll();

    @Select("SELECT * FROM reservation WHERE name = #{name}")
    Reservation selectByName(@Param("name")String name);

    @Select("SELECT COUNT(*) FROM reservation WHERE standard = #{standard}")
    int selectCountByStandard(@Param("standard")String standard);

}
