package com.cershy.linyuserver.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.constant.WsContentType;
import com.cershy.linyuserver.utils.JwtUtil;
import com.cershy.linyuserver.utils.ResultUtil;
import io.jsonwebtoken.Claims;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketService {

    @Data
    public static class WsContent {
        private String type;
        private Object content;
    }

    public static final ConcurrentHashMap<String, Channel> Online_User = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Channel, String> Online_Channel = new ConcurrentHashMap<>();

    public void online(Channel channel, String token) {
        try {
            Claims claims = JwtUtil.parseToken(token);
            String userId = (String) claims.get("userId");
            Online_User.put(userId, channel);
            Online_Channel.put(channel, userId);
        } catch (Exception e) {
            sendMsg(channel, ResultUtil.Fail("连接错误"), WsContentType.Msg);
            channel.close();
        }
    }

    public void offline(Channel channel) {
        String userId = Online_Channel.get(channel);
        if (StrUtil.isNotBlank(userId)) {
            Online_User.remove(userId);
            Online_Channel.remove(channel);
        }
    }

    private void sendMsg(Channel channel, Object msg, String type) {
        WsContent wsContent = new WsContent();
        wsContent.setType(type);
        wsContent.setContent(msg);
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsContent)));
    }

    public void sendMsgToUser(Object msg, String userId) {
        Channel channel = Online_User.get(userId);
        if (channel != null) {
            sendMsg(channel, msg, WsContentType.Msg);
        }
    }

    public void sendMsgAll(Object msg) {
        Online_Channel.forEach((channel, ext) -> {
            sendMsg(channel, msg, WsContentType.Msg);
        });
    }

    public void sendNotifyToUser(Object msg, String userId) {
        Channel channel = Online_User.get(userId);
        if (channel != null) {
            sendMsg(channel, msg, WsContentType.Notify);
        }
    }

    public void sendNotifyAll(Object msg) {
        Online_Channel.forEach((channel, ext) -> {
            sendMsg(channel, msg, WsContentType.Notify);
        });
    }

}
