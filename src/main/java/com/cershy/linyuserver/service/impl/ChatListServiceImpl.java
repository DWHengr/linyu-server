package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.dto.ChatListDto;
import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.mapper.ChatListMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 聊天列表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements ChatListService {

    @Resource
    ChatListMapper chatListMapper;

    private List<ChatList> getChatListByUserIdAndIsTop(String userId, boolean isTop) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getIsTop, isTop)
                .orderByDesc(ChatList::getUpdateTime);
        return list(queryWrapper);
    }

    @Override
    public ChatListDto getChatList(String userId) {
        ChatListDto chatListDto = new ChatListDto();
        List<ChatList> chatListByUserIdAndIsTop = getChatListByUserIdAndIsTop(userId, false);
        //置顶
        chatListDto.setTops(chatListMapper.getChatListByUserIdAndIsTop(userId, true));
        //其他
        chatListDto.setOthers(chatListMapper.getChatListByUserIdAndIsTop(userId, false));
        return chatListDto;
    }

    @Override
    public void updateChatList(String toUserId, String fromUserId, MsgContent msgContent) {
        //判断好友的聊天列表是否存在
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, toUserId)
                .eq(ChatList::getFromId, toUserId);
        ChatList chatList = getOne(queryWrapper);
        if (null == chatList) {
            //新建
            chatList = new ChatList();
            chatList.setId(IdUtil.randomUUID());
            chatList.setIsTop(false);
            chatList.setUserId(toUserId);
            chatList.setFromId(fromUserId);
            chatList.setUnreadNum(1);
            chatList.setLastMsgContent(msgContent);
            save(chatList);
        } else {
            //更新
            chatList.setUnreadNum(chatList.getUnreadNum() + 1);
            chatList.setLastMsgContent(msgContent);
            updateById(chatList);
        }
    }
}
