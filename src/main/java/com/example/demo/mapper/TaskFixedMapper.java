package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.TaskFixed;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskFixedMapper extends BaseMapper<TaskFixed> {
    @Select("SELECT t.task, s.person, s.status, t.type, t.id, t.fixed FROM task_fixed AS t LEFT JOIN task_fixed_status AS s ON t.task = s.task  WHERE t.type = #{type}")
    List<TaskFixed> selectFixedTaskByType(@Param("type")String type);

    @Select("SELECT COUNT(*) FROM task_fixed WHERE type = #{type}")
    int selectCountByType(@Param("type") String type);


//    @Select("SELECT COUNT(*) FROM task_fixed JOIN task")
}
