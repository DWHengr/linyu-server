package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.constant.FriendApplyStatus;
import com.cershy.linyuserver.constant.NotifyType;
import com.cershy.linyuserver.dto.FriendNotifyDto;
import com.cershy.linyuserver.entity.Notify;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.NotifyMapper;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.NotifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.WebSocketService;
import com.cershy.linyuserver.vo.notify.FriendApplyNotifyVo;
import com.cershy.linyuserver.vo.notify.ReadNotifyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 通知 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-28
 */
@Service
public class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify> implements NotifyService {

    @Resource
    FriendService friendService;

    @Resource
    NotifyMapper notifyMapper;

    @Resource
    WebSocketService webSocketService;

    @Override
    public boolean friendApplyNotify(String userId, FriendApplyNotifyVo friendApplyNotifyVo) {
//        boolean isFriend = friendService.isFriend(userId, friendApplyNotifyVo.getUserId());
//        if (isFriend) {
//            throw new LinyuException("ta已是您的好友");
//        }
//        LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Notify::getFromId, userId)
//                .eq(Notify::getToId, friendApplyNotifyVo.getUserId())
//                .eq(Notify::getType, NotifyType.Friend_Apply);
//        if (count(queryWrapper) > 0) {
//            throw new LinyuException("请勿重复申请");
//        }
        Notify notify = new Notify();
        notify.setId(IdUtil.randomUUID());
        notify.setFromId(userId);
        notify.setToId(friendApplyNotifyVo.getUserId());
        notify.setType(NotifyType.Friend_Apply);
        notify.setStatus(FriendApplyStatus.Wait);
        notify.setContent(friendApplyNotifyVo.getContent());
        notify.setUnreadId(friendApplyNotifyVo.getUserId());
        webSocketService.sendNotifyToUser(notify, friendApplyNotifyVo.getUserId());
        return save(notify);
    }

    @Override
    public List<FriendNotifyDto> friendListNotify(String userId) {
        List<FriendNotifyDto> notifyList = notifyMapper.friendListNotify(userId, NotifyType.Friend_Apply);
        return notifyList;
    }

    @Override
    public int unread(String userId) {
        int num = notifyMapper.unreadByUserId(userId);
        return num;
    }

    @Override
    public boolean readNotify(String userId, ReadNotifyVo readNotifyVo) {
        LambdaUpdateWrapper<Notify> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Notify::getUnreadId, "")
                .eq(Notify::getUnreadId, userId)
                .eq(Notify::getType, readNotifyVo.getNotifyType());
        return update(updateWrapper);
    }
}
