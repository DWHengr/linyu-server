package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.LikeListDto;
import com.cershy.linyuserver.entity.TalkLike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.talkLike.CreateTalkLikeVo;
import com.cershy.linyuserver.vo.talkLike.DeleteTalkLikeVo;
import com.cershy.linyuserver.vo.talkLike.TalkLikeListVo;

import java.util.List;

/**
 * <p>
 * 说说点赞 服务类
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
public interface TalkLikeService extends IService<TalkLike> {

    boolean createTalkLike(String userId, CreateTalkLikeVo createTalkLikeVo);

    List<LikeListDto> talkLikeList(String userId, TalkLikeListVo talkLikeListVo);

    boolean deleteTalkLike(String userId, DeleteTalkLikeVo deleteTalkLikeVo);
}
