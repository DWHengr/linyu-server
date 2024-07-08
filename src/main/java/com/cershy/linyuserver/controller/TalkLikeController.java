package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.LikeListDto;
import com.cershy.linyuserver.service.TalkLikeService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.talkLike.CreateTalkLikeVo;
import com.cershy.linyuserver.vo.talkLike.DeleteTalkLikeVo;
import com.cershy.linyuserver.vo.talkLike.TalkLikeListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 说说点赞 前端控制器
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/v1/api/talk-like")
public class TalkLikeController {

    @Resource
    TalkLikeService talkLikeService;

    @PostMapping("/create")
    public JSONObject createTalkLike(@Userid String userId, @RequestBody CreateTalkLikeVo createTalkLikeVo) {
        boolean result = talkLikeService.createTalkLike(userId, createTalkLikeVo);
        return ResultUtil.ResultByFlag(result);
    }

    @PostMapping("/list")
    public JSONObject talkLikeList(@Userid String userId, @RequestBody TalkLikeListVo talkLikeListVo) {
        List<LikeListDto> result = talkLikeService.talkLikeList(userId, talkLikeListVo);
        return ResultUtil.Succeed(result);
    }

    @PostMapping("/delete")
    public JSONObject deleteTalkLike(@Userid String userId, @RequestBody DeleteTalkLikeVo deleteTalkLikeVo) {
        boolean result = talkLikeService.deleteTalkLike(userId, deleteTalkLikeVo);
        return ResultUtil.ResultByFlag(result);
    }
}

