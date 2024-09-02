package com.cershy.linyuserver.vo.chatGroupNotice;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteNoticeVo {
    @NotNull(message = "群不能为空~")
    private String groupId;
    @NotNull(message = "公告不能为空~")
    private String noticeId;
}
