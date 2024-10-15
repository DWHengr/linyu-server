package com.cershy.linyuserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cershy.linyuserver.dto.UserOperatedDto;
import com.cershy.linyuserver.entity.UserOperated;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserOperatedMapper extends BaseMapper<UserOperated> {

    @Select("SELECT uo.*, u.name, u.account, u.email, u.phone, u.portrait " +
            "FROM user_operated AS uo " +
            "JOIN user AS u ON u.id = uo.user_id " +
            "WHERE u.account LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR u.name LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR u.email LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY uo.create_time DESC " +
            "LIMIT #{index}, #{num}")
    List<UserOperatedDto> loginDetails(@Param("index") int index, @Param("num") int num, @Param("keyword") String keyword);
}
