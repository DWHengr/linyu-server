package com.cershy.linyuserver.vo.message;

import com.cershy.linyuserver.entity.ext.MsgContent;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendMsgVo {
    //目标用户
    @NotNull(message = "目标用户不能为空")
    private String toUserId;
    //目标源
    private String source; //目标源:user,group
    //消息内容
    @NotNull(message = "消息内容不能为空")
    private MsgContent msgContent;
}
