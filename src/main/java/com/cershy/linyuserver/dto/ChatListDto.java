package com.cershy.linyuserver.dto;

import com.cershy.linyuserver.entity.ChatList;
import lombok.Data;

import java.util.List;

@Data
public class ChatListDto {
    private List<ChatList> tops;
    private List<ChatList> others;
}
