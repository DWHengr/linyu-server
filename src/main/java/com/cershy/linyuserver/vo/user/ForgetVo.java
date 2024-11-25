package com.cershy.linyuserver.vo.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author zhanglin
 * @date 2024/10/30$ 3:12$
 */
@Data
public class ForgetVo {
    @NotNull(message = "账号不能为空")
    private String account;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "验证码不能为空")
    private String code;
}
