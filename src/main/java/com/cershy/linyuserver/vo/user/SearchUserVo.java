package com.cershy.linyuserver.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchUserVo {
    @NotNull(message = "用户信息不能为空")
    private String userInfo;
}
