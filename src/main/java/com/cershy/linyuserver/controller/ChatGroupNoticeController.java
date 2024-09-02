package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.entity.ChatGroupNotice;
import com.cershy.linyuserver.service.ChatGroupNoticeService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.chatGroupNotice.CreateNoticeVo;
import com.cershy.linyuserver.vo.chatGroupNotice.DeleteNoticeVo;
import com.cershy.linyuserver.vo.chatGroupNotice.NoticeListVo;
import com.cershy.linyuserver.vo.chatGroupNotice.UpdateNoticeVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/api/chat-group-notice")
public class ChatGroupNoticeController {

    @Resource
    ChatGroupNoticeService chatGroupNoticeService;


    /**
     * 创建群公告
     *
     * @return
     */
    @PostMapping("/create")
    public JSONObject createNotice(@Userid String userId, @RequestBody CreateNoticeVo createNoticeVo) {
        boolean result = chatGroupNoticeService.createNotice(userId, createNoticeVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 群公告列表
     *
     * @return
     */
    @PostMapping("/list")
    public JSONObject noticeList(@Userid String userId, @RequestBody NoticeListVo noticeListVo) {
        List<ChatGroupNotice> result = chatGroupNoticeService.noticeList(userId, noticeListVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 删除群公告
     *
     * @return
     */
    @PostMapping("/delete")
    public JSONObject deleteNotice(@Userid String userId, @RequestBody DeleteNoticeVo deleteNoticeVo) {
        boolean result = chatGroupNoticeService.deleteNotice(userId, deleteNoticeVo);
        return ResultUtil.ResultByFlag(result);
    }


    /**
     * 编辑群公告
     *
     * @return
     */
    @PostMapping("/update")
    public JSONObject updateNotice(@Userid String userId, @RequestBody UpdateNoticeVo updateNoticeVo) {
        boolean result = chatGroupNoticeService.updateNotice(userId, updateNoticeVo);
        return ResultUtil.ResultByFlag(result);
    }
}
