package com.cershy.linyuserver.admin.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.UrlResource;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.SystemNotifyDto;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("AdminNotifyController")
@RequestMapping("/admin/v1/api/notify")
@Slf4j
public class NotifyController {

    @Resource
    NotifyService notifyService;

    /**
     * 系统通知列表
     *
     * @return
     */
    @GetMapping("/system/list")
    @UrlResource("admin")
    public JSONObject SystemListNotify(@Userid String userId) {
        List<SystemNotifyDto> result = notifyService.SystemListNotify(userId);
        return ResultUtil.Succeed(result);
    }
}
