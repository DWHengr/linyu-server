package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.CommentListDto;
import com.cershy.linyuserver.service.TalkCommentService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.talkComment.CreateTalkCommentVo;
import com.cershy.linyuserver.vo.talkComment.DeleteTalkCommentVo;
import com.cershy.linyuserver.vo.talkComment.TalkCommentListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 说说评论 前端控制器
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/v1/api/talk-comment")
public class TalkCommentController {

    @Resource
    TalkCommentService talkCommentService;

    @PostMapping("/create")
    public JSONObject createTalkComment(@Userid String userId, @RequestBody CreateTalkCommentVo createTalkCommentVo) {
        boolean result = talkCommentService.createTalkComment(userId, createTalkCommentVo);
        return ResultUtil.ResultByFlag(result);
    }

    @PostMapping("/list")
    public JSONObject talkCommentList(@Userid String userId, @RequestBody TalkCommentListVo talkCommentListVo) {
        List<CommentListDto> result = talkCommentService.talkCommentList(userId, talkCommentListVo);
        return ResultUtil.Succeed(result);
    }

    @PostMapping("/delete")
    public JSONObject deleteTalkComment(@Userid String userId, @RequestBody DeleteTalkCommentVo deleteTalkLikeVo) {
        boolean result = talkCommentService.deleteTalkComment(userId, deleteTalkLikeVo);
        return ResultUtil.ResultByFlag(result);
    }
}

