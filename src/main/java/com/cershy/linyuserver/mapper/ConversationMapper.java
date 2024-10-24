package com.cershy.linyuserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cershy.linyuserver.dto.ConversationDto;
import com.cershy.linyuserver.entity.Conversation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ConversationMapper extends BaseMapper<Conversation> {

    @Select("SELECT c.*, u.`name`, u.`portrait`, u.`account` " +
            "FROM `conversation` AS c " +
            "JOIN `user` AS u ON u.`id` = c.`user_id` ")
    List<ConversationDto> conversationList();
}
