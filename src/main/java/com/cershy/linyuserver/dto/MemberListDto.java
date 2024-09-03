package com.cershy.linyuserver.dto;

import lombok.Data;

@Data
public class MemberListDto {
    private String chatGroupId;
    private String userId;
    private String name;
    private String remark;
    private String friendId;
    private String groupName;
    private String portrait;
    private String create_time;
    private String update_time;
}
