package com.cershy.linyuserver.dto;

import com.cershy.linyuserver.entity.Conversation;
import lombok.Data;

@Data
public class ConversationDto extends Conversation {
    private String name;
    private String portrait;
    private String account;
}
