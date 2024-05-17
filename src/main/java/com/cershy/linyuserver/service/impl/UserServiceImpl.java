package com.cershy.linyuserver.service.impl;

import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.mapper.UserMapper;
import com.cershy.linyuserver.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
