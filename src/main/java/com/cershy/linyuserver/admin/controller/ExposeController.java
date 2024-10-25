package com.cershy.linyuserver.admin.controller;

import com.cershy.linyuserver.admin.vo.expose.ThirdSendMsgVo;
import com.cershy.linyuserver.annotation.UrlFree;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.service.MessageService;
import com.cershy.linyuserver.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/api/expose")
public class ExposeController {

    @Resource
    MessageService messengerService;

    @UrlFree
    @PostMapping("/send")
    public Object thirdPartySendMsg(@Userid String userid, @RequestBody ThirdSendMsgVo sendMsgVo) {
        boolean result = messengerService.thirdPartySendMsg(userid, sendMsgVo);
        return ResultUtil.ResultByFlag(result);
    }
}
