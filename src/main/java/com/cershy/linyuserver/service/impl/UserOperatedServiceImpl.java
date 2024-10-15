package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.admin.vo.statistic.LoginDetailsVo;
import com.cershy.linyuserver.constant.UserOperatedType;
import com.cershy.linyuserver.dto.UserOperatedDto;
import com.cershy.linyuserver.entity.UserOperated;
import com.cershy.linyuserver.mapper.UserOperatedMapper;
import com.cershy.linyuserver.service.UserOperatedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserOperatedServiceImpl extends ServiceImpl<UserOperatedMapper, UserOperated> implements UserOperatedService {

    @Resource
    UserOperatedMapper userOperatedMapper;

    @Override
    public boolean recordLogin(String id, String ip) {
        UserOperated operated = new UserOperated();
        operated.setId(IdUtil.randomUUID());
        operated.setUserId(id);
        operated.setContent(ip);
        operated.setType(UserOperatedType.Login);
        return save(operated);
    }

    @Override
    public List<UserOperatedDto> loginDetails(LoginDetailsVo loginDetailsVo) {
        List<UserOperatedDto> result = userOperatedMapper.loginDetails(loginDetailsVo.getIndex(), loginDetailsVo.getNum(), loginDetailsVo.getKeyword());
        return result;
    }
}
