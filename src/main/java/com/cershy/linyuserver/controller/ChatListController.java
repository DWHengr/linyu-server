package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.ChatListDto;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.chatlist.CreateChatListVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author heath
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/v1/api/chat-list")
public class ChatListController {
    @Resource
    ChatListService chatListService;

    /**
     * 获取聊天列表
     *
     * @return
     */
    @GetMapping("/list")
    public JSONObject getChatList(@Userid String userId) {
        ChatListDto chatList = chatListService.getChatList(userId);
        return ResultUtil.Succeed(chatList);
    }

    /**
     * 创建聊天会话
     *
     * @param userId
     * @param createChatListVo
     * @return
     */
    @PostMapping("/create")
    public JSONObject createChatList(@Userid String userId, @RequestBody CreateChatListVo createChatListVo) {
        boolean result = chatListService.createChatList(userId, createChatListVo);
        return ResultUtil.ResultByFlag(result);
    }
}

