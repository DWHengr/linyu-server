package com.cershy.linyuserver.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.dto.QrCodeResult;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.utils.RedisUtils;
import com.cershy.linyuserver.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QrCodeService {

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserService userService;

    public String createQrCode(String action, String userIp, String userId) {
        switch (action) {
            case "login": {
                QrCodeResult qrCodeResult = new QrCodeResult();
                qrCodeResult.setAction("login");
                qrCodeResult.setIp(userIp);
                qrCodeResult.setStatus("wait");
                String key = IdUtil.objectId();
                redisUtils.set(key, JSONUtil.toJsonStr(qrCodeResult), 60);
                return key;
            }
            case "mine": {
                if (userId == null) {
                    throw new LinyuException("用户不能为空~");
                }
                QrCodeResult qrCodeResult = new QrCodeResult();
                qrCodeResult.setAction("mine");
                qrCodeResult.setIp(userIp);
                UserDto user = userService.info(userId);
                qrCodeResult.setExtend(JSONUtil.parseObj(user));
                String key = SecurityUtil.aesEncrypt(userId);
                redisUtils.set(key, JSONUtil.toJsonStr(qrCodeResult), 30 * 24 * 60 * 60);
                return key;
            }
        }
        throw new LinyuException("二维码生成失败~");
    }
}
