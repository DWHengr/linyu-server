package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.MemberListDto;
import com.cershy.linyuserver.service.ChatGroupMemberService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.ChatListMember.MemberListVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/chat-group-member")
public class ChatGroupMemberController {

    @Resource
    ChatGroupMemberService chatGroupMemberService;

    @PostMapping("/list")
    public JSONObject memberList(@Userid String userId, @RequestBody MemberListVo memberListVo) {
        Map<String, MemberListDto> result = chatGroupMemberService.memberList(userId, memberListVo);
        return ResultUtil.Succeed(result);
    }
}
