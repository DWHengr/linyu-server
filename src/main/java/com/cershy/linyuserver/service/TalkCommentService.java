package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.CommentListDto;
import com.cershy.linyuserver.entity.TalkComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.talkComment.CreateTalkCommentVo;
import com.cershy.linyuserver.vo.talkComment.DeleteTalkCommentVo;
import com.cershy.linyuserver.vo.talkComment.TalkCommentListVo;

import java.util.List;

/**
 * <p>
 * 说说评论 服务类
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
public interface TalkCommentService extends IService<TalkComment> {
    boolean createTalkComment(String userId, CreateTalkCommentVo createTalkCommentVo);

    List<CommentListDto> talkCommentList(String userId, TalkCommentListVo talkCommentListVo);

    boolean deleteTalkComment(String userId, DeleteTalkCommentVo deleteTalkLikeVo);
}
