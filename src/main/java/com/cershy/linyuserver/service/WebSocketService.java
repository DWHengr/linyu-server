package com.cershy.linyuserver.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketService {

    public static final ConcurrentHashMap<String, Channel> Online_User = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Channel, String> Online_Channel = new ConcurrentHashMap<>();

    public void online(Channel channel, String token) {
        try {
            Claims claims = JwtUtil.parseToken(token);
            String userId = (String) claims.get("userId");
            Online_User.put(userId, channel);
            Online_Channel.put(channel, userId);
        } catch (Exception e) {
            sendMsg(channel, "连接错误");
        }
    }

    public void offline(Channel channel) {
        String userId = Online_Channel.get(channel);
        if (StrUtil.isNotBlank(userId)) {
            Online_User.remove(userId);
            Online_Channel.remove(channel);
        }
    }

    private void sendMsg(Channel channel, Object msg) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(msg)));
    }

    public void sendToUser(Object msg, String userId) {
        Channel channel = Online_User.get(userId);
        if (channel != null) {
            sendMsg(channel, msg);
        }
    }

    public void sendAll(Object msg) {
        Online_Channel.forEach((channel, ext) -> {
            sendMsg(channel, msg);
        });
    }

}
