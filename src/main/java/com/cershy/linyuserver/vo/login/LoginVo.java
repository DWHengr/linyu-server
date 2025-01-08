package com.cershy.linyuserver.vo.login;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: dwh
 **/
@Data
public class LoginVo {
    @NotNull(message = "账号不能为空")
    private String account;
    @NotNull(message = "密码不能为空")
    private String password;

    // 登录设备
    private String onlineEquipment;
}
