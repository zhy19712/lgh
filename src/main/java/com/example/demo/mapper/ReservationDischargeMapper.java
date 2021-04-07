package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.ReservationDischarge;
import com.example.demo.entity.WetlandRt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReservationDischargeMapper extends BaseMapper<ReservationDischarge> {
    @Select("SELECT * FROM reservation_discharge ")
    List<ReservationDischarge> selectAll();


    @Select("SELECT * FROM reservation_discharge WHERE name = #{name} ")
    List<ReservationDischarge> selectByName(@Param("name")String name);


}
