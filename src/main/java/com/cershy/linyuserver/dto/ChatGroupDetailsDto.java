package com.cershy.linyuserver.dto;

import lombok.Data;

@Data
public class ChatGroupDetailsDto {
    private String id;
    private String userId;
    private String ownerUserId;
    private String portrait;
    private String name;
    private String notice;
    private String memberNum;
    private String groupName;
    private String groupRemark;
    private String createTime;
    private String updateTime;
}
