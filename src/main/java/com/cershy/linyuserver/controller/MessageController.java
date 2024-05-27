package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.service.MessageService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.SendMsgToUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: dwh
 **/
@RestController
@RequestMapping("/v1/api/message")
@Slf4j
public class MessageController {

    @Resource
    MessageService messageService;

    /**
     * 发送消息给用户
     *
     * @return
     */
    @PostMapping("/send/to/user")
    public JSONObject sendMessageToUser(@Userid String userId, @RequestBody SendMsgToUserVo sendMsgToUserVo) {
        Message result = messageService.sendMessageToUser(userId, sendMsgToUserVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 聊天记录
     *
     * @return
     */
    @PostMapping("/record")
    public JSONObject messageRecord(@Userid String userId, @RequestBody MessageRecordVo messageRecordVo) {
        List<Message> result = messageService.messageRecord(userId, messageRecordVo);
        return ResultUtil.Succeed(result);
    }

}
