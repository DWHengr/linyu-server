package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.MessageMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.WebSocketService;
import com.cershy.linyuserver.vo.message.SendMsgToUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public Message sendMessageToUser(String userId, SendMsgToUserVo sendMsgToUserVo) {
        String toUserId = sendMsgToUserVo.getToUserId();
        //验证是否是好友
        boolean isFriend = friendService.isFriend(userId, toUserId);
        if (!isFriend) {
            throw new LinyuException("双方非好友");
        }
        //存入数据库
        Message message = new Message();
        message.setId(IdUtil.randomUUID());
        message.setFromId(userId);
        message.setToId(toUserId);
        message.setIsShowTime(sendMsgToUserVo.isShowTime());
        MsgContent msgContent = sendMsgToUserVo.getMsgContent();
        msgContent.setFormUserId(userId);
        message.setMsgContent(msgContent);
        boolean isSave = save(message);
        if (isSave) {
            //发送消息
            webSocketService.sendToUser(message, toUserId);
            //更新聊天列表
            chatListService.updateChatList(toUserId, userId, msgContent);
            return message;
        }
        return null;
    }
}
