package com.cershy.linyuserver.dto;

import com.cershy.linyuserver.entity.User;
import lombok.Data;

@Data
public class Top10MsgDto extends User {
    private int num;
}
