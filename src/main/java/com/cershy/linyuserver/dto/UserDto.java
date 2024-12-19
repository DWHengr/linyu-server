package com.cershy.linyuserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String id;
    private String account;
    private String name;
    private String portrait;
    private String sex;
    private Date birthday;
    private String signature;
    private String phone;
    private String email;
}
