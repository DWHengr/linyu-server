package com.cershy.linyuserver.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.dto.NumInfoDto;
import com.cershy.linyuserver.dto.Top10MsgDto;
import com.cershy.linyuserver.entity.Statistic;
import com.cershy.linyuserver.mapper.StatisticMapper;
import com.cershy.linyuserver.service.MessageService;
import com.cershy.linyuserver.service.StatisticService;
import com.cershy.linyuserver.service.UserOperatedService;
import com.cershy.linyuserver.service.WebSocketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StatisticServiceImpl extends ServiceImpl<StatisticMapper, Statistic> implements StatisticService {

    @Resource
    UserOperatedService userOperatedService;

    @Resource
    StatisticMapper statisticMapper;

    @Resource
    WebSocketService webSocketService;

    @Resource
    MessageService messageService;

    @Override
    public void statisticLoginNum(Date yesterday) {
        Integer loginNum = userOperatedService.uniqueLoginNum(yesterday);
        LambdaQueryWrapper<Statistic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Statistic::getDate, yesterday);
        Statistic statistic = getOne(queryWrapper);
        if (null == statistic) {
            statistic = new Statistic();
            statistic.setId(IdUtil.randomUUID());
        }
        statistic.setDate(yesterday);
        statistic.setLoginNum(loginNum);
        saveOrUpdate(statistic);
    }


    public List<Statistic> getStatisticList(int day) {
        List<Statistic> result = statisticMapper.getStatisticList(day);
        return result;
    }

    @Override
    public NumInfoDto numInfo() {
        DateTime date = DateUtil.parseDate(DateUtil.today());
        List<Statistic> statisticList = getStatisticList(7);
        NumInfoDto numInfoDto = new NumInfoDto();
        numInfoDto.setStatistics(statisticList);
        numInfoDto.setLoginNum(userOperatedService.uniqueLoginNum(date));
        numInfoDto.setMsgNum(messageService.messageNum(date));
        numInfoDto.setOnlineNum(webSocketService.getOnlineNum());
        return numInfoDto;
    }

    @Override
    public List<Top10MsgDto> top10Msg() {
        List<Top10MsgDto> result = messageService.getTop10Msg(new Date());
        return result;
    }
}
