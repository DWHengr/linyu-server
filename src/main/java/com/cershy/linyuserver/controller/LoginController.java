package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.annotation.UserIp;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.service.UserService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.utils.SecurityUtil;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.login.QrCodeLoginVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: dwh
 **/
@RestController
@RequestMapping("/v1/api/login")
@Slf4j
@AllArgsConstructor
public class LoginController {
    @Resource
    UserService userService;

    @UrlFree
    @GetMapping("/public-key")
    public Object getPublicKey() {
        String result = SecurityUtil.getPublicKey();
        return ResultUtil.Succeed(result);
    }

    @UrlFree
    @PostMapping()
    public Object login(@Valid @RequestBody LoginVo loginVo, @UserIp String userIp) {
        String decryptedPassword = SecurityUtil.decryptPassword(loginVo.getPassword());
        loginVo.setPassword(decryptedPassword);
        JSONObject result = userService.validateLogin(loginVo, userIp, false);
        return result;
    }

    @PostMapping("/qr")
    public Object qrCodeLogin(@Valid @RequestBody QrCodeLoginVo qrCodeLoginVo, @Userid String userid) {
        JSONObject result = userService.validateQrCodeLogin(qrCodeLoginVo, userid);
        return result;
    }
}
