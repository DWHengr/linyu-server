package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.config.MinioConfig;
import com.cershy.linyuserver.dto.ChatGroupDetailsDto;
import com.cershy.linyuserver.entity.ChatGroup;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.cershy.linyuserver.mapper.ChatGroupMapper;
import com.cershy.linyuserver.service.ChatGroupMemberService;
import com.cershy.linyuserver.service.ChatGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.vo.chatGroup.CreateChatGroupVo;
import com.cershy.linyuserver.vo.chatGroup.DetailsChatGroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
}
