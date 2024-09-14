package com.cershy.linyuserver.admin.vo.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserVo {
    @NotNull(message = "账号不能为空")
    private String account;
    private String username;
    @Email(message = "邮箱格式有误")
    private String email;
    private String phone;
}
