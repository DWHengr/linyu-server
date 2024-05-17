package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.vo.login.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: dwh
 **/
@RestController
@RequestMapping("/v1/api/login")
@Slf4j
public class LoginController {
    @Resource
    UserService userService;

    @UrlFree
    @PostMapping()
    public Object login(@Valid @RequestBody LoginVo loginVo) {
        JSONObject result = userService.validateLogin(loginVo);
        return result;
    }
}
