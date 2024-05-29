package com.cershy.linyuserver.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.mapper.UserMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.utils.JwtUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.user.SearchUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    ChatListService chatListService;

    @Resource
    NotifyService notifyService;

    @Override
    public JSONObject validateLogin(LoginVo loginVo) {
        // 获取用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getAccount, loginVo.getAccount());
        User user = getOne(queryWrapper);
        if (null == user) {
            return ResultUtil.Fail("用户名或密码错误");
        }
        if (!user.getPassword().equals(loginVo.getPassword())) {
            return ResultUtil.Fail("用户名或密码错误");
        }
        JSONObject userinfo = new JSONObject();
        userinfo.put("userId", user.getId());
        userinfo.put("account", user.getAccount());
        userinfo.put("username", user.getName());
        userinfo.put("portrait", user.getPortrait());
        userinfo.put("phone", user.getPhone());
        userinfo.put("email", user.getEmail());
        //生成用户token
        userinfo.put("token", JwtUtil.createToken(userinfo));
        return ResultUtil.Succeed(userinfo);
    }

    @Override
    public List<User> searchUser(SearchUserVo searchUserVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, searchUserVo.getUserInfo())
                .or().eq(User::getPhone, searchUserVo.getUserInfo())
                .or().eq(User::getEmail, searchUserVo.getUserInfo());
        return list(queryWrapper);
    }

    @Override
    public HashMap<String, Integer> unreadInfo(String userId) {
        HashMap<String, Integer> unreadInfo = new HashMap<>();
        //获取消息未读数
        int msgNum = chatListService.unread(userId);
        //获取通知未读数
        int notifyNum = notifyService.unread(userId);
        unreadInfo.put("chat", msgNum);
        unreadInfo.put("notify", notifyNum);
        return unreadInfo;
    }
}
