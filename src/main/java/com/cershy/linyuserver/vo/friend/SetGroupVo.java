package com.cershy.linyuserver.vo.friend;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SetGroupVo {
    @NotNull(message = "好友不能为空")
    private String friendId;
    private String groupId;
}
