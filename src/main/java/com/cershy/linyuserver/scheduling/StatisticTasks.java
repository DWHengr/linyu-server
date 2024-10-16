package com.cershy.linyuserver.scheduling;

import cn.hutool.core.date.DateUtil;
import com.cershy.linyuserver.service.StatisticService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StatisticTasks {

    @Resource
    StatisticService statisticService;

    @Scheduled(cron = "0 2 0 * * ?")
    public void statisticLoginNum() {
        statisticService.statisticLoginNum(DateUtil.yesterday());
    }
}
