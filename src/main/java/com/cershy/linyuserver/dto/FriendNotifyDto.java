package com.cershy.linyuserver.dto;

import com.cershy.linyuserver.entity.Notify;
import lombok.Data;

@Data
public class FriendNotifyDto extends Notify {
    private String fromName;
    private String fromPortrait;
    private String toName;
    private String toPortrait;
}
