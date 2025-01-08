package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.entity.ChatList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 聊天列表 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
public interface ChatListMapper extends BaseMapper<ChatList> {

    @Select("SELECT c.*, u.`name` AS `name`, f.`remark`,f.`chat_background` AS `chatBackground`, u.`portrait` AS portrait " +
            "FROM `chat_list` AS c " +
            "LEFT JOIN `user` AS u ON c.`from_id` = u.`id` " +
            "LEFT JOIN `friend` AS f ON c.`from_id` = f.`friend_id` AND c.`user_id` = f.`user_id` " +
            "WHERE c.`user_id` = #{userId} AND c.`is_top` = #{isTop} AND c.`type` = 'user' " +
            "ORDER BY c.`update_time` DESC")
    @ResultMap("mybatis-plus_ChatList")
    List<ChatList> getChatListByUserIdAndIsTop(@Param("userId") String userId, @Param("isTop") boolean isTop);

    @Select("SELECT c.*, cg.`name` AS `name`, cgm.`chat_background` AS `chatBackground`,cgm.`group_remark` AS `remark`, cg.`portrait` AS portrait  " +
            "FROM `chat_list` AS c " +
            "LEFT JOIN `chat_group` AS cg ON c.`from_id` = cg.`id` " +
            "LEFT JOIN `chat_group_member` AS cgm ON c.`from_id` = cgm.`chat_group_id` AND c.`user_id` = cgm.`user_id`  " +
            "WHERE c.`user_id` = #{userId} AND c.`is_top` = #{isTop} AND c.`type` = 'group' " +
            "ORDER BY c.`update_time` DESC")
    @ResultMap("mybatis-plus_ChatList")
    List<ChatList> getChatListChatGroupByUserIdAndIsTop(@Param("userId") String userId, @Param("isTop") boolean isTop);

    @Select("SELECT c.*, u.`name` AS `name`,f.`chat_background` AS `chatBackground`, u.`portrait` AS portrait, f.`remark` " +
            "FROM `chat_list` AS c " +
            "LEFT JOIN `user` AS u ON c.`from_id` = u.`id` " +
            "LEFT JOIN `friend` AS f ON c.`from_id` = f.`friend_id` AND c.`user_id` = f.`user_id` " +
            "WHERE c.`user_id` = #{userId} AND c.`from_id` = #{targetId} ")
    ChatList detailChatList(String userId, String targetId);


    @Select("SELECT c.*, cg.`name` AS `name`, cgm.`chat_background` AS `chatBackground`,cgm.`group_remark` AS `remark`, cg.`portrait` AS portrait  " +
            "FROM `chat_list` AS c " +
            "LEFT JOIN `chat_group` AS cg ON c.`from_id` = cg.`id` " +
            "LEFT JOIN `chat_group_member` AS cgm ON c.`from_id` = cgm.`chat_group_id` AND c.`user_id` = cgm.`user_id`  " +
            "WHERE c.`user_id` = #{userId} AND c.`from_id` = #{targetId} AND c.type='group'")
    ChatList detailChatGroupList(String userId, String targetId);


    @Select("SELECT SUM(`unread_num`) FROM `chat_list` " +
            "WHERE `user_id` = #{userId}")
    Integer unreadByUserId(String userId);
}
