package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.service.MessageService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.SendMsgToUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
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

    @Resource
    MinioUtil minioUtil;

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

    /**
     * 发送文件
     *
     * @return
     */
    @PostMapping("/send/file")
    public JSONObject sendFile(HttpServletRequest request,
                               @Userid String userId,
                               @RequestHeader("msgId") String msgId) throws IOException {
        MsgContent msgContent = messageService.getFileMsgContent(userId, msgId);
        JSONObject fileInfo = JSONUtil.parseObj(msgContent.getContent());
        String url = minioUtil.uploadFile(request.getInputStream(), fileInfo.get("fileName").toString(), fileInfo.getLong("size"));
        return ResultUtil.Succeed(url);
    }

    /**
     * 发送文件
     *
     * @return
     */
    @GetMapping("/get/file")
    public ResponseEntity<InputStreamResource> getFile(HttpServletResponse response,
                                                       @Userid String userId,
                                                       @RequestHeader("msgId") String msgId) {
        MsgContent msgContent = messageService.getFileMsgContent(userId, msgId);
        JSONObject fileInfo = JSONUtil.parseObj(msgContent.getContent());
        String fileName = fileInfo.get("fileName").toString();
        InputStream inputStream = minioUtil.getObject(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.get("name").toString() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}
