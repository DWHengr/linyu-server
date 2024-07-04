package com.cershy.linyuserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class TalkListDto {
    private String userId;
    private String name;
    private String portrait;
    private String remark;
    private String talkId;
    private TalkContentDto content;
    private List<LatestCommentDto> latestComment;
    private String time;
    private int likeNum;
    private int commentNum;
}
