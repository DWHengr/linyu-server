package com.cershy.linyuserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cershy.linyuserver.dto.SetsDto;
import com.cershy.linyuserver.entity.UserSet;
import com.cershy.linyuserver.mapper.UserSetMapper;
import com.cershy.linyuserver.service.UserSetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuserver.vo.userSet.UpdateUserSetVo;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * <p>
 * 用户设置表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-07-16
 */
@Service
public class UserSetServiceImpl extends ServiceImpl<UserSetMapper, UserSet> implements UserSetService {

    @Override
    public UserSet getUserSet(String userId) {
        LambdaQueryWrapper<UserSet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSet::getUserId, userId);
        UserSet userSet = getOne(queryWrapper);
        if (null == userSet) {
            userSet = new UserSet();
            userSet.setId(IdUtil.randomUUID());
            userSet.setUserId(userId);
            userSet.setSets(SetsDto.defaultSets());
            save(userSet);
        }
        return userSet;
    }

    @Override
    public boolean updateUserSet(String userId, UpdateUserSetVo updateUserSetVo) {
        LambdaQueryWrapper<UserSet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSet::getUserId, userId);
        UserSet userSet = getOne(queryWrapper);
        if (userSet == null) {
            return false;
        }
        SetsDto sets = userSet.getSets();
        try {
            Field field = SetsDto.class.getDeclaredField(updateUserSetVo.getKey());
            field.setAccessible(true);
            field.set(sets, updateUserSetVo.getValue());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return updateById(userSet);
    }
}
