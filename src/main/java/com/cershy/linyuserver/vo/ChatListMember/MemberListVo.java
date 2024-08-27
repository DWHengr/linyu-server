package com.cershy.linyuserver.vo.ChatListMember;


import lombok.Data;

@Data
public class MemberListVo {
    public String chatGroupId;
    //起始
    private int index = 0;
    //查询条数
    private int num = 10;
}
