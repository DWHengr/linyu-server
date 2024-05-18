package com.cershy.linyuserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.dto.FriendList;
import com.cershy.linyuserver.entity.Friend;
import com.cershy.linyuserver.entity.Group;
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
    public List<FriendList> getFriendList(String userId) {
        List<FriendList> friendLists = new ArrayList<>();
        //将没有分组的好像添加到未分组中
        List<Friend> ungroupFriends = friendMapper.getFriendByUserIdAndGroupId(userId, "0");
        if (null != ungroupFriends && ungroupFriends.size() > 0) {
            FriendList ungroupFriendList = new FriendList();
            ungroupFriendList.setName("未分组");
            ungroupFriendList.setFriends(ungroupFriends);
            friendLists.add(ungroupFriendList);
        }
        //查询用户当前分组
        List<Group> groups = groupService.getGroupByUserId(userId);
        //遍历分组，查询分组下的好友
        groups.forEach((group) -> {
            List<Friend> friends = friendMapper.getFriendByUserIdAndGroupId(userId, group.getId());
            FriendList friendList = new FriendList();
            friendList.setGroupId(group.getId());
            friendList.setName(group.getName());
            friendList.setFriends(friends);
            friendLists.add(friendList);
        });
        return friendLists;
    }
}
