package com.cershy.linyuserver.dto;

import lombok.Data;

@Data
public class UserOperatedDto {
    private String id;
    private String userId;
    private String portrait;
    private String name;
    private String account;
    private String email;
    private String phone;
    private String type;
    private String content;
    private String createTime;
}
