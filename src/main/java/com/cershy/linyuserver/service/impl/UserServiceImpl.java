package com.cershy.linyuserver.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.admin.vo.user.*;
import com.cershy.linyuserver.config.MinioConfig;
import com.cershy.linyuserver.constant.UserRole;
import com.cershy.linyuserver.constant.UserStatus;
import com.cershy.linyuserver.dto.QrCodeResult;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.UserMapper;
import com.cershy.linyuserver.service.*;
import com.cershy.linyuserver.utils.*;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.login.QrCodeLoginVo;
import com.cershy.linyuserver.vo.user.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    ChatListService chatListService;

    @Resource
    NotifyService notifyService;

    @Resource
    UserMapper userMapper;

    @Resource
    MinioConfig minioConfig;

    @Resource
    RedisUtils redisUtils;

    @Resource
    EmailService emailService;

    @Resource
    UserOperatedService userOperatedService;

    @Resource
    WebSocketService webSocketService;

    @Resource
    MinioUtil minioUtil;

    public void updateRedisOnlineNum() {
        Integer onlineNum = webSocketService.getOnlineNum();
        String key = "onlineNum#" + DateUtil.today();
        Integer redisOnlineNum = (Integer) redisUtils.get(key);
        if (null == redisOnlineNum) {
            redisUtils.set(key, onlineNum, 25 * 60 * 60);
        }
        if (onlineNum > redisOnlineNum) {
            redisUtils.set(key, onlineNum, 25 * 60 * 60);
        }
    }


    @Override
    public JSONObject validateLogin(LoginVo loginVo, String userIp, boolean isAdmin) {
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
        JSONObject userinfo = createUserToken(user, userIp);
        return ResultUtil.Succeed(userinfo);
    }

    public JSONObject createUserToken(User user, String userIp) {
        JSONObject userinfo = new JSONObject();
        userinfo.put("userId", user.getId());
        userinfo.put("account", user.getAccount());
        userinfo.put("username", user.getName());
        userinfo.put("role", user.getRole());
        userinfo.put("sex", user.getSex());
        userinfo.put("portrait", user.getPortrait());
        userinfo.put("phone", user.getPhone());
        userinfo.put("email", user.getEmail());
        //生成用户token
        userinfo.put("token", JwtUtil.createToken(userinfo));
        ThreadUtil.execAsync(() -> {
            //记录登录操作
            userOperatedService.recordLogin(user.getId(), userIp);
            //更新同时在线人数
            updateRedisOnlineNum();
        });
        return userinfo;
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
    public boolean updateUserInfo(String userId, UpdatePasswordVo updateVo) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        String passwordHash = SecurityUtil.hashPassword(updateVo.getConfirmPassword());
        updateWrapper.set(User::getPassword, passwordHash)
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
        queryWrapper.select(User::getId, User::getAccount, User::getName, User::getPortrait,
                User::getSex, User::getBirthday, User::getSignature, User::getPhone,
                User::getEmail, User::getLastOptTime, User::getStatus, User::getIsOnline,
                User::getRole, User::getCreateTime, User::getUpdateTime);
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
        //验证码校验
        String code = (String) redisUtils.get(registerVo.getEmail());
        if (code == null || !code.equals(registerVo.getCode())) {
            throw new LinyuException("验证码错误或者已失效~");
        }
        redisUtils.del(registerVo.getEmail());
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
        user.setEmail(registerVo.getEmail());
        user.setPortrait(minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/default-portrait.jpg");
        return save(user);
    }

    @Override
    public boolean forget(ForgetVo forgetVo) {
        //验证码校验
        String code = (String) redisUtils.get(forgetVo.getEmail());
        if (code == null || !code.equals(forgetVo.getCode())) {
            throw new LinyuException("验证码错误或者已失效~");
        }
        redisUtils.del(forgetVo.getEmail());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, forgetVo.getAccount())
                .eq(User::getEmail, forgetVo.getEmail());
        User user = getOne(queryWrapper);
        if (null == user) throw new LinyuException("用户不存在~");
//        User user = new User();
//        user.setId(IdUtil.randomUUID());
//        user.setAccount(forgetVo.getAccount());
        String passwordHash = SecurityUtil.hashPassword(forgetVo.getPassword());
//        user.setStatus(UserStatus.Normal);
        user.setPassword(passwordHash);
//        user.setBirthday(new Date());
//        user.setSex("男");
//        user.setEmail(forgetVo.getEmail());
//        user.setPortrait(minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/default-portrait.jpg");
        return updateById(user);
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

    @Override
    public boolean createUser(CreateUserVo createUserVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, createUserVo.getAccount());
        if (count(queryWrapper) > 0) {
            throw new LinyuException("账号已存在~");
        }
        User user = new User();
        user.setId(IdUtil.randomUUID());
        user.setName(createUserVo.getName());
        user.setAccount(createUserVo.getAccount());
        String password = RandomUtil.randomString(8);
        String passwordHash = SecurityUtil.hashPassword(password);
        user.setStatus(UserStatus.Normal);
        user.setPassword(passwordHash);
        user.setBirthday(new Date());
        user.setSex("男");
        user.setEmail(createUserVo.getEmail());
        user.setPortrait(minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/default-portrait.jpg");

        //密码发送邮件
        if (save(user)) {
            Context context = new Context();
            context.setVariable("username", createUserVo.getName());
            context.setVariable("account", createUserVo.getAccount());
            context.setVariable("password", password);
            emailService.sendHtmlMessage(createUserVo.getEmail(), "Linyu用户密码", "email_password_template.html", context);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String createThirdPartyUser(MultipartFile portrait, String name) {
        String userId = IdUtil.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setAccount(IdUtil.objectId());
        String password = RandomUtil.randomString(8);
        String passwordHash = SecurityUtil.hashPassword(password);
        user.setStatus(UserStatus.Normal);
        user.setPassword(passwordHash);
        user.setBirthday(new Date());
        user.setRole(UserRole.Third);
        user.setSex("男");
        String url;
        try {
            url = minioUtil.upload(portrait.getInputStream(), userId + "-portrait"
                    , portrait.getContentType(), portrait.getSize());
        } catch (Exception e) {
            throw new LinyuException("头像上传失败~");
        }
        user.setPortrait(url);
        save(user);
        return userId;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateThirdPartyUser(MultipartFile portrait, String name, String userId) {
        String url;
        try {
            url = minioUtil.upload(portrait.getInputStream(), userId + "-portrait"
                    , portrait.getContentType(), portrait.getSize());
        } catch (Exception e) {
            throw new LinyuException("头像上传失败~");
        }
        url += "?t=" + System.currentTimeMillis();
        User user = getById(userId);
        user.setPortrait(url);
        user.setName(name);
        return updateById(user);
    }

    @Override
    public boolean allUserOffline() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getIsOnline, false);
        return update(updateWrapper);
    }

    @Override
    public boolean disableUser(String userId, DisableUserVo disableUserVo) {
        if (userId.equals(disableUserVo.getUserId())) {
            throw new LinyuException("不能禁用自己~");
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getStatus, UserStatus.Disable)
                .eq(User::getId, disableUserVo.getUserId());
        return update(updateWrapper);
    }

    @Override
    public boolean deleteUser(String userId, DeleteUserVo deleteUserVo) {
        if (userId.equals(deleteUserVo.getUserId())) {
            throw new LinyuException("不能删除自己~");
        }
        User user = getById(deleteUserVo.getUserId());
        if (UserRole.Third.equals(user.getRole())) {
            throw new LinyuException("第三方用户不能删除，请到会话中删除~");
        }
        return removeById(deleteUserVo.getUserId());
    }

    @Override
    public boolean undisableUser(UndisableUserVo undisableUserVo) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getStatus, UserStatus.Normal)
                .eq(User::getId, undisableUserVo.getUserId());
        return update(updateWrapper);
    }

    @Override
    public boolean updateUser(UpdateUserVo updateUserVo) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getName, updateUserVo.getName())
                .set(User::getEmail, updateUserVo.getEmail())
                .set(User::getPhone, updateUserVo.getPhone())
                .eq(User::getId, updateUserVo.getId());
        return update(updateWrapper);
    }

    @Override
    public boolean restPassword(ResetPasswordVo resetPasswordVo) {
        User user = getById(resetPasswordVo.getUserId());
        if (null == user) {
            throw new LinyuException("用户不存在~");
        }
        String password = RandomUtil.randomString(8);
        String passwordHash = SecurityUtil.hashPassword(password);
        user.setPassword(passwordHash);
        //密码发送邮件
        if (updateById(user)) {
            Context context = new Context();
            context.setVariable("username", user.getName());
            context.setVariable("account", user.getAccount());
            context.setVariable("password", password);
            emailService.sendHtmlMessage(user.getEmail(), "Linyu用户密码", "email_password_template.html", context);
        }
        return true;
    }

    @Override
    public boolean setAdmin(String userId, SetAdminVo setAdminVo) {
        if (userId.equals(setAdminVo.getUserId())) {
            throw new LinyuException("不能操作自己~");
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getRole, UserRole.Admin)
                .eq(User::getId, setAdminVo.getUserId());
        return update(updateWrapper);
    }

    @Override
    public boolean cancelAdmin(String userId, CancelAdminVo cancelAdminVo) {
        if (userId.equals(cancelAdminVo.getUserId())) {
            throw new LinyuException("不能操作自己~");
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getRole, UserRole.User)
                .eq(User::getId, cancelAdminVo.getUserId());
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteThirdPartyUser(String userId) {
        return removeById(userId);
    }

    @Override
    public List<User> getUserByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return list(queryWrapper);
    }

    @Override
    public JSONObject validateQrCodeLogin(QrCodeLoginVo qrCodeLoginVo, String userid) {
        // 获取用户
        User user = getById(userid);
        if (null == user) {
            return ResultUtil.Fail("用户不存在~");
        }
        String result = (String) redisUtils.get(qrCodeLoginVo.getKey());
        if (null == result) {
            return ResultUtil.Fail("二维码已失效~");
        }
        QrCodeResult qrCodeResult = JSONUtil.toBean(result, QrCodeResult.class);
        JSONObject userinfo = createUserToken(user, qrCodeResult.getIp());
        qrCodeResult.setStatus("success");
        qrCodeResult.setExtend(userinfo);
        redisUtils.set(qrCodeLoginVo.getKey(), JSONUtil.toJsonStr(qrCodeResult), 60);
        return ResultUtil.Succeed();
    }
}
