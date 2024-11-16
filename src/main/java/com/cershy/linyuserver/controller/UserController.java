package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.annotation.UserRole;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.service.VerificationCodeService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.utils.RedisUtils;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.utils.SecurityUtil;
import com.cershy.linyuserver.vo.user.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author: dwh
 **/
@RestController
@RequestMapping("/v1/api/user")
@Slf4j
public class UserController {

    @Resource
    UserService userService;

    @Resource
    MinioUtil minioUtil;

    @Resource
    FriendService friendService;

    @Resource
    RedisUtils redisUtils;

    @Resource
    VerificationCodeService verificationCodeService;

    /**
     * 用户查询
     *
     * @return
     */
    @PostMapping("/search")
    public JSONObject searchUser(@RequestBody SearchUserVo searchUserVo) {
        List<UserDto> result = userService.searchUser(searchUserVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 获取用户每项未读数
     *
     * @return
     */
    @GetMapping("/unread")
    public JSONObject unreadInfo(@Userid String userId) {
        HashMap result = userService.unreadInfo(userId);
        return ResultUtil.Succeed(result);
    }

    /**
     * 邮箱验证码
     *
     * @return
     */
    @PostMapping("/email/verify")
    @UrlFree
    public JSONObject emailVerify(@RequestBody EmailVerifyVo emailVerifyVo) {
        verificationCodeService.emailVerificationCode(emailVerifyVo.getEmail());
        return ResultUtil.Succeed();
    }

    /**
     * 用户注册
     *
     * @return
     */
    @UrlFree
    @PostMapping("/register")
    public JSONObject register(@RequestBody RegisterVo registerVo) {
        String decryptedPassword = SecurityUtil.decryptPassword(registerVo.getPassword());
        registerVo.setPassword(decryptedPassword);
        boolean result = userService.register(registerVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 忘记密码
     *
     * @return
     */
    @UrlFree
    @PostMapping("/forget")
    public JSONObject forget(@RequestBody ForgetVo forgetVo) {
        String decryptedPassword = SecurityUtil.decryptPassword(forgetVo.getPassword());
        forgetVo.setPassword(decryptedPassword);
        boolean result = userService.forget(forgetVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public JSONObject info(@Userid String userId) {
        UserDto result = userService.info(userId);
        return ResultUtil.Succeed(result);
    }

    /**
     * 修改当前用户信息
     *
     * @return
     */
    @PostMapping("/update")
    public JSONObject update(@Userid String userId, @RequestBody UpdateVo updateVo) {
        boolean result = userService.updateUserInfo(userId, updateVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 修改密码
     *
     * @return
     */
    @PostMapping("/update/password")
    public JSONObject updateUserPassword(@Userid String userId, @RequestBody UpdatePasswordVo updateVo) {
        String decryptedPassword = SecurityUtil.decryptPassword(updateVo.getConfirmPassword());
        updateVo.setConfirmPassword(decryptedPassword);
        User user = userService.getById(userId);
        if (SecurityUtil.verifyPassword(updateVo.getOldPassword(), user.getPassword())) {
            boolean result = userService.updateUserInfo(userId, updateVo);
            return ResultUtil.ResultByFlag(result);
        } else return ResultUtil.ResultByFlag(false, "原密码错误~", 400);
    }

    @PostMapping(value = "/upload/portrait")
    public JSONObject upload(HttpServletRequest request,
                             @Userid String userId,
                             @RequestHeader("name") String name,
                             @RequestHeader("type") String type,
                             @RequestHeader("size") long size) throws IOException {
        String fileName = userId + "-portrait" + name.substring(name.lastIndexOf("."));
        String url = minioUtil.upload(request.getInputStream(), fileName, type, size);
        url += "?t=" + System.currentTimeMillis();
        userService.updateUserPortrait(userId, url);
        return ResultUtil.Succeed(url);
    }

    @PostMapping(value = "upload/portrait/form")
    public JSONObject uploadFrom(@Userid String userId,
                                 @RequestParam("name") String name,
                                 @RequestParam("type") String type,
                                 @RequestParam("size") long size,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = userId + "-portrait" + name.substring(name.lastIndexOf("."));
        String url = minioUtil.upload(file.getInputStream(), fileName, type, size);
        url += "?t=" + System.currentTimeMillis();
        userService.updateUserPortrait(userId, url);
        return ResultUtil.Succeed(url);
    }

    /**
     * 获取文件
     *
     * @return
     */
    @GetMapping("/get/file")
    public ResponseEntity<InputStreamResource> getFile(@Userid String userId,
                                                       @UserRole String role,
                                                       @RequestHeader("targetId") String targetId,
                                                       @RequestHeader("fileName") String fileName) {
        boolean isFriend = friendService.isFriend(userId, targetId);
        if (!isFriend && !userId.equals(targetId)
                && com.cershy.linyuserver.constant.UserRole.User.equals(role)) {
            throw new LinyuException("双方非好友");
        }
        InputStream inputStream = minioUtil.getObject(targetId + "/img/" + fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

    /**
     * 获取图片内容
     *
     * @return
     */
    @GetMapping("/get/img")
    public JSONObject getImg(@Userid String userId,
                             @UserRole String role,
                             @RequestParam("targetId") String targetId,
                             @RequestParam("fileName") String fileName) {
        boolean isFriend = friendService.isFriend(userId, targetId);
        if (!isFriend && !userId.equals(targetId) &&
                com.cershy.linyuserver.constant.UserRole.User.equals(role)) {
            throw new LinyuException("双方非好友");
        }
        String name = targetId + "/img/" + fileName;
        String url = (String) redisUtils.get(name);
        if (StringUtils.isBlank(url)) {
            url = minioUtil.previewFile(name);
            redisUtils.set(name, url, 7 * 24 * 60 * 60);
        }
        return ResultUtil.Succeed(url);
    }
}
