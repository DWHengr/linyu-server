package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.VideoService;
import com.cershy.linyuserver.service.WebSocketService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.video.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/api/video")
@Slf4j
public class VideoController {

    @Resource
    VideoService videoService;

    /**
     * 发送offer
     */
    @PostMapping("/offer")
    public JSONObject offer(@Userid String userId, @RequestBody OfferVo offerVo) {
        boolean result = videoService.offer(userId, offerVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 发送answer
     */
    @PostMapping("/answer")
    public JSONObject answer(@Userid String userId, @RequestBody AnswerVo answerVo) {
        boolean result = videoService.answer(userId, answerVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 发送candidate
     */
    @PostMapping("/candidate")
    public JSONObject candidate(@Userid String userId, @RequestBody CandidateVo candidateVo) {
        boolean result = videoService.candidate(userId, candidateVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 挂断
     */
    @PostMapping("/hangup")
    public JSONObject hangup(@Userid String userId, @RequestBody HangupVo hangupVo) {
        boolean result = videoService.hangup(userId, hangupVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 邀请
     */
    @PostMapping("/invite")
    public JSONObject invite(@Userid String userId, @RequestBody InviteVo inviteVo) {
        boolean result = videoService.invite(userId, inviteVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 邀请
     */
    @PostMapping("/accept")
    public JSONObject accept(@Userid String userId, @RequestBody AcceptVo acceptVo) {
        boolean result = videoService.accept(userId, acceptVo);
        return ResultUtil.ResultByFlag(result);
    }
}
