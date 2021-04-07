package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterLevel;
import com.example.demo.entity.WetlandRt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WetlandRtMapper extends BaseMapper<WetlandRt> {
    @Select("SELECT * FROM wetland_rt WHERE time < '2099-09-29 19:39:39' ORDER BY time DESC LIMIT 1")
    WetlandRt selectNewestFlowIn();

}
