package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.FriendDetailsDto;
import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.friend.AgreeFriendApplyVo;
import com.cershy.linyuserver.vo.friend.SearchFriendsVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author heath
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/v1/api/friend")
public class FriendController {

    @Resource
    FriendService friendService;

    /**
     * 获取好友列表
     *
     * @return
     */
    @GetMapping("/list")
    public JSONObject getFriendList(@Userid String userId) {
        List<FriendListDto> friendListDto = friendService.getFriendList(userId);
        return ResultUtil.Succeed(friendListDto);
    }

    /**
     * 获取好友详情
     *
     * @return
     */
    @GetMapping("/details/{friendId}")
    public JSONObject getFriendDetails(@Userid String userId, @PathVariable String friendId) {
        FriendDetailsDto friendDetailsDto = friendService.getFriendDetails(userId, friendId);
        return ResultUtil.Succeed(friendDetailsDto);
    }

    /**
     * 搜索好友
     *
     * @return
     */
    @PostMapping("/search")
    public JSONObject searchFriends(@Userid String userId, @RequestBody SearchFriendsVo searchFriendsVo) {
        List<FriendDetailsDto> result = friendService.searchFriends(userId, searchFriendsVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 同意好友请求
     *
     * @return
     */
    @PostMapping("/agree")
    public JSONObject agreeFriendApply(@Userid String userId, @RequestBody AgreeFriendApplyVo agreeFriendApplyVo) {
        boolean result = friendService.agreeFriendApply(userId, agreeFriendApplyVo);
        return ResultUtil.Succeed(result);
    }
}

