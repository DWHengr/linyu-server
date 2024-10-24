package com.cershy.linyuserver.admin.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.admin.vo.conversation.DeleteConversationVo;
import com.cershy.linyuserver.admin.vo.conversation.ResetSecretVo;
import com.cershy.linyuserver.annotation.UrlResource;
import com.cershy.linyuserver.dto.ConversationDto;
import com.cershy.linyuserver.entity.Conversation;
import com.cershy.linyuserver.service.ConversationService;
import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("AdminConversationController")
@RequestMapping("/admin/v1/api/conversation")
@Slf4j
public class ConversationController {

    @Resource
    ConversationService conversationService;

    /**
     * 创建会话
     *
     * @return
     */
    @PostMapping("/create")
    @UrlResource("admin")
    public JSONObject createConversation(@NotNull(message = "头像不能为空~") @RequestParam("portrait") MultipartFile portrait,
                                         @NotNull(message = "名称不能为空~") @RequestParam("name") String name) {
        Conversation result = conversationService.createConversation(portrait, name);
        return ResultUtil.Succeed(result);
    }

    /**
     * 修改会话
     *
     * @return
     */
    @PostMapping("/update")
    @UrlResource("admin")
    public JSONObject updateConversation(@NotNull(message = "头像不能为空~") @RequestParam("portrait") MultipartFile portrait,
                                         @NotNull(message = "名称不能为空~") @RequestParam("name") String name,
                                         @NotNull(message = "会话不能为空~") @RequestParam("id") String id) {
        boolean result = conversationService.updateConversation(portrait, name, id);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 会话列表
     *
     * @return
     */
    @GetMapping("/list")
    @UrlResource("admin")
    public JSONObject conversationList() {
        List<ConversationDto> result = conversationService.conversationList();
        return ResultUtil.Succeed(result);
    }

    /**
     * 删除会话
     *
     * @return
     */
    @PostMapping("/delete")
    @UrlResource("admin")
    public JSONObject deleteConversation(@RequestBody DeleteConversationVo deleteConversationVo) {
        boolean result = conversationService.deleteConversation(deleteConversationVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 重置会话秘钥
     *
     * @return
     */
    @PostMapping("/reset/secret")
    @UrlResource("admin")
    public JSONObject resetSecret(@RequestBody ResetSecretVo resetSecretVo) {
        boolean result = conversationService.resetSecret(resetSecretVo);
        return ResultUtil.ResultByFlag(result);
    }

}
