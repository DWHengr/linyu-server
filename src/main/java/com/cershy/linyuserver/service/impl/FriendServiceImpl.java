package com.cershy.linyuserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.dto.FriendDetailsDto;
import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.entity.Friend;
import com.cershy.linyuserver.entity.Group;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.FriendMapper;
import com.cershy.linyuserver.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.service.GroupService;
import org.springframework.stereotype.Service;

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


    @Override
    public List<FriendListDto> getFriendList(String userId) {
        List<FriendListDto> friendListDtos = new ArrayList<>();
        //将没有分组的好像添加到未分组中
        List<Friend> ungroupFriends = friendMapper.getFriendByUserIdAndGroupId(userId, "0");
        if (null != ungroupFriends && ungroupFriends.size() > 0) {
            FriendListDto ungroupFriendListDto = new FriendListDto();
            ungroupFriendListDto.setName("未分组");
            ungroupFriendListDto.setFriends(ungroupFriends);
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
}
