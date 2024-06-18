package com.cershy.linyuserver.mapper;

import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from `user` where `id` = #{userId}")
    UserDto info(String userId);

    @Select("SELECT * FROM user WHERE account = #{userInfo} OR phone = #{userInfo} OR email = #{userInfo}")
    List<UserDto> findUserByInfo(String userInfo);
}
