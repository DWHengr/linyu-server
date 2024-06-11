package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.constant.FriendApplyStatus;
import com.cershy.linyuserver.constant.NotifyType;
import com.cershy.linyuserver.dto.FriendDetailsDto;
import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.entity.Friend;
import com.cershy.linyuserver.entity.Group;
import com.cershy.linyuserver.entity.Notify;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.FriendMapper;
import com.cershy.linyuserver.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.GroupService;
import com.cershy.linyuserver.service.NotifyService;
import com.cershy.linyuserver.service.WebSocketService;
import com.cershy.linyuserver.vo.friend.AgreeFriendApplyVo;
import com.cershy.linyuserver.vo.friend.SearchFriendsVo;
import com.cershy.linyuserver.vo.friend.SetRemarkVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 好友表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Resource
    GroupService groupService;

    @Resource
    FriendMapper friendMapper;

    @Resource
    NotifyService notifyService;

    @Resource
    WebSocketService webSocketService;


    @Override
    public List<FriendListDto> getFriendList(String userId) {
        List<FriendListDto> friendListDtos = new ArrayList<>();
        //将没有分组的好像添加到未分组中
        List<Friend> ungroupFriends = friendMapper.getFriendByUserIdAndGroupId(userId, "0");
        if (null != ungroupFriends && ungroupFriends.size() > 0) {
            FriendListDto ungroupFriendListDto = new FriendListDto();
            ungroupFriendListDto.setName("未分组");
            ungroupFriendListDto.setFriends(ungroupFriends);
            ungroupFriendListDto.setCustom(false);
            friendListDtos.add(ungroupFriendListDto);
        }
        //查询用户当前分组
        List<Group> groups = groupService.getGroupByUserId(userId);
        //遍历分组，查询分组下的好友
        groups.forEach((group) -> {
            List<Friend> friends = friendMapper.getFriendByUserIdAndGroupId(userId, group.getId());
            FriendListDto friendListDto = new FriendListDto();
            friendListDto.setGroupId(group.getId());
            friendListDto.setName(group.getName());
            friendListDto.setFriends(friends);
            friendListDto.setCustom(true);
            friendListDtos.add(friendListDto);
        });
        return friendListDtos;
    }

    @Override
    public boolean isFriend(String userId, String friendId) {
        LambdaQueryWrapper<Friend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Friend::getUserId, userId).eq(Friend::getFriendId, friendId);
        return count(queryWrapper) > 0;
    }

    @Override
    public FriendDetailsDto getFriendDetails(String userId, String friendId) {
        boolean isFriend = isFriend(userId, friendId);
        if (!isFriend) {
            throw new LinyuException("双方非好友");
        }
        FriendDetailsDto friendDetailsDto = friendMapper.getFriendDetails(userId, friendId);
        return friendDetailsDto;
    }

    @Override
    public List<FriendDetailsDto> searchFriends(String userId, SearchFriendsVo searchFriendsVo) {
        List<FriendDetailsDto> friends = friendMapper.searchFriends(userId, "%" + searchFriendsVo.getFriendInfo() + "%");
        return friends;
    }

    /**
     * 添加好友
     *
     * @return
     */
    public boolean addFriend(String userId, String targetId) {
        //判断目标是否是自己好友
        LambdaQueryWrapper<Friend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Friend::getUserId, userId)
                .eq(Friend::getFriendId, targetId);
        if (count(queryWrapper) <= 0) {
            Friend friend = new Friend();
            friend.setId(IdUtil.randomUUID());
            friend.setUserId(userId);
            friend.setFriendId(targetId);
            return save(friend);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean agreeFriendApply(String userId, AgreeFriendApplyVo agreeFriendApplyVo) {
        //判断申请是否是用户发起
        Notify notify = notifyService.getById(agreeFriendApplyVo.getNotifyId());
        if (null == notify
                || !notify.getToId().equals(userId)
                || !notify.getType().equals(NotifyType.Friend_Apply)
                || !notify.getStatus().equals(FriendApplyStatus.Wait)
        ) {
            throw new LinyuException("没有添加好友申请");
        }
        //双方添加好友
        addFriend(userId, notify.getFromId());
        addFriend(notify.getFromId(), userId);
        //更新通知
        notify.setStatus(FriendApplyStatus.Agree);
        notify.setUnreadId(notify.getFromId());
        notifyService.updateById(notify);
        //发送通知
        webSocketService.sendNotifyToUser(notify, notify.getFromId());
        return true;
    }

    @Override
    public boolean updateGroupId(String userId, String oldGroupId, String newGroupId) {
        LambdaUpdateWrapper<Friend> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Friend::getGroupId, newGroupId)
                .eq(Friend::getGroupId, oldGroupId)
                .eq(Friend::getUserId, userId);
        return update(updateWrapper);
    }

    @Override
    public boolean setRemark(String userId, SetRemarkVo setRemarkVo) {
        LambdaUpdateWrapper<Friend> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Friend::getRemark, setRemarkVo.getRemark())
                .eq(Friend::getFriendId, setRemarkVo.getFriendId())
                .eq(Friend::getUserId, userId);
        return update(updateWrapper);
    }
}
