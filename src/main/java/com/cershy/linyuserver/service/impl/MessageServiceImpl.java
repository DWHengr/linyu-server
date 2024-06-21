package com.cershy.linyuserver.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.constant.MessageType;
import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.MessageMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.WebSocketService;
import com.cershy.linyuserver.utils.MinioUtil;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.SendMsgToUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public Message sendMessage(String userId, String toUserId, MsgContent msgContent) {
        //验证是否是好友
        boolean isFriend = friendService.isFriend(userId, toUserId);
        if (!isFriend) {
            throw new LinyuException("双方非好友");
        }
        //获取上一条显示时间的消息
        Message previousMessage = messageMapper.getPreviousShowTimeMsg(userId, toUserId);
        //存入数据库
        Message message = new Message();
        message.setId(IdUtil.randomUUID());
        message.setFromId(userId);
        message.setToId(toUserId);
        message.setIsShowTime(DateUtil.between(new Date(), previousMessage.getUpdateTime(), DateUnit.MINUTE) > 5);
        //设置内容
        msgContent.setFormUserId(userId);
        if (MessageType.File.equals(msgContent.getType())) {
            JSONObject content = JSONUtil.parseObj(msgContent.getContent());
            String fileName = userId + "/" + content.get("name");
            content.set("fileName", fileName);
            content.set("url", minioUtil.getUrl(fileName));
            msgContent.setContent(content.toJSONString(0));
        }
        message.setMsgContent(msgContent);
        boolean isSave = save(message);
        if (isSave) {
            //发送消息
            webSocketService.sendMsgToUser(message, toUserId);
            //更新聊天列表
            chatListService.updateChatList(toUserId, userId, msgContent);
            return message;
        }
        return null;
    }

    @Override
    public Message sendMessageToUser(String userId, SendMsgToUserVo sendMsgToUserVo) {
        return sendMessage(userId, sendMsgToUserVo.getToUserId(), sendMsgToUserVo.getMsgContent());
    }

    @Override
    public List<Message> messageRecord(String userId, MessageRecordVo messageRecordVo) {
        List<Message> messages = messageMapper.messageRecord(userId, messageRecordVo.getTargetId(),
                messageRecordVo.getIndex(), messageRecordVo.getNum());
        return messages;
    }

    @Override
    public Message sendFileMessageToUser(String userId, String toUserId, JSONObject fileInfo) {
        MsgContent msgContent = new MsgContent();
        msgContent.setContent(fileInfo.toJSONString(0));
        msgContent.setType(MessageType.File);
        return sendMessage(userId, toUserId, msgContent);
    }

    @Override
    public MsgContent getFileMsgContent(String userId, String msgId) {
        Message msg = getById(msgId);
        if (msg == null) {
            throw new LinyuException("消息为空");
        }
        if (msg.getFromId().equals(userId) || msg.getToId().equals(userId)) {
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
}
