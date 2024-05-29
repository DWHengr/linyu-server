package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.FriendNotifyDto;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.notify.FriendApplyNotifyVo;
import com.cershy.linyuserver.vo.notify.ReadNotifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: dwh
 **/
@RestController
@RequestMapping("/v1/api/notify")
@Slf4j
public class NotifyController {

    @Resource
    NotifyService notifyService;

    /**
     * 好有通知列表
     *
     * @return
     */
    @GetMapping("/friend/list")
    public JSONObject friendListNotify(@Userid String userId) {
        List<FriendNotifyDto> result = notifyService.friendListNotify(userId);
        return ResultUtil.Succeed(result);
    }


    /**
     * 好有申请通知
     *
     * @return
     */
    @PostMapping("/friend/apply")
    public JSONObject friendApplyNotify(@Userid String userId, @RequestBody FriendApplyNotifyVo friendApplyNotifyVo) {
        boolean result = notifyService.friendApplyNotify(userId, friendApplyNotifyVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 通知已读
     *
     * @return
     */
    @PostMapping("/read")
    public JSONObject readNotify(@Userid String userId, @RequestBody ReadNotifyVo readNotifyVo) {
        boolean result = notifyService.readNotify(userId, readNotifyVo);
        return ResultUtil.Succeed(result);
    }
}

