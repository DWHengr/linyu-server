package com.cershy.linyuserver.service;

import com.cershy.linyuserver.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.group.CreateGroupVo;
import com.cershy.linyuserver.vo.group.DeleteGroupVo;
import com.cershy.linyuserver.vo.group.UpdateGroupVo;

import java.util.List;

/**
 * <p>
 * 分组表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
public interface GroupService extends IService<Group> {

    List<Group> getGroupByUserId(String userId);

    boolean createGroup(String userId, CreateGroupVo createGroupVo);

    boolean updateGroup(String userId, UpdateGroupVo updateGroupVo);

    boolean deleteGroup(String userId, DeleteGroupVo deleteGroupVo);
}
