package com.cershy.linyuserver.dto;

import com.cershy.linyuserver.entity.ChatGroup;
import lombok.Data;

import java.util.List;

/**
 * @author colouredglaze
 * @date 2025/1/4$ 3:09$
 */
@Data
public class ChatDto {
    private List<FriendDetailsDto> friend;
    private List<ChatGroup> group;
}
