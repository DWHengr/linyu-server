package com.cershy.linyuserver.service;

import com.cershy.linyuserver.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
