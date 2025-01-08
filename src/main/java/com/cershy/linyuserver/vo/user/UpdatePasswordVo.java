package com.cershy.linyuserver.vo.user;

import lombok.Data;

/**
 * @author colouredglaze
 * @date 2024/10/29$ 22:22$
 */
@Data
public class UpdatePasswordVo {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
