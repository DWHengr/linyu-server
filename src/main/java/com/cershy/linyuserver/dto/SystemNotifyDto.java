package com.cershy.linyuserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SystemNotifyDto {
    private String id;
    private String type;
    private SystemNotifyContent content;
    private Date createTime;

    @Data
    public static class SystemNotifyContent {
        private String title;
        private String img;
        private String text;
    }
}
