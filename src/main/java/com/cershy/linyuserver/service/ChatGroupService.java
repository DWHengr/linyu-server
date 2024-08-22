package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.ChatGroupDetailsDto;
import com.cershy.linyuserver.entity.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.cershy.linyuserver.vo.chatGroup.CreateChatGroupVo;
import com.cershy.linyuserver.vo.chatGroup.DetailsChatGroupVo;

import java.util.List;

/**
 * <p>
 * 聊天群表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-08-21
 */
public interface ChatGroupService extends IService<ChatGroup> {

    boolean createChatGroup(String userId, CreateChatGroupVo createChatGroupVo);

    List<ChatGroup> chatGroupList(String userId);

    ChatGroupDetailsDto detailsChatGroup(String userId, DetailsChatGroupVo detailsChatGroupVo);
}
