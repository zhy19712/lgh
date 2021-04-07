package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PumpRt;
import org.apache.ibatis.annotations.Select;

public interface PumprtMapper extends BaseMapper<PumpRt> {
    @Select("SELECT * FROM pump_rt ORDER BY time DESC LIMIT 1")
    PumpRt selectNewest();
}
