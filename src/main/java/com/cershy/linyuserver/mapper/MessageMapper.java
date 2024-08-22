package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT * " +
            "FROM (SELECT * " +
            "      FROM `message` " +
            "      WHERE (`from_id` = #{userId} AND `to_id` = #{targetId}) " +
            "         OR (`from_id` = #{targetId} AND `to_id` = #{userId}) " +
            "         OR (`source` = 'group' AND `to_id` = #{targetId}) " +
            "      ORDER BY `create_time` DESC LIMIT #{index}, #{num}) AS subquery " +
            "ORDER BY `create_time` ASC")
    @ResultMap("mybatis-plus_Message")
    List<Message> messageRecord(@Param("userId") String userId, @Param("targetId") String targetId, @Param("index") int index, @Param("num") int num);

    @Select("SELECT * " +
            "FROM (SELECT * " +
            "      FROM `message` " +
            "      WHERE (`from_id` = #{userId} AND `to_id` = #{targetId}) " +
            "         OR (`from_id` = #{targetId} AND `to_id` = #{userId}) " +
            "         OR (`source` = 'group' AND `to_id` = #{targetId}) " +
            "      ORDER BY `create_time` DESC LIMIT #{index}, #{num}) AS subquery ")
    @ResultMap("mybatis-plus_Message")
    List<Message> messageRecordDesc(@Param("userId") String userId, @Param("targetId") String targetId, @Param("index") int index, @Param("num") int num);


    @Select("SELECT * " +
            "FROM `message` " +
            "WHERE (`from_id` = #{userId} AND `to_id` = #{targetId}) " +
            "   OR (`from_id` = #{targetId} AND `to_id` = #{userId}) " +
            "ORDER BY `create_time` DESC LIMIT 1")
    @ResultMap("mybatis-plus_Message")
    Message getPreviousShowTimeMsg(@Param("userId") String userId, @Param("targetId") String toUserId);
}
