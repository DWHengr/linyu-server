package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.ChatListDto;
import com.cershy.linyuserver.entity.ChatList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.entity.ext.MsgContent;

/**
 * <p>
 * 聊天列表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
public interface ChatListService extends IService<ChatList> {

    ChatListDto getChatList(String userId);

    void updateChatList(String toUserId, String fromUserId, MsgContent msgContent);
}
