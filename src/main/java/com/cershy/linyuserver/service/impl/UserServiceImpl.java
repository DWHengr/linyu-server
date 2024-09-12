package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cershy.linyuserver.admin.vo.user.UserListVo;
import com.cershy.linyuserver.config.MinioConfig;
import com.cershy.linyuserver.constant.UserRole;
import com.cershy.linyuserver.constant.UserStatus;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.UserMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.utils.JwtUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.utils.SecurityUtil;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.user.RegisterVo;
import com.cershy.linyuserver.vo.user.SearchUserVo;
import com.cershy.linyuserver.vo.user.UpdateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    UserMapper userMapper;

    @Resource
    MinioConfig minioConfig;

    @Override
    public JSONObject validateLogin(LoginVo loginVo, boolean isAdmin) {
        // 获取用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getAccount, loginVo.getAccount());
        User user = getOne(queryWrapper);
        if (null == user) {
            return ResultUtil.Fail("用户名或密码错误~");
        }
        if (!SecurityUtil.verifyPassword(loginVo.getPassword(), user.getPassword())) {
            return ResultUtil.Fail("用户名或密码错误~");
        }
        if (isAdmin && !UserRole.Admin.equals(user.getRole())) {
            return ResultUtil.Fail("您非管理员~");
        }
        JSONObject userinfo = new JSONObject();
        userinfo.put("userId", user.getId());
        userinfo.put("account", user.getAccount());
        userinfo.put("username", user.getName());
        userinfo.put("role", user.getRole());
        userinfo.put("portrait", user.getPortrait());
        userinfo.put("phone", user.getPhone());
        userinfo.put("email", user.getEmail());
        //生成用户token
        userinfo.put("token", JwtUtil.createToken(userinfo));
        return ResultUtil.Succeed(userinfo);
    }

    @Override
    public List<UserDto> searchUser(SearchUserVo searchUserVo) {
        List<UserDto> users = userMapper.findUserByInfo(searchUserVo.getUserInfo());
        return users;
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

    @Override
    public UserDto info(String userId) {
        UserDto user = userMapper.info(userId);
        return user;
    }

    @Override
    public boolean updateUserInfo(String userId, UpdateVo updateVo) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getName, updateVo.getName())
                .set(User::getPortrait, updateVo.getPortrait())
                .set(User::getSex, updateVo.getSex())
                .set(User::getBirthday, updateVo.getBirthday())
                .set(User::getSignature, updateVo.getSignature())
                .eq(User::getId, userId);
        return update(updateWrapper);
    }

    @Override
    public boolean updateUserPortrait(String userId, String portrait) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPortrait, portrait)
                .eq(User::getId, userId);
        return update(updateWrapper);
    }


    @Override
    public Page<User> userList(UserListVo userListVo) {
        Page<User> page = new Page<>(userListVo.getCurrentPage(), userListVo.getPageSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userListVo.getKeyword())) {
            queryWrapper.and(query -> {
                query.like(User::getName, userListVo.getKeyword())
                        .or()
                        .like(User::getAccount, userListVo.getKeyword())
                        .or()
                        .like(User::getEmail, userListVo.getKeyword())
                        .or()
                        .like(User::getPhone, userListVo.getKeyword());
            });
        }
        if (StringUtils.isNotBlank(userListVo.getOnlineStatus())) {
            if (userListVo.getOnlineStatus().equals("online")) {
                queryWrapper.eq(User::getIsOnline, true);
            }
            if (userListVo.getOnlineStatus().equals("offline")) {
                queryWrapper.eq(User::getIsOnline, false);
            }
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        return this.page(page, queryWrapper);
    }


    @Override
    public boolean register(RegisterVo registerVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, registerVo.getAccount());
        if (count(queryWrapper) > 0) {
            throw new LinyuException("账号已存在~");
        }
        User user = new User();
        user.setId(IdUtil.randomUUID());
        user.setName(registerVo.getUsername());
        user.setAccount(registerVo.getAccount());
        String passwordHash = SecurityUtil.hashPassword(registerVo.getPassword());
        user.setStatus(UserStatus.Normal);
        user.setPassword(passwordHash);
        user.setBirthday(new Date());
        user.setSex("男");
        user.setPortrait(minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/default-portrait.jpg");
        return save(user);
    }

    @Override
    public void offline(String userId) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getIsOnline, false)
                .eq(User::getId, userId);
        update(updateWrapper);
    }

    @Override
    public void online(String userId) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getIsOnline, true)
                .eq(User::getId, userId);
        update(updateWrapper);
    }
}
