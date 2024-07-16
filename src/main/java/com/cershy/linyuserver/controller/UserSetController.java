package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.entity.UserSet;
import com.cershy.linyuserver.service.UserSetService;
import com.cershy.linyuserver.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/v1/api/user-set")
@RestController
public class UserSetController {

    @Resource
    UserSetService userSetService;

    @GetMapping("")
    public JSONObject getUserSet(@Userid String userId) {
        UserSet result = userSetService.getUserSet(userId);
        return ResultUtil.Succeed(result);
    }

}
