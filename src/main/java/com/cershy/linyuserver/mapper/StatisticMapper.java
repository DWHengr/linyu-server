package com.cershy.linyuserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cershy.linyuserver.entity.Statistic;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatisticMapper extends BaseMapper<Statistic> {

    @Select("SELECT * " +
            "FROM `statistic` " +
            "WHERE `date` >= DATE_SUB(CURDATE(), INTERVAL #{day} DAY) " +
            "  AND `date` <= CURDATE() " +
            "ORDER BY `date` ASC ")
    List<Statistic> getStatisticList(int day);
}
