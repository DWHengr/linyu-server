package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.user.SearchUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
}
