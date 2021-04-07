package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Clock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ClockMapper extends BaseMapper<Clock> {
    //当天某类型人员上班人数
//    @Select("SELECT COUNT(clock.name) FROM clock JOIN user ON clock.name = user.name WHERE user.department = #{type} AND clock.type = '上班' AND to_days(clock.time) = to_days(now())")
//    int selectClockedCountByType(@Param("type") String type);

    @Select("SELECT COUNT(clock.name) FROM clock JOIN user ON clock.name = user.name WHERE user.department = #{type} AND clock.type = '上班' AND to_days(clock.time) = to_days('2020-12-07')")
    int selectClockedCountByType(@Param("type") String type);

    //本月某类型人员上班（打卡）次数
//    @Select("SELECT COUNT(clock.name) FROM clock JOIN user ON clock.name = user.name WHERE user.department = #{type} AND clock.type = '上班' AND DATE_FORMAT(clock.time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m')")
//    int selectMonthClockedCountByType(@Param("type")String type);

    @Select("SELECT COUNT(clock.name) FROM clock JOIN user ON clock.name = user.name WHERE user.department = #{type} AND clock.type = '上班' AND clock.time like '%2020-12%'")
    int selectMonthClockedCountByType(@Param("type")String type);
}
