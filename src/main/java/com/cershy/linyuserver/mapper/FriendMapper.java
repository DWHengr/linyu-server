package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 好友表 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
public interface FriendMapper extends BaseMapper<Friend> {

    @Select("SELECT f.*, u.`name` AS `name` FROM `friend` AS f " +
            "JOIN `user` AS u ON f.`friend_id` = u.`id` " +
            "WHERE f.`user_id` = #{userId} AND f.`group_id`= #{groupId}")
    List<Friend> getFriendByUserIdAndGroupId(@Param("userId") String userId, @Param("groupId") String groupId);
}
