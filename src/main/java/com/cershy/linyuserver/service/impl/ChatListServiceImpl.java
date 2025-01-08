package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.constant.MsgSource;
import com.cershy.linyuserver.constant.UserRole;
import com.cershy.linyuserver.dto.ChatListDto;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.ChatListMapper;
import com.cershy.linyuserver.service.ChatGroupMemberService;
import com.cershy.linyuserver.service.ChatListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.vo.chatlist.CreateChatListVo;
import com.cershy.linyuserver.vo.chatlist.DeleteChatListVo;
import com.cershy.linyuserver.vo.chatlist.DetailChatListVo;
import com.cershy.linyuserver.vo.chatlist.TopChatListVo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Lazy
    FriendService friendService;

    @Resource
    ChatGroupMemberService chatGroupMemberService;

    private List<ChatList> getChatListByUserIdAndIsTop(String userId, boolean isTop) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getIsTop, isTop)
                .orderByDesc(ChatList::getUpdateTime);
        return list(queryWrapper);
    }

    public List<ChatList> mergeSortedLists(List<ChatList> list1, List<ChatList> list2) {
        List<ChatList> mergedList = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            // 如果list1的当前元素的updateTime大于等于list2的当前元素，则将list1的当前元素加入mergedList
            if (list1.get(i).getUpdateTime().compareTo(list2.get(j).getUpdateTime()) >= 0) {
                mergedList.add(list1.get(i));
                i++;
            } else {
                // 否则将list2的当前元素加入mergedList
                mergedList.add(list2.get(j));
                j++;
            }
        }
        // 将list1中剩余的元素加入mergedList
        while (i < list1.size()) {
            mergedList.add(list1.get(i));
            i++;
        }
        // 将list2中剩余的元素加入mergedList
        while (j < list2.size()) {
            mergedList.add(list2.get(j));
            j++;
        }
        return mergedList;
    }

    @Override
    public ChatListDto getChatList(String userId) {
        ChatListDto chatListDto = new ChatListDto();
        //置顶
        List<ChatList> top = mergeSortedLists(chatListMapper.getChatListByUserIdAndIsTop(userId, true),
                chatListMapper.getChatListChatGroupByUserIdAndIsTop(userId, true));
        chatListDto.setTops(top);
        //其他
        List<ChatList> noTop = mergeSortedLists(chatListMapper.getChatListByUserIdAndIsTop(userId, false),
                chatListMapper.getChatListChatGroupByUserIdAndIsTop(userId, false));
        chatListDto.setOthers(noTop);
        return chatListDto;
    }

    @Override
    public void updateChatList(String toUserId, String fromUserId, MsgContent msgContent, String type) {
        //判断聊天列表是否存在
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
            chatList.setType(type);
            chatList.setFromId(fromUserId);
            if (toUserId.equals(msgContent.getFormUserId())) {
                chatList.setUnreadNum(0);
            } else {
                chatList.setUnreadNum(1);
            }
            chatList.setLastMsgContent(msgContent);
            save(chatList);
        } else {
            //更新
            if (toUserId.equals(msgContent.getFormUserId())) {
                chatList.setUnreadNum(0);
            } else {
                chatList.setUnreadNum(chatList.getUnreadNum() + 1);
            }
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
    public ChatList createChatList(String userId, String role, CreateChatListVo createChatListVo) {
        ChatList chatList;
        if (MsgSource.User.equals(createChatListVo.getType())) {
            boolean isFriend = friendService.isFriendIgnoreSpecial(userId, createChatListVo.getToId());
            if (!isFriend && UserRole.User.equals(role)) {
                throw new LinyuException("双方非好友");
            }
            chatList = chatListMapper.detailChatList(userId, createChatListVo.getToId());
        } else {
            chatList = chatListMapper.detailChatGroupList(userId, createChatListVo.getToId());
        }
        //查询是否有会话,没有则新建
        if (null != chatList)
            return chatList;
        chatList = new ChatList();
        chatList.setId(IdUtil.randomUUID());
        chatList.setUserId(userId);
        chatList.setType(createChatListVo.getType());
        chatList.setFromId(createChatListVo.getToId());
        chatList.setUnreadNum(0);
        save(chatList);
        if (MsgSource.User.equals(createChatListVo.getType())) {
            chatList = chatListMapper.detailChatList(userId, createChatListVo.getToId());
        } else {
            chatList = chatListMapper.detailChatGroupList(userId, createChatListVo.getToId());
        }
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
    public ChatList detailChartList(String userId, DetailChatListVo detailChatListVo) {
        if (MsgSource.Group.equals(detailChatListVo.getType())) {
            return chatListMapper.detailChatGroupList(userId, detailChatListVo.getTargetId());
        } else {
            return chatListMapper.detailChatList(userId, detailChatListVo.getTargetId());
        }
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

    @Override
    public void updateChatListGroup(String groupId, MsgContent msgContent) {
        List<ChatGroupMember> members = chatGroupMemberService.getGroupMember(groupId);
        for (ChatGroupMember member : members) {
            updateChatList(member.getUserId(), groupId, msgContent, MsgSource.Group);
        }
    }

    @Override
    public void removeByUserId(String userId, String friendId) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getFromId, friendId);
        remove(queryWrapper);
    }
}
