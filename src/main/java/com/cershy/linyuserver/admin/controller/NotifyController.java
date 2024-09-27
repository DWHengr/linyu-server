package com.cershy.linyuserver.admin.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.admin.vo.notify.DeleteNotifyVo;
import com.cershy.linyuserver.annotation.UrlResource;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.SystemNotifyDto;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 系统通知删除
     *
     * @return
     */
    @PostMapping("/system/delete")
    @UrlResource("admin")
    public JSONObject deleteNotify(@RequestBody DeleteNotifyVo deleteNotifyVo) {
        boolean result = notifyService.deleteNotify(deleteNotifyVo);
        return ResultUtil.ResultByFlag(result);
    }
}
