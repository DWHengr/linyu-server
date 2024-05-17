package com.cershy.linyuserver.service;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.login.LoginVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
public interface UserService extends IService<User> {
    JSONObject validateLogin(LoginVo loginVo);
}
