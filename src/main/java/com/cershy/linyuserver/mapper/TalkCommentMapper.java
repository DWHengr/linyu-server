package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.dto.CommentListDto;
import com.cershy.linyuserver.entity.TalkComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 说说评论 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
public interface TalkCommentMapper extends BaseMapper<TalkComment> {

    @Select("SELECT tc.id, tc.`content`, u.`name`, u.`portrait`, f.`remark`, u.`id` AS `user_id`, tc.create_time " +
            "FROM `talk_comment` AS tc " +
            "         LEFT JOIN `user` AS u ON u.`id` = tc.`user_id` " +
            "         LEFT JOIN `friend` AS f ON f.`user_id` = #{userId} AND f.`friend_id` = tc.`user_id` " +
            "WHERE tc.`talk_id` = #{talkId} " +
            "ORDER BY  " +
            "    tc.create_time ASC ")
    List<CommentListDto> talkCommentList(String userId, String talkId);
}
