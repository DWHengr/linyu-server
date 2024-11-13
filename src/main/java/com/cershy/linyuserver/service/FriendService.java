package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.FriendDetailsDto;
import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.friend.*;

import java.util.List;

/**
 * <p>
 * 好友表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
public interface FriendService extends IService<Friend> {
    List<FriendListDto> getFriendList(String userId);

    boolean isFriend(String userId, String friendId);

    FriendDetailsDto getFriendDetails(String userId, String friendId);

    List<FriendDetailsDto> searchFriends(String userId, SearchFriendsVo searchFriendsVo);

    boolean agreeFriendApply(String userId, AgreeFriendApplyVo agreeFriendApplyVo);

    boolean addFriendApply(String userId, String targetId);

    boolean updateGroupId(String userId, String oldGroupId, String newGroupId);

    boolean setRemark(String userId, SetRemarkVo setRemarkVo);

    boolean setGroup(String userId, SetGroupVo setGroupVo);

    boolean deleteFriend(String userId, DeleteFriendVo deleteFriendVo);

    boolean careForFriend(String userId, CareForFriendVo careForFriendVo);

    boolean unCareForFriend(String userId, UnCareForFriendVo unCareForFriendVo);

    List<Friend> getFriendListFlat(String userId, String friendInfo);

    List<Friend> getFriendListFlatUnread(String userId, String friendInfo);
}
