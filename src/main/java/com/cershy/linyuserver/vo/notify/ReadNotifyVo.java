package com.cershy.linyuserver.vo.notify;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReadNotifyVo {

    @NotNull(message = "通知类型")
    private String notifyType;
}
