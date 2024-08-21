package com.cershy.linyuserver.service;

import com.cershy.linyuserver.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MQProducerService {

    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    @Value("${rocketmq.enabled}")
    private boolean enabled;

    private static final String topic = "linyu";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通发送
     */
    public void send(Object obj) {
        rocketMQTemplate.send(topic, MessageBuilder.withPayload(obj).build());
    }

    /**
     * 发送同步消息
     */
    public SendResult sendMsg(Message msgBody) {
        if (!enabled)
            return null;
        SendResult sendResult = rocketMQTemplate.syncSend(topic + ":msg", MessageBuilder.withPayload(msgBody).build());
        return sendResult;
    }

    /**
     * 发送异步消息
     */
    public void sendAsyncData(String msgBody) {
        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
            }

            @Override
            public void onException(Throwable throwable) {
                // 处理消息发送异常逻辑
            }
        });
    }

    /**
     * 发送延时消息
     */
    public void sendDelayData(String msgBody, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
    }

    /**
     * 发送单向消息
     */
    public void sendOneWayData(String msgBody) {
        rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(msgBody).build());
    }

}
