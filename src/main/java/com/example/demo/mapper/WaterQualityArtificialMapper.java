package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.WaterQualityArtificial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WaterQualityArtificialMapper extends BaseMapper<WaterQualityArtificial> {
    @Select("SELECT q.name, MAX(DISTINCT time) AS time, codcr, ammonia, phosphorous, ph, lon, lat FROM water_quality_artificial AS a JOIN water_quality AS q ON a.name = q.name WHERE q.name = #{name}")
    List<WaterQualityArtificial> selectByMyWrapper(@Param("name") String name);

    @Select("SELECT name, time, codcr, ammonia, phosphorous, ph FROM water_quality_artificial WHERE name = #{name} ORDER BY time DESC LIMIT 100")
    List<WaterQualityArtificial> selectByName(@Param("name") String name);
}
