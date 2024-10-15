package com.cershy.linyuserver.admin.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.admin.vo.statistic.LoginDetailsVo;
import com.cershy.linyuserver.annotation.UrlResource;
import com.cershy.linyuserver.dto.UserOperatedDto;
import com.cershy.linyuserver.service.UserOperatedService;
import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("AdminStatisticController")
@RequestMapping("/admin/v1/api/stat")
@Slf4j
public class StatisticController {

    @Resource
    UserOperatedService userOperatedService;

    /**
     * 登录详情列表
     *
     * @return
     */
    @PostMapping("/login/details")
    @UrlResource("admin")
    public JSONObject loginDetails(@RequestBody LoginDetailsVo loginDetailsVo) {
        List<UserOperatedDto> result = userOperatedService.loginDetails(loginDetailsVo);
        return ResultUtil.Succeed(result);
    }


}
