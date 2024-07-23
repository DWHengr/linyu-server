package com.cershy.linyuserver.dto;

import lombok.Data;

@Data
public class SetsDto {
    private String sendMsgShortcut; // 发送消息
    private boolean bootstrap; //开机自启
    private String screenshot; //截图
    private String openUnreadMsg; //打开未读会话
    private String hideOrShowHome; //隐藏/显示主面板
    private String closeMsgWindow; //关闭会话窗口
    private boolean friendMsgNotify; //好有消息
    private boolean msgTone; //消息提示音
    private boolean audioVideoTone; //音视频提示音

    public static SetsDto defaultSets() {
        SetsDto defaultSets = new SetsDto();
        defaultSets.setSendMsgShortcut("enter");
        defaultSets.setBootstrap(false);
        defaultSets.setScreenshot("Alt + A");
        defaultSets.setOpenUnreadMsg("Alt + Z");
        defaultSets.setHideOrShowHome("Alt + Q");
        defaultSets.setCloseMsgWindow("Alt + W");
        defaultSets.setFriendMsgNotify(true);
        defaultSets.setMsgTone(true);
        defaultSets.setAudioVideoTone(true);
        return defaultSets;
    }
}
