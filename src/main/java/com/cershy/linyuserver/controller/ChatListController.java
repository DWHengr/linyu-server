package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.ChatListDto;
import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.chatlist.CreateChatListVo;
import com.cershy.linyuserver.vo.chatlist.DeleteChatListVo;
import com.cershy.linyuserver.vo.chatlist.TopChatListVo;
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
     * @return
     */
    @PostMapping("/create")
    public JSONObject createChatList(@Userid String userId, @RequestBody CreateChatListVo createChatListVo) {
        ChatList result = chatListService.createChatList(userId, createChatListVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 删除会话
     *
     * @return
     */
    @PostMapping("/delete")
    public JSONObject deleteChatList(@Userid String userId, @RequestBody DeleteChatListVo deleteChatListVo) {
        boolean result = chatListService.deleteChatList(userId, deleteChatListVo);
        return ResultUtil.ResultByFlag(result);
    }


    /**
     * 设置置顶会话
     *
     * @return
     */
    @PostMapping("/top")
    public JSONObject topChatList(@Userid String userId, @RequestBody TopChatListVo topChatListVo) {
        boolean result = chatListService.topChatList(userId, topChatListVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 消息已读
     *
     * @return
     */
    @GetMapping("/read/{targetId}")
    public JSONObject messageRead(@Userid String userId, @PathVariable String targetId) {
        boolean result = chatListService.messageRead(userId, targetId);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 获取详细信息
     *
     * @return
     */
    @GetMapping("/detail/{targetId}")
    public JSONObject detailChartList(@Userid String userId, @PathVariable String targetId) {
        ChatList result = chatListService.detailChartList(userId, targetId);
        return ResultUtil.Succeed(result);
    }
}

