package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.TalkListDto;
import com.cershy.linyuserver.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.talk.CreateTalkVo;
import com.cershy.linyuserver.vo.talk.DeleteTalkVo;
import com.cershy.linyuserver.vo.talk.TalkListVo;

import java.util.List;

/**
 * <p>
 * 说说 服务类
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
public interface TalkService extends IService<Talk> {

    List<TalkListDto> talkList(String userId, TalkListVo talkListVo);

    Talk createTalk(String userId, CreateTalkVo createTalkVo);

    Talk updateTalkImg(String userId, String talkId, String imgName);

    boolean deleteTalk(String userId, DeleteTalkVo deleteTalkVo);
}
