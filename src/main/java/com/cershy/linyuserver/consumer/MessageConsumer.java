package com.cershy.linyuserver.consumer;

import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@RocketMQMessageListener(topic = "linyu", selectorExpression = "msg", consumerGroup = "linyu_group")
public class MessageConsumer implements RocketMQListener<Message> {

    @Resource
    WebSocketService webSocketService;

    @Override
    public void onMessage(Message msg) {
        //发送消息
        webSocketService.sendMsgToUser(msg, msg.getToId());
    }
}
