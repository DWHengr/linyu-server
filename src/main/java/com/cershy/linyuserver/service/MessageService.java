package com.cershy.linyuserver.service;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.entity.MessageRetraction;
import com.cershy.linyuserver.entity.ext.MsgContent;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.ReeditMsgVo;
import com.cershy.linyuserver.vo.message.RetractionMsgVo;
import com.cershy.linyuserver.vo.message.SendMsgVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
public interface MessageService extends IService<Message> {

    Message sendMessage(String userId, SendMsgVo sendMsgVo);

    List<Message> messageRecord(String userId, MessageRecordVo messageRecordVo);

    List<Message> messageRecordDesc(String userId, MessageRecordVo messageRecordVo);

    Message sendFileMessageToUser(String userId, String toUserId, JSONObject fileInfo);

    MsgContent getFileMsgContent(String userId, String msgId);

    boolean updateMsgContent(String msgId, MsgContent msgContent);

    Message retractionMsg(String userId, RetractionMsgVo retractionMsgVo);

    MessageRetraction reeditMsg(String userId, ReeditMsgVo reeditMsgVo);

    String sendFileOrImg(String userId, String msgId, HttpServletRequest request) throws IOException;

    Message voiceToText(String userId, String msgId);
}
