package com.cershy.linyuserver.dto;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class QrCodeResult {
    private String action;
    private String ip;
    private String status;
    private JSONObject userInfo;
}
