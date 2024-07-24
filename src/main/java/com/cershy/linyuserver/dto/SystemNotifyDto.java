package com.cershy.linyuserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SystemNotifyDto {
    private String id;
    private String type;
    private String content;
    private Date createTime;
}
