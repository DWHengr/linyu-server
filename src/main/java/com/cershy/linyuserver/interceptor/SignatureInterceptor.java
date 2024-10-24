package com.cershy.linyuserver.interceptor;

import com.cershy.linyuserver.service.ConversationService;
import com.cershy.linyuserver.utils.SignatureUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SignatureInterceptor implements HandlerInterceptor {

    @Resource
    ConversationService conversationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessKey = request.getHeader("X-Access-Key");
        String timestamp = request.getHeader("X-Timestamp");
        String signature = request.getHeader("X-Signature");
        // 验证必要参数
        if (accessKey == null || timestamp == null || signature == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // 验证时间戳，防止重放攻击
        long now = System.currentTimeMillis();
        long requestTime = Long.parseLong(timestamp);
        if (Math.abs(now - requestTime) > 300000) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // 根据accessKey获取secretKey
        String secretKey = conversationService.getSecretKey(accessKey);
        if (secretKey == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // 验证签名
        String method = request.getMethod();
        String path = request.getRequestURI();
        String calculatedSignature = SignatureUtils.calculateSignature(method, path, accessKey, timestamp, secretKey);
        if (!calculatedSignature.equals(signature)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
