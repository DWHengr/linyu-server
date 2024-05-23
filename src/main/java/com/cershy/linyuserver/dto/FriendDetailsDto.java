package com.cershy.linyuserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FriendDetailsDto {
    private String friendId;
    private String account;
    private String name;
    private String sex;
    private Date birthday;
    private String signature;
    private String remark;
    private String groupName;
}
