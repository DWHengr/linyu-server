package com.cershy.linyuserver.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.dto.NumInfoDto;
import com.cershy.linyuserver.dto.Top10MsgDto;
import com.cershy.linyuserver.entity.Statistic;

import java.util.Date;
import java.util.List;

public interface StatisticService extends IService<Statistic> {
    void statisticLoginNum(Date yesterday);

    NumInfoDto numInfo();

    List<Top10MsgDto> top10Msg();
}
