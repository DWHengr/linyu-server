package com.cershy.linyuserver.vo.chatGroup;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class CreateChatGroupVo {
    @NotNull(message = "群名称不能为空~")
    public String name;
    public String notice; //公告
    public ArrayList<User> users; // 成员ids

    @Data
    public static class User {
        private String userId;
        private String name;
    }
}
