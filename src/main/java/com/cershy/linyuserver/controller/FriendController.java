package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.FriendDetailsDto;
import com.cershy.linyuserver.dto.FriendListDto;
import com.cershy.linyuserver.entity.Friend;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.utils.SecurityUtil;
import com.cershy.linyuserver.vo.friend.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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
     * 获取好友列表
     *
     * @return
     */
    @GetMapping("/list/flat")
    public JSONObject getFriendListFlat(@Userid String userId, @RequestParam(defaultValue = "") String friendInfo) {
        List<Friend> friendListDto = friendService.getFriendListFlat(userId, friendInfo);
        return ResultUtil.Succeed(friendListDto);
    }

    /**
     * 判断是否是好友
     */
    @GetMapping("/is/friend")
    public JSONObject isFriend(@Userid String userId, @RequestParam String targetId) {
        boolean result = friendService.isFriend(userId, targetId);
        return ResultUtil.Succeed(result);
    }

    /**
     * 获取好友列表(未读消息数)
     *
     * @return
     */
    @GetMapping("/list/flat/unread")
    public JSONObject getFriendListFlatUnread(@Userid String userId, @RequestParam(defaultValue = "") String friendInfo) {
        List<Friend> friendListDto = friendService.getFriendListFlatUnread(userId, friendInfo);
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

    /**
     * 同意好友请求
     *
     * @return
     */
    @PostMapping("/agree/id")
    public JSONObject agreeFriendApplyFromId(@Userid String userId, @RequestBody AgreeFriendApplyVo agreeFriendApplyVo) {
        boolean result = friendService.agreeFriendApply(userId, agreeFriendApplyVo.getFromId());
        return ResultUtil.Succeed(result);
    }

    /**
     * 拒绝好友请求
     *
     * @return
     */
    @PostMapping("/reject")
    public JSONObject refuseFriendApply(@Userid String userId, @RequestBody RejectFriendApplyVo friendApplyVo) {
        boolean result = friendService.rejectFriendApply(userId, friendApplyVo.getFromId());
        return ResultUtil.Succeed(result);
    }


    /**
     * 扫码好友请求
     *
     * @return
     */
    @PostMapping("/add/qr")
    public JSONObject addFriendByQr(@Userid String userId, @RequestBody AddFriendByQrVo AddFriendByQrVo) {
        String targetId = SecurityUtil.aesDecrypt(AddFriendByQrVo.getQrCode());
        boolean result = friendService.addFriendApply(userId, targetId);
        return ResultUtil.Succeed(result);
    }

    /**
     * 设置好友备注
     *
     * @return
     */
    @PostMapping("/set/remark")
    public JSONObject setRemark(@Userid String userId, @RequestBody SetRemarkVo setRemarkVo) {
        boolean result = friendService.setRemark(userId, setRemarkVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 设置好友分组
     *
     * @return
     */
    @PostMapping("/set/group")
    public JSONObject setGroup(@Userid String userId, @RequestBody SetGroupVo setGroupVo) {
        boolean result = friendService.setGroup(userId, setGroupVo);
        return ResultUtil.Succeed(result);
    }

    /**
     * 删除好友
     *
     * @return
     */
    @PostMapping("/delete")
    public JSONObject deleteFriend(@Userid String userId, @RequestBody DeleteFriendVo deleteFriendVo) {
        boolean result = friendService.deleteFriend(userId, deleteFriendVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 特别关心
     *
     * @return
     */
    @PostMapping("/carefor")
    public JSONObject careForFriend(@Userid String userId, @RequestBody CareForFriendVo careForFriendVo) {
        boolean result = friendService.careForFriend(userId, careForFriendVo);
        return ResultUtil.ResultByFlag(result);
    }

    /**
     * 特别关心
     *
     * @return
     */
    @PostMapping("/uncarefor")
    public JSONObject unCareForFriend(@Userid String userId, @RequestBody UnCareForFriendVo unCareForFriendVo) {
        boolean result = friendService.unCareForFriend(userId, unCareForFriendVo);
        return ResultUtil.ResultByFlag(result);
    }
}

