package com.cershy.linyuserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.admin.vo.statistic.LoginDetailsVo;
import com.cershy.linyuserver.dto.NumInfoDto;
import com.cershy.linyuserver.dto.UserOperatedDto;
import com.cershy.linyuserver.entity.UserOperated;

import java.util.Date;
import java.util.List;

public interface UserOperatedService extends IService<UserOperated> {
    boolean recordLogin(String id, String ip);

    List<UserOperatedDto> loginDetails(LoginDetailsVo loginDetailsVo);

    Integer uniqueLoginNum(Date date);
}
