package com.cershy.linyuserver.vo.group;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteGroupVo {
    @NotNull(message = "分组名称分组id")
    private String groupId;
}
