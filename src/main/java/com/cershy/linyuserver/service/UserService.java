package com.cershy.linyuserver.service;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.user.SearchUserVo;

import java.util.HashMap;
import java.util.List;

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

    List<UserDto> searchUser(SearchUserVo searchUserVo);

    HashMap<String, Integer> unreadInfo(String userId);

    UserDto info(String userId);
}
