package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.dto.GroupListDto;
import com.cershy.linyuserver.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 分组表 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
public interface GroupMapper extends BaseMapper<Group> {

    @Select("SELECT `name` AS `label`,`id` AS `value` FROM `group` WHERE `user_id` = #{userId}")
    List<GroupListDto> getList(String userId);
}
