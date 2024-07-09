package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.utils.RedisUtils;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.user.SearchUserVo;
import com.cershy.linyuserver.vo.user.UpdateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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

    /**
     * 获取文件
     *
     * @return
     */
    @GetMapping("/get/file")
    public ResponseEntity<InputStreamResource> getFile(@Userid String userId,
                                                       @RequestHeader("targetId") String targetId,
                                                       @RequestHeader("fileName") String fileName) {
        boolean isFriend = friendService.isFriend(userId, targetId);
        if (!isFriend && !userId.equals(targetId)) {
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
                             @RequestParam("targetId") String targetId,
                             @RequestParam("fileName") String fileName) {
        boolean isFriend = friendService.isFriend(userId, targetId);
        if (!isFriend && !userId.equals(targetId)) {
            throw new LinyuException("双方非好友");
        }
        String name = targetId + "/img/" + fileName;
        String url = (String) redisUtils.get(name);
        if (StringUtils.isBlank(url)) {
            url = minioUtil.previewFile(name);
            redisUtils.set(name, url, 60 * 60);
        }
        return ResultUtil.Succeed(url);
    }
}
