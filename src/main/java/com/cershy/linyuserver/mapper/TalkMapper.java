package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.dto.TalkListDto;
import com.cershy.linyuserver.entity.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 说说 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
public interface TalkMapper extends BaseMapper<Talk> {

    @Select("SELECT  " +
            "    u.name, " +
            "    u.id, " +
            "    t.user_id, " +
            "    u.portrait, " +
            "    CASE  " +
            "        WHEN t.user_id =  #{userId} THEN NULL  " +
            "        ELSE f.remark  " +
            "    END AS remark, " +
            "    t.id AS talk_id, " +
            "    t.`latest_comment`, " +
            "    t.`create_time` AS `time`, " +
            "    t.content, " +
            "    t.comment_num " +
            "FROM  " +
            "    talk AS t " +
            "LEFT JOIN  " +
            "    friend AS f ON (t.user_id = f.friend_id AND f.user_id =  #{userId}) " +
            "LEFT JOIN  " +
            "    user AS u ON u.id = t.user_id " +
            "LEFT JOIN  " +
            "    talk_permission AS tp ON t.id = tp.talk_id " +
            "WHERE  " +
            "    t.user_id =  #{userId}  " +
            "    OR (f.user_id =  #{userId} AND (tp.permission =  #{userId} OR tp.permission = 'all')) " +
            "ORDER BY  " +
            "    t.create_time DESC " +
            "LIMIT #{index}, #{num} ")
    @ResultMap("TalkListDtoResultMap")
    List<TalkListDto> talkList(String userId, int index, int num);
}
