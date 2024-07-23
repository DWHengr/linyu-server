package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.entity.UserSet;
import com.cershy.linyuserver.service.UserSetService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.userSet.UpdateUserSetVo;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update")
    public JSONObject updateUserSet(@Userid String userId, @RequestBody UpdateUserSetVo updateUserSetVo) {
        boolean result = userSetService.updateUserSet(userId, updateUserSetVo);
        return ResultUtil.ResultByFlag(result);
    }

}
