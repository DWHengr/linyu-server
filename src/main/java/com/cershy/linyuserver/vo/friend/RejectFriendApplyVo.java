package com.cershy.linyuserver.vo.friend;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author colouredglaze
 * @date 2024/11/23$ 13:08$
 */
@Data
public class RejectFriendApplyVo {

    @NotNull(message = "fromId不能为空")
    private String fromId;

}
