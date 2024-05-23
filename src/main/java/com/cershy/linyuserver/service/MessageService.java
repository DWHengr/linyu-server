package com.cershy.linyuserver.service;

import com.cershy.linyuserver.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.message.MessageRecordVo;
import com.cershy.linyuserver.vo.message.SendMsgToUserVo;

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

    Message sendMessageToUser(String userId, SendMsgToUserVo sendMsgToUserVo);

    List<Message> messageRecord(String userId, MessageRecordVo messageRecordVo);
}
