package com.cershy.linyuserver.entity.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgContent implements Serializable {
    private static final long serialVersionUID = 1L;
    //发送方用户id
    private String formUserId;
    //消息内容类型
    private String type;
    //消息内容
    private String content;
}
