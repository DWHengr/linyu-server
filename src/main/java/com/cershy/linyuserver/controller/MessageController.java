package com.cershy.linyuserver.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.annotation.UserRole;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.constant.MsgType;
import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.entity.MessageRetraction;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.service.MessageService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.utils.RedisUtils;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.ReeditMsgVo;
import com.cershy.linyuserver.vo.message.RetractionMsgVo;
import com.cershy.linyuserver.vo.message.SendMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    @Resource
    RedisUtils redisUtils;

    /**
     * 发送消息给用户
     *
     * @return
     */
    @PostMapping("/send")
    public JSONObject sendMessage(@Userid String userId, @UserRole String role, @RequestBody SendMsgVo sendMsgVo) {
        Message result = messageService.sendMessage(userId, role, sendMsgVo, MsgType.User);
        return ResultUtil.Succeed(result);
    }

    /**
     * 撤回消息
     *
     * @return
     */
    @PostMapping("/retraction")
    public JSONObject retractionMsg(@Userid String userId, @RequestBody RetractionMsgVo retractionMsgVo) {
        Message result = messageService.retractionMsg(userId, retractionMsgVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 重新编辑
     *
     * @return
     */
    @PostMapping("/reedit")
    public JSONObject reeditMsg(@Userid String userId, @RequestBody ReeditMsgVo reeditMsgVo) {
        MessageRetraction result = messageService.reeditMsg(userId, reeditMsgVo);
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
     * 聊天记录（降序）
     *
     * @return
     */
    @PostMapping("/record/desc")
    public JSONObject messageRecordDesc(@Userid String userId, @RequestBody MessageRecordVo messageRecordVo) {
        List<Message> result = messageService.messageRecordDesc(userId, messageRecordVo);
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
        String url = messageService.sendFileOrImg(userId, msgId, request.getInputStream());
        return ResultUtil.Succeed(url);
    }


    /**
     * 发送文件（表单）
     *
     * @return
     */
    @PostMapping("/send/file/form")
    public JSONObject sendFile(@RequestParam("file") MultipartFile file,
                               @Userid String userId,
                               @RequestParam("msgId") String msgId) throws IOException {
        String url = messageService.sendFileOrImg(userId, msgId, file.getInputStream());
        return ResultUtil.Succeed(url);
    }

    /**
     * 发送图片
     *
     * @return
     */
    @PostMapping(value = "/send/Img")
    public JSONObject sendImg(HttpServletRequest request,
                              @Userid String userId,
                              @RequestHeader("msgId") String msgId) throws IOException {
        String url = messageService.sendFileOrImg(userId, msgId, request.getInputStream());
        return ResultUtil.Succeed(url);
    }


    /**
     * 获取文件
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

    /**
     * 获取媒体
     *
     * @return
     */
    @GetMapping("/get/media")
    public JSONObject getMedia(@Userid String userId, @RequestParam("msgId") String msgId) {
        MsgContent msgContent = messageService.getFileMsgContent(userId, msgId);
        JSONObject fileInfo = JSONUtil.parseObj(msgContent.getContent());
        String fileName = fileInfo.get("fileName").toString();
        String url = (String) redisUtils.get(fileName);
        if (StringUtils.isBlank(url)) {
            url = minioUtil.previewFile(fileName);
            redisUtils.set(fileName, url, 7 * 24 * 60 * 60);
        }
        return ResultUtil.Succeed(url);
    }

    /**
     * 语音消息转文字
     *
     * @return
     */
    @GetMapping("/voice/to/text")
    public JSONObject voiceToText(@Userid String userId, @RequestParam("msgId") String msgId) {
        Message result = messageService.voiceToText(userId, msgId);
        return ResultUtil.Succeed(result);
    }
}
