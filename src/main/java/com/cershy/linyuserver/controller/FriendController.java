package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.FriendDetailsDto;
import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author heath
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/v1/api/friend")
public class FriendController {

    @Resource
    FriendService friendService;

    /**
     * 获取好友列表
     *
     * @return
     */
    @GetMapping("/list")
    public JSONObject getFriendList(@Userid String userId) {
        List<FriendListDto> friendListDto = friendService.getFriendList(userId);
        return ResultUtil.Succeed(friendListDto);
    }

    /**
     * 获取好友详情
     *
     * @return
     */
    @GetMapping("/details/{friendId}")
    public JSONObject getFriendDetails(@Userid String userId, @PathVariable String friendId) {
        FriendDetailsDto friendDetailsDto = friendService.getFriendDetails(userId, friendId);
        return ResultUtil.Succeed(friendDetailsDto);
    }
}

