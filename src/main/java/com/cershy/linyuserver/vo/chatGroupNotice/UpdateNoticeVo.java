package com.cershy.linyuserver.vo.chatGroupNotice;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateNoticeVo {
    @NotNull(message = "群不能为空~")
    private String groupId;
    @NotNull(message = "公告不能为空~")
    private String noticeId;
    @NotNull(message = "公告内容不能为空~")
    private String noticeContent;
}
