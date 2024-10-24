package com.cershy.linyuserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.admin.vo.conversation.DeleteConversationVo;
import com.cershy.linyuserver.admin.vo.conversation.ResetSecretVo;
import com.cershy.linyuserver.dto.ConversationDto;
import com.cershy.linyuserver.entity.Conversation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConversationService extends IService<Conversation> {
    String getSecretKey(String accessKey);

    Conversation createConversation(MultipartFile portrait, String name);

    List<ConversationDto> conversationList();

    boolean updateConversation(MultipartFile portrait, String name, String id);

    boolean deleteConversation(DeleteConversationVo deleteConversationVo);

    boolean resetSecret(ResetSecretVo resetSecretVo);
}
