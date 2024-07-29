package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.dto.ChatListDto;
import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.ChatListMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.vo.chatlist.CreateChatListVo;
import com.cershy.linyuserver.vo.chatlist.DeleteChatListVo;
import com.cershy.linyuserver.vo.chatlist.TopChatListVo;
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

    @Resource
    FriendService friendService;

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
                .eq(ChatList::getFromId, fromUserId);
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
            //更新自己的聊天列表
            LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper();
            updateWrapper.set(ChatList::getLastMsgContent, JSONUtil.toJsonStr(msgContent))
                    .eq(ChatList::getUserId, fromUserId)
                    .eq(ChatList::getFromId, toUserId);
            update(new ChatList(), updateWrapper);
        }
    }

    @Override
    public ChatList createChatList(String userId, CreateChatListVo createChatListVo) {
        boolean isFriend = friendService.isFriend(userId, createChatListVo.getUserId());
        if (!isFriend) {
            throw new LinyuException("双方非好友");
        }
        //查询是否有会话,没有则新建
        ChatList chatList = chatListMapper.detailChartList(userId, createChatListVo.getUserId());
        if (null != chatList)
            return chatList;
        chatList = new ChatList();
        chatList.setId(IdUtil.randomUUID());
        chatList.setUserId(userId);
        chatList.setFromId(createChatListVo.getUserId());
        chatList.setUnreadNum(0);
        save(chatList);
        chatList = chatListMapper.detailChartList(userId, createChatListVo.getUserId());
        return chatList;
    }

    @Override
    public boolean messageRead(String userId, String targetId) {
        LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatList::getUnreadNum, 0).
                eq(ChatList::getUserId, userId).
                eq(ChatList::getFromId, targetId);
        return update(updateWrapper);
    }

    @Override
    public ChatList detailChartList(String userId, String targetId) {
        ChatList chatList = chatListMapper.detailChartList(userId, targetId);
        return chatList;
    }

    @Override
    public boolean deleteChatList(String userId, DeleteChatListVo deleteChatListVo) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getId, deleteChatListVo.getChatListId())
                .eq(ChatList::getUserId, userId);
        return remove(queryWrapper);
    }

    @Override
    public boolean topChatList(String userId, TopChatListVo topChatListVo) {
        LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatList::getIsTop, topChatListVo.isTop())
                .eq(ChatList::getId, topChatListVo.getChatListId())
                .eq(ChatList::getUserId, userId);
        return update(new ChatList(), updateWrapper);
    }

    @Override
    public int unread(String userId) {
        Integer num = chatListMapper.unreadByUserId(userId);
        return num == null ? 0 : num;
    }

    @Override
    public ChatList getChatListByUserIdAndFromId(String userId, String fromId) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getFromId, fromId);
        return getOne(queryWrapper);
    }

    @Override
    public boolean messageReadAll(String userId) {
        LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatList::getUnreadNum, 0).
                eq(ChatList::getUserId, userId);
        return update(updateWrapper);
    }
}
