package com.cershy.linyuserver.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.cershy.linyuserver.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@Service
@Slf4j
public class VerificationCodeService {

    @Resource
    EmailService emailService;

    @Resource
    RedisUtils redisUtils;

    public void emailVerificationCode(String email) {
        String code = (String) redisUtils.get(email);
        if (code != null) {
            return;
        }
        Context context = new Context();
        context.setVariable("nowDate", DateUtil.now());
        code = RandomUtil.randomNumbers(6);
        redisUtils.set(email, code, 10 * 60);
        context.setVariable("code", code.toCharArray());
        emailService.sendHtmlMessage(email, "Linyu验证码", "email_template.html", context);
    }
}
