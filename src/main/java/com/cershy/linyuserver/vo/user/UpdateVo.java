package com.cershy.linyuserver.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateVo {
    @NotNull(message = "用户名不能为空")
    private String name;
    private String sex;
    private Date birthday;
    private String signature;
    @NotNull(message = "头像不能为空")
    private String portrait;
}
