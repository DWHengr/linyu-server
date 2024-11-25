package com.cershy.linyuserver.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.TalkListDto;
import com.cershy.linyuserver.entity.Talk;
import com.cershy.linyuserver.service.TalkService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.talk.CreateTalkVo;
import com.cershy.linyuserver.vo.talk.DeleteTalkVo;
import com.cershy.linyuserver.vo.talk.DetailsTalkVo;
import com.cershy.linyuserver.vo.talk.TalkListVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 说说 前端控制器
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/v1/api/talk")
public class TalkController {

    @Resource
    TalkService talkService;

    @Resource
    MinioUtil minioUtil;

    @PostMapping("/list")
    public JSONObject talkList(@Userid String userId, @RequestBody TalkListVo talkListVo) {
        List<TalkListDto> result = talkService.talkList(userId, talkListVo);
        return ResultUtil.Succeed(result);
    }

    @PostMapping("/details")
    public JSONObject detailsTalk(@Userid String userId, @RequestBody DetailsTalkVo detailsTalkVo) {
        TalkListDto result = talkService.detailsTalk(userId, detailsTalkVo);
        return ResultUtil.Succeed(result);
    }

    @PostMapping("/create")
    public JSONObject createTalk(@Userid String userId, @RequestBody CreateTalkVo createTalkVo) {
        Talk result = talkService.createTalk(userId, createTalkVo);
        return ResultUtil.Succeed(result);
    }


    @PostMapping("/upload/img")
    public JSONObject uploadImgTalk(HttpServletRequest request,
                                    @Userid String userId,
                                    @RequestHeader("talkId") String talkId,
                                    @RequestHeader("name") String name,
                                    @RequestHeader("type") String type,
                                    @RequestHeader("size") long size) throws IOException {
        String imgName = IdUtil.randomUUID() + name.substring(name.lastIndexOf("."));
        String imgPath = userId + "/img/" + imgName;
        minioUtil.uploadFile(request.getInputStream(), imgPath, size);
        Talk talk = talkService.updateTalkImg(userId, talkId, imgName);
        return ResultUtil.Succeed(talk);
    }

    @PostMapping("/upload/img/form")
    public JSONObject uploadImgTalk(MultipartFile file,
                                    @Userid String userId,
                                    @RequestParam("talkId") String talkId,
                                    @RequestParam("name") String name,
                                    @RequestParam("size") long size) throws IOException {
        String imgName = IdUtil.randomUUID() + name.substring(name.lastIndexOf("."));
        String imgPath = userId + "/img/" + imgName;
        minioUtil.uploadFile(file.getInputStream(), imgPath, size);
        Talk talk = talkService.updateTalkImg(userId, talkId, imgName);
        return ResultUtil.Succeed(talk);
    }

    @PostMapping("/delete")
    public JSONObject deleteTalk(@Userid String userId, @RequestBody DeleteTalkVo deleteTalkVo) {
        boolean result = talkService.deleteTalk(userId, deleteTalkVo);
        return ResultUtil.ResultByFlag(result);
    }
}

