package com.cershy.linyuserver.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailVerifyByAccountVo {
    @NotNull(message = "账号不能为空~")
    private String account;
}
