package com.cershy.linyuserver.admin.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cershy.linyuserver.admin.vo.user.CreateUserVo;
import com.cershy.linyuserver.admin.vo.user.UserListVo;
import com.cershy.linyuserver.annotation.UrlResource;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("AdminUserController")
@RequestMapping("/admin/v1/api/user")
@Slf4j
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/page")
    @UrlResource("admin")
    public JSONObject userList(@RequestBody UserListVo userListVo) {
        Page<User> result = userService.userList(userListVo);
        return ResultUtil.Succeed(result);
    }

    @PostMapping("/create")
    @UrlResource("admin")
    public JSONObject createUser(@RequestBody CreateUserVo createUserVo) {
        boolean result = userService.createUser(createUserVo);
        return ResultUtil.ResultByFlag(result);
    }
}
