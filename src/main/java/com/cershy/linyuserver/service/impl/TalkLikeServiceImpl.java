package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.dto.LikeListDto;
import com.cershy.linyuserver.entity.Talk;
import com.cershy.linyuserver.entity.TalkLike;
import com.cershy.linyuserver.mapper.TalkLikeMapper;
import com.cershy.linyuserver.service.TalkLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.TalkService;
import com.cershy.linyuserver.vo.talkLike.CreateTalkLikeVo;
import com.cershy.linyuserver.vo.talkLike.DeleteTalkLikeVo;
import com.cershy.linyuserver.vo.talkLike.TalkLikeListVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 说说点赞 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
@Service
public class TalkLikeServiceImpl extends ServiceImpl<TalkLikeMapper, TalkLike> implements TalkLikeService {

    @Resource
    TalkLikeMapper talkLikeList;

    @Resource
    TalkService talkService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean createTalkLike(String userId, CreateTalkLikeVo createTalkLikeVo) {
        LambdaQueryWrapper<TalkLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TalkLike::getTalkId, createTalkLikeVo.getTalkId())
                .eq(TalkLike::getUserId, userId);
        if (count(queryWrapper) <= 0) {

            Talk talk = talkService.getById(createTalkLikeVo.getTalkId());
            talk.setLikeNum(talk.getLikeNum() + 1);
            talkService.updateById(talk);

            TalkLike talkLike = new TalkLike();
            talkLike.setId(IdUtil.randomUUID());
            talkLike.setTalkId(createTalkLikeVo.getTalkId());
            talkLike.setUserId(userId);
            return save(talkLike);
        }
        return false;
    }

    @Override
    public List<LikeListDto> talkLikeList(String userId, TalkLikeListVo talkLikeListVo) {
        List<LikeListDto> list = talkLikeList.talkLikeList(userId, talkLikeListVo.getTalkId());
        return list;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteTalkLike(String userId, DeleteTalkLikeVo deleteTalkLikeVo) {
        Talk talk = talkService.getById(deleteTalkLikeVo.getTalkId());
        talk.setLikeNum(talk.getLikeNum() - 1);
        talkService.updateById(talk);
        LambdaQueryWrapper<TalkLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TalkLike::getTalkId, deleteTalkLikeVo.getTalkId())
                .eq(TalkLike::getUserId, userId);
        return remove(queryWrapper);
    }
}
