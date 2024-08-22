package com.cershy.linyuserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.dto.MemberListDto;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.cershy.linyuserver.mapper.ChatGroupMemberMapper;
import com.cershy.linyuserver.service.ChatGroupMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.vo.ChatListMember.MemberListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 聊天群成员表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-08-21
 */
@Service
public class ChatGroupMemberServiceImpl extends ServiceImpl<ChatGroupMemberMapper, ChatGroupMember> implements ChatGroupMemberService {

    @Resource
    ChatGroupMemberMapper chatGroupMemberMapper;

    @Override
    public List<ChatGroupMember> getGroupMember(String groupId) {
        LambdaQueryWrapper<ChatGroupMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatGroupMember::getChatGroupId, groupId);
        return list(queryWrapper);
    }

    @Override
    public Map<String, MemberListDto> memberList(String userId, MemberListVo memberListVo) {
        List<MemberListDto> result = chatGroupMemberMapper.memberList(userId, memberListVo.getChatGroupId());
        return result.stream().collect(Collectors.toMap(MemberListDto::getUserId, user -> user));
    }
}
