package com.cershy.linyuserver.dto;

import com.cershy.linyuserver.entity.Friend;
import lombok.Data;

import java.util.List;

@Data
public class FriendList {
    //分组id
    private String groupId;
    //分组名称
    private String name;
    //分组对应好友
    private List<Friend> friends;
}
