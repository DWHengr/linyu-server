package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.MemberListDto;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.ChatListMember.MemberListVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 聊天群成员表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-08-21
 */
public interface ChatGroupMemberService extends IService<ChatGroupMember> {

    List<ChatGroupMember> getGroupMember(String groupId);

    Map<String, MemberListDto> memberList(String userId, MemberListVo memberListVo);
}
