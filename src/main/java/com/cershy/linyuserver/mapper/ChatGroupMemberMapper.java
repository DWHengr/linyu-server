package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.dto.MemberListDto;
import com.cershy.linyuserver.entity.ChatGroupMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 聊天群成员表 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-08-21
 */
public interface ChatGroupMemberMapper extends BaseMapper<ChatGroupMember> {

    @Select("SELECT cgm.*,u.`name`,f.`remark`,u.`portrait` FROM `chat_group_member` AS cgm " +
            "LEFT JOIN `friend` as f on f.`friend_id` = cgm.`user_id` and f.`user_id` = #{userId} " +
            "LEFT JOIN `user` as u on u.`id` = cgm.`user_id`" +
            "WHERE cgm.`chat_group_id` = #{chatGroupId} " +
            "ORDER BY cgm.`create_time` ASC ")
    List<MemberListDto> memberList(String userId, String chatGroupId);
}
