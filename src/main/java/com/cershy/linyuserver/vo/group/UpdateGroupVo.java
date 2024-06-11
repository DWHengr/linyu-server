package com.cershy.linyuserver.vo.group;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateGroupVo {

    @NotNull(message = "分组名称分组id")
    private String groupId;

    @NotNull(message = "分组名称不能为空")
    private String groupName;
}
