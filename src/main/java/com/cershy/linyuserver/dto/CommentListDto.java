package com.cershy.linyuserver.dto;

import lombok.Data;

@Data
public class CommentListDto {
    private String id;
    private String userId;
    private String name;
    private String portrait;
    private String remark;
    private String content;
    private String createTime;
}
