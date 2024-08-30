package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cershy.linyuserver.config.MinioConfig;
import com.cershy.linyuserver.dto.ChatGroupDetailsDto;
import com.cershy.linyuserver.entity.ChatGroup;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.ChatGroupMapper;
import com.cershy.linyuserver.service.ChatGroupMemberService;
import com.cershy.linyuserver.service.ChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.vo.chatGroup.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 聊天群表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-08-21
 */
@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements ChatGroupService {

    @Resource
    ChatGroupMemberService chatGroupMemberService;

    @Resource
    ChatGroupMapper chatGroupMapper;

    @Resource
    MinioConfig minioConfig;

    @Resource
    ChatListService chatListService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean createChatGroup(String userId, CreateChatGroupVo createChatGroupVo) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setId(IdUtil.randomUUID());
        chatGroup.setName(createChatGroupVo.getName());
        chatGroup.setNotice(createChatGroupVo.getNotice());
        chatGroup.setMemberNum(Optional.ofNullable(createChatGroupVo.getUsers()).map(v -> v.size()).orElse(0) + 1);
        chatGroup.setUserId(userId);
        chatGroup.setOwnerUserId(userId);
        chatGroup.setPortrait(minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/default-group-portrait.png");
        boolean isSava = save(chatGroup);
        //添加自己
        ChatGroupMember chatGroupMember = new ChatGroupMember();
        chatGroupMember.setId(IdUtil.randomUUID());
        chatGroupMember.setChatGroupId(chatGroup.getId());
        chatGroupMember.setUserId(userId);
        chatGroupMemberService.save(chatGroupMember);
        if (isSava && null != createChatGroupVo.getUsers()) {
            for (CreateChatGroupVo.User user : createChatGroupVo.getUsers()) {
                chatGroupMember = new ChatGroupMember();
                chatGroupMember.setId(IdUtil.randomUUID());
                chatGroupMember.setChatGroupId(chatGroup.getId());
                chatGroupMember.setUserId(user.getUserId());
                chatGroupMember.setGroupRemark(user.getName());
                chatGroupMemberService.save(chatGroupMember);
            }
        }
        return isSava;
    }

    @Override
    public List<ChatGroup> chatGroupList(String userId) {
        List<ChatGroup> result = chatGroupMapper.getList(userId);
        return result;
    }

    @Override
    public ChatGroupDetailsDto detailsChatGroup(String userId, DetailsChatGroupVo detailsChatGroupVo) {
        ChatGroupDetailsDto result = chatGroupMapper.detailsChatGroup(userId, detailsChatGroupVo.getChatGroupId());
        return result;
    }

    @Override
    public boolean isOwner(String groupId, String userId) {
        ChatGroup group = getById(groupId);
        if (group.getOwnerUserId().equals(userId))
            return true;
        return false;
    }

    @Override
    public boolean updateGroupPortrait(String groupId, String url) {
        LambdaUpdateWrapper<ChatGroup> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatGroup::getPortrait, url)
                .eq(ChatGroup::getId, groupId);
        return update(updateWrapper);
    }

    @Override
    public boolean updateChatGroupName(String userId, UpdateChatGroupNameVo updateChatGroupNameVo) {
        if (!isOwner(updateChatGroupNameVo.getGroupId(), userId))
            throw new LinyuException("您不是群主~");
        LambdaUpdateWrapper<ChatGroup> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatGroup::getName, updateChatGroupNameVo.getName())
                .eq(ChatGroup::getId, updateChatGroupNameVo.getGroupId());
        return update(updateWrapper);
    }

    @Override
    public boolean updateChatGroup(String userId, UpdateChatGroupVo updateChatGroupVo) {
        UpdateWrapper<ChatGroupMember> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(updateChatGroupVo.getUpdateKey(), updateChatGroupVo.getUpdateValue())
                .eq("chat_group_id", updateChatGroupVo.getGroupId())
                .eq("user_id", userId);
        return chatGroupMemberService.update(updateWrapper);
    }

    @Override
    public boolean inviteMember(String userId, InviteMemberVo inviteMemberVo) {
        List<ChatGroupMember> members = new ArrayList<>();
        for (String userid : inviteMemberVo.getUserIds()) {
            ChatGroupMember member = new ChatGroupMember();
            member.setId(IdUtil.randomUUID());
            member.setUserId(userid);
            member.setChatGroupId(inviteMemberVo.getGroupId());
            members.add(member);
        }
        if (members.size() > 0) {
            ChatGroup chatGroup = getById(inviteMemberVo.getGroupId());
            chatGroup.setMemberNum(chatGroup.getMemberNum() + members.size());
            updateById(chatGroup);
            return chatGroupMemberService.saveBatch(members);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean quitChatGroup(String userId, QuitChatGroupVo quitChatGroupVo) {
        LambdaQueryWrapper<ChatGroupMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatGroupMember::getUserId, userId)
                .eq(ChatGroupMember::getChatGroupId, quitChatGroupVo.getGroupId());
        chatGroupMemberService.remove(queryWrapper);

        LambdaQueryWrapper<ChatList> chatListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatListLambdaQueryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getFromId, quitChatGroupVo.getGroupId());
        chatListService.remove(chatListLambdaQueryWrapper);

        ChatGroup chatGroup = getById(quitChatGroupVo.getGroupId());
        chatGroup.setMemberNum(chatGroup.getMemberNum() - 1);
        return updateById(chatGroup);
    }
}
