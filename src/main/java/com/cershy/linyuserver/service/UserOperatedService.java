package com.cershy.linyuserver.service;

import com.cershy.linyuserver.admin.vo.statistic.LoginDetailsVo;
import com.cershy.linyuserver.dto.UserOperatedDto;

import java.util.List;

public interface UserOperatedService {
    boolean recordLogin(String id, String ip);

    List<UserOperatedDto> loginDetails(LoginDetailsVo loginDetailsVo);
}
