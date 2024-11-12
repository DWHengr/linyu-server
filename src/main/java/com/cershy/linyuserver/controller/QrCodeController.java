package com.cershy.linyuserver.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.annotation.UserIp;
import com.cershy.linyuserver.dto.QrCodeResult;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.utils.JwtUtil;
import com.cershy.linyuserver.utils.RedisUtils;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.qr.ResultVo;
import com.cershy.linyuserver.vo.qr.StatusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/qr")
@Slf4j
public class QrCodeController {

    @Resource
    RedisUtils redisUtils;

    @GetMapping("/code")
    @UrlFree
    public JSONObject code(@UserIp String userIp) {
        QrCodeResult qrCodeResult = new QrCodeResult();
        qrCodeResult.setAction("login");
        qrCodeResult.setIp(userIp);
        qrCodeResult.setStatus("wait");
        String key = IdUtil.objectId();
        redisUtils.set(key, JSONUtil.toJsonStr(qrCodeResult), 1);
        return ResultUtil.Succeed(key);
    }

    @PostMapping("/code/result")
    @UrlFree
    public JSONObject result(@UserIp String userIp, @RequestBody ResultVo resultVo) {
        String result = (String) redisUtils.get(resultVo.getKey());
        if (null == result) {
            throw new LinyuException("二维码失效~");
        }
        QrCodeResult qrCodeResult = JSONUtil.toBean(result, QrCodeResult.class);
        if (!userIp.equals(qrCodeResult.getIp())) {
            throw new LinyuException("登录地址不匹配~");
        }
        return ResultUtil.Succeed(qrCodeResult);
    }

    @GetMapping("/code/status")
    public JSONObject status(@RequestBody StatusVo statusVo) {
        String result = (String) redisUtils.get(statusVo.getKey());
        if (null == result) {
            throw new LinyuException("二维码失效~");
        }
        QrCodeResult qrCodeResult = JSONUtil.toBean(result, QrCodeResult.class);
        qrCodeResult.setStatus("scan");
        redisUtils.set(statusVo.getKey(), JSONUtil.toJsonStr(qrCodeResult), 1);
        return ResultUtil.Succeed(qrCodeResult);
    }
}
