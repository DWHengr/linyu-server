package com.cershy.linyuserver.controller;


import cn.hutool.json.JSONObject;
import com.cershy.linyuserver.annotation.Userid;
import com.cershy.linyuserver.dto.GroupListDto;
import com.cershy.linyuserver.service.GroupService;
import com.cershy.linyuserver.utils.ResultUtil;
import com.cershy.linyuserver.vo.group.CreateGroupVo;
import com.cershy.linyuserver.vo.group.DeleteGroupVo;
import com.cershy.linyuserver.vo.group.UpdateGroupVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author heath
 * @since 2024-05-18
 */
@RestController
@RequestMapping("/v1/api/group")
public class GroupController {

    @Resource
    GroupService groupService;

    @PostMapping("/create")
    public JSONObject createGroup(@Userid String userId, @RequestBody CreateGroupVo createGroupVo) {
        boolean flag = groupService.createGroup(userId, createGroupVo);
        return ResultUtil.ResultByFlag(flag);
    }

    @PostMapping("/update")
    public JSONObject updateGroup(@Userid String userId, @RequestBody UpdateGroupVo updateGroupVo) {
        boolean flag = groupService.updateGroup(userId, updateGroupVo);
        return ResultUtil.ResultByFlag(flag);
    }

    @PostMapping("/delete")
    public JSONObject deleteGroup(@Userid String userId, @RequestBody DeleteGroupVo deleteGroupVo) {
        boolean flag = groupService.deleteGroup(userId, deleteGroupVo);
        return ResultUtil.ResultByFlag(flag);
    }

    /**
     * 获取用户分组
     */
    @GetMapping("/list")
    public JSONObject getList(@Userid String userId) {
        List<GroupListDto> result = groupService.getList(userId);
        return ResultUtil.Succeed(result);
    }
}

