package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Device;
import com.example.demo.entity.Shelter;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeviceMapper extends BaseMapper<Device> {
    @Select("SELECT * FROM device")
    List<Device> selectAll();
}
