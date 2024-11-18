package com.cershy.linyuserver.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.admin.vo.expose.ThirdSendMsgVo;
import com.cershy.linyuserver.config.VoiceConfig;
import com.cershy.linyuserver.constant.MessageContentType;
import com.cershy.linyuserver.constant.MsgSource;
import com.cershy.linyuserver.constant.MsgType;
import com.cershy.linyuserver.dto.Top10MsgDto;
import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.entity.MessageRetraction;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.MessageMapper;
import com.cershy.linyuserver.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.utils.FileUtil;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.ReeditMsgVo;
import com.cershy.linyuserver.vo.message.RetractionMsgVo;
import com.cershy.linyuserver.vo.message.SendMsgVo;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
@Service
@Logger
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    FriendService friendService;

    @Resource
    WebSocketService webSocketService;

    @Resource
    ChatListService chatListService;

    @Resource
    MessageMapper messageMapper;

    @Resource
    MinioUtil minioUtil;

    @Resource
    RestTemplate restTemplate;

    @Resource
    VoiceConfig voiceConfig;

    @Resource
    MessageRetractionService messageRetractionService;

    @Resource
    MQProducerService mqProducerService;

    @Resource
    UserService userService;

    @Resource
    ChatGroupMemberService chatGroupMemberService;

    public Message sendMessage(String userId, String toUserId, MsgContent msgContent, String source, String type) {
        //获取上一条显示时间的消息
        Message previousMessage = messageMapper.getPreviousShowTimeMsg(userId, toUserId);
        //存入数据库
        Message message = new Message();
        message.setId(IdUtil.randomUUID());
        message.setFromId(userId);
        message.setSource(source);
        message.setToId(toUserId);
        message.setType(type);
        if (null == previousMessage) {
            message.setIsShowTime(true);
        } else {
            message.setIsShowTime(DateUtil.between(new Date(), previousMessage.getUpdateTime(), DateUnit.MINUTE) > 5);
        }
        //设置内容
        msgContent.setFormUserId(userId);
        if (MessageContentType.Img.equals(msgContent.getType()) ||
                MessageContentType.File.equals(msgContent.getType()) ||
                MessageContentType.Voice.equals(msgContent.getType())) {
            JSONObject content = JSONUtil.parseObj(msgContent.getContent());
            String name = (String) content.get("name");
            String fileType = name.substring(name.lastIndexOf(".") + 1);
            String fileName = userId + "/" + toUserId + "/" + IdUtil.randomUUID() + "." + fileType;
            content.set("fileName", fileName);
            content.set("url", minioUtil.getUrl(fileName));
            content.set("type", fileType);
            msgContent.setContent(content.toJSONString(0));
        }
        message.setMsgContent(msgContent);
        boolean isSave = save(message);
        if (isSave) {
            return message;
        }
        return null;
    }

    public Message sendMessageToUser(String userId, SendMsgVo sendMsgVo, String type) {
        //验证是否是好友
        boolean isFriend = friendService.isFriendIgnoreSpecial(userId, sendMsgVo.getToUserId());
        if (!isFriend) {
            throw new LinyuException("双方非好友");
        }
        Message message = sendMessage(userId, sendMsgVo.getToUserId(), sendMsgVo.getMsgContent(), MsgSource.User, type);
        //更新聊天列表
        chatListService.updateChatList(message.getToId(), userId, message.getMsgContent(), MsgSource.User);
        if (null != message) {
            try {
                mqProducerService.sendMsgToUser(message);
            } catch (Exception e) {
                //发送消息
                webSocketService.sendMsgToUser(message, message.getToId());
            }
        }
        return message;

    }

    public Message sendMessageToGroup(String userId, SendMsgVo sendMsgVo, String type) {
        //获取发送方用户信息
        User user = userService.getById(userId);
        MsgContent msgContent = sendMsgVo.getMsgContent();
        msgContent.setFormUserName(user.getName());
        msgContent.setFormUserPortrait(user.getPortrait());
        Message message = sendMessage(userId, sendMsgVo.getToUserId(), msgContent, MsgSource.Group, type);
        //更新聊天列表
        chatListService.updateChatListGroup(message.getToId(), message.getMsgContent());
        if (null != message) {
            try {
                mqProducerService.sendMsgToGroup(message);
            } catch (Exception e) {
                //发送消息
                webSocketService.sendMsgToGroup(message, message.getToId());
            }
        }
        return message;
    }

    @Override
    public Message sendMessage(String userId, String role, SendMsgVo sendMsgVo, String type) {
        if (MsgSource.Group.equals(sendMsgVo.getSource())) {
            return sendMessageToGroup(userId, sendMsgVo, type);
        } else {
            return sendMessageToUser(userId, sendMsgVo, type);
        }
    }

    @Override
    public List<Message> messageRecord(String userId, MessageRecordVo messageRecordVo) {
        List<Message> messages = messageMapper.messageRecord(userId, messageRecordVo.getTargetId(),
                messageRecordVo.getIndex(), messageRecordVo.getNum());
        return messages;
    }

    @Override
    public List<Message> messageRecordDesc(String userId, MessageRecordVo messageRecordVo) {
        List<Message> messages = messageMapper.messageRecordDesc(userId, messageRecordVo.getTargetId(),
                messageRecordVo.getIndex(), messageRecordVo.getNum());
        return messages;
    }

    @Override
    public Message sendFileMessageToUser(String userId, String toUserId, JSONObject fileInfo) {
        MsgContent msgContent = new MsgContent();
        msgContent.setContent(fileInfo.toJSONString(0));
        msgContent.setType(MessageContentType.File);
        return sendMessage(userId, toUserId, msgContent, MsgSource.User, MsgType.User);
    }

    @Override
    public MsgContent getFileMsgContent(String userId, String msgId) {
        Message msg = getById(msgId);
        if (msg == null) {
            throw new LinyuException("消息为空");
        }
        if (msg.getFromId().equals(userId) || msg.getToId().equals(userId)
                || chatGroupMemberService.isMemberExists(msg.getToId(), userId)) {
            return msg.getMsgContent();
        } else {
            throw new LinyuException("消息为空");
        }
    }

    @Override
    public boolean updateMsgContent(String msgId, MsgContent msgContent) {
        LambdaUpdateWrapper<Message> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Message::getMsgContent, msgContent)
                .eq(Message::getId, msgId);
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Message retractionMsg(String userId, RetractionMsgVo retractionMsgVo) {
        Message message = getById(retractionMsgVo.getMsgId());
        if (null == message)
            throw new LinyuException("消息不存在");
        MsgContent msgContent = message.getMsgContent();
        msgContent.setExt(msgContent.getType());
        //只有文本才保存，之前的消息内容
        if (MessageContentType.Text.equals(msgContent.getType())) {
            MessageRetraction messageRetraction = new MessageRetraction();
            messageRetraction.setMsgId(IdUtil.randomUUID());
            messageRetraction.setMsgId(message.getId());
            messageRetraction.setMsgContent(msgContent);
            messageRetractionService.save(messageRetraction);
        }
        msgContent.setType(MessageContentType.Retraction);
        msgContent.setContent("");
        updateById(message);

        ChatList userIdchatList = chatListService.getChatListByUserIdAndFromId(userId, message.getToId());
        userIdchatList.setLastMsgContent(msgContent);
        chatListService.updateById(userIdchatList);
        ChatList toIdchatList = null;
        if (MsgSource.User.equals(message.getSource())) {
            toIdchatList = chatListService.getChatListByUserIdAndFromId(message.getToId(), userId);
        } else {
            toIdchatList = chatListService.getChatListByUserIdAndFromId(message.getFromId(), message.getToId());
        }
        toIdchatList.setLastMsgContent(msgContent);
        chatListService.updateById(toIdchatList);

        webSocketService.sendMsgToUser(message, message.getToId());
        return message;
    }

    @Override
    public MessageRetraction reeditMsg(String userId, ReeditMsgVo reeditMsgVo) {
        LambdaQueryWrapper<MessageRetraction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MessageRetraction::getMsgId, reeditMsgVo.getMsgId());
        return messageRetractionService.getOne(queryWrapper);
    }

    @Override
    public String sendFileOrImg(String userId, String msgId, HttpServletRequest request) throws IOException {
        MsgContent msgContent = getFileMsgContent(userId, msgId);
        JSONObject fileInfo = JSONUtil.parseObj(msgContent.getContent());
        String url = minioUtil.uploadFile(request.getInputStream(), fileInfo.get("fileName").toString(), fileInfo.getLong("size"));
        return url;
    }

    @Override
    public Message voiceToText(String userId, String msgId) {
        Message message = getById(msgId);
        if (null == message || !MessageContentType.Voice.equals(message.getMsgContent().getType())) {
            throw new LinyuException("这不是一条语音~");
        }
        if (!message.getToId().equals(userId) && !message.getFromId().equals(userId)) {
            throw new LinyuException("不能查看其他~");
        }
        JSONObject voice = JSONUtil.parseObj(message.getMsgContent().getContent());
        if (voice.containsKey("text")) {
            return message;
        }
        //获取语音的路径
        String fileName = voice.get("fileName").toString();
        try {
            // 从 MinIO 获取文件
            InputStream inputStream = minioUtil.getObject(fileName);
            byte[] content = IOUtils.toByteArray(inputStream);
            ByteArrayResource fileResource = FileUtil.createByteArrayResource(content, fileName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);
            body.add("model", voiceConfig.getModel());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(voiceConfig.getTransitionApi(), requestEntity, String.class);

            JSONObject result = JSONUtil.parseObj(response.getBody());
            if (result.containsKey("text")) {
                String text = result.get("text").toString();
                voice.set("text", text);
                message.getMsgContent().setContent(voice.toJSONString(0));
                updateById(message);
                return message;
            } else {
                throw new LinyuException("语音转换错误~");
            }
        } catch (Exception e) {
            log.error("voiceToText:" + e.getMessage());
            throw new LinyuException("语音转换错误~");
        }
    }

    @Override
    public Integer messageNum(DateTime date) {
        Integer num = messageMapper.messageNum(date);
        return num;
    }

    @Override
    public List<Top10MsgDto> getTop10Msg(Date date) {
        List<Top10MsgDto> result = messageMapper.getTop10Msg(date);
        return result;
    }

    @Override
    public boolean thirdPartySendMsg(String userId, ThirdSendMsgVo thirdSendMsgVo) {
        if (userId == null)
            return false;
        List<User> users = userService.getUserByEmail(thirdSendMsgVo.getEmail());
        for (User user : users) {
            MsgContent msgContent = new MsgContent();
            msgContent.setType(MessageContentType.Text);
            msgContent.setContent(thirdSendMsgVo.getContent());

            SendMsgVo sendMsgVo = new SendMsgVo();
            sendMsgVo.setMsgContent(msgContent);
            sendMsgVo.setToUserId(user.getId());
            sendMsgVo.setSource(MsgSource.User);
            sendMessageToUser(userId, sendMsgVo, MsgType.User);
        }
        return true;
    }
}
