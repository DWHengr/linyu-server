package com.cershy.linyuserver.vo.video;

import lombok.Data;

@Data
public class InviteVo {
    private String userId;
    private boolean isOnlyAudio;

    public void setIsOnlyAudio(boolean isOnlyAudio) {
        this.isOnlyAudio = isOnlyAudio;
    }
}
