package com.cershy.linyuserver.admin.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.annotation.UserIp;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.utils.SecurityUtil;
import com.cershy.linyuserver.vo.login.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController("AdminLoginController")
@RequestMapping("/admin/v1/api/login")
@Slf4j
public class LoginController {

    @Resource
    UserService userService;

    @UrlFree
    @PostMapping()
    public Object login(@Valid @RequestBody LoginVo loginVo, @UserIp String userIp) {
        String decryptedPassword = SecurityUtil.decryptPassword(loginVo.getPassword());
        loginVo.setPassword(decryptedPassword);
        JSONObject result = userService.validateLogin(loginVo, userIp, true);
        return result;
    }
}
