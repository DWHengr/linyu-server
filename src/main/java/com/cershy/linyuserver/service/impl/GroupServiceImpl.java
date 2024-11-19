package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cershy.linyuserver.dto.GroupListDto;
import com.cershy.linyuserver.entity.Group;
import com.cershy.linyuserver.exception.LinyuException;
import com.cershy.linyuserver.mapper.GroupMapper;
import com.cershy.linyuserver.service.FriendService;
import com.cershy.linyuserver.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.vo.group.CreateGroupVo;
import com.cershy.linyuserver.vo.group.DeleteGroupVo;
import com.cershy.linyuserver.vo.group.UpdateGroupVo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分组表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Lazy
    @Resource
    FriendService friendService;

    @Resource
    GroupMapper groupMapper;

    @Override
    public List<Group> getGroupByUserId(String userId) {
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getUserId, userId).orderByAsc(Group::getName);
        return list(queryWrapper);
    }

    @Override
    public boolean createGroup(String userId, CreateGroupVo createGroupVo) {
        if (isExistGroupNameByUserId(userId, createGroupVo.getGroupName())) {
            throw new LinyuException("分组名已存在~");
        }
        Group group = new Group();
        group.setId(IdUtil.randomUUID());
        group.setUserId(userId);
        group.setName(createGroupVo.getGroupName());
        return save(group);
    }

    @Override
    public boolean updateGroup(String userId, UpdateGroupVo updateGroupVo) {
        if (isExistGroupNameByUserId(userId, updateGroupVo.getGroupName())) {
            throw new LinyuException("分组名已存在~");
        }
        LambdaUpdateWrapper<Group> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Group::getName, updateGroupVo.getGroupName())
                .eq(Group::getUserId, userId)
                .eq(Group::getId, updateGroupVo.getGroupId());
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteGroup(String userId, DeleteGroupVo deleteGroupVo) {
        //将该分组下好友设置为未分组
        friendService.updateGroupId(userId, deleteGroupVo.getGroupId(), "0");
        //删除分组
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getId, deleteGroupVo.getGroupId())
                .eq(Group::getUserId, userId);
        return remove(queryWrapper);
    }

    @Override
    public List<GroupListDto> getList(String userId) {
        List<GroupListDto> list = groupMapper.getList(userId);
        if (list == null)
            list = new ArrayList<>();
        //未分组
        GroupListDto groupListDto = new GroupListDto();
        groupListDto.setLabel("未分组");
        groupListDto.setValue("0");
        list.add(groupListDto);
        return list;
    }

    @Override
    public boolean IsExistGroupByUserId(String userId, String GroupId) {
        if ("0".equals(GroupId)) {
            return true;
        }
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getUserId, userId).eq(Group::getId, GroupId);
        return count(queryWrapper) > 0;
    }

    public boolean isExistGroupNameByUserId(String userId, String groupName) {
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getUserId, userId).eq(Group::getName, groupName);
        return count(queryWrapper) > 0;
    }
}
