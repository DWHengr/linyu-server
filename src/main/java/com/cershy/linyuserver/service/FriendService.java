package com.cershy.linyuserver.service;

import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
