package com.cershy.linyuserver.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cershy.linyuserver.admin.vo.user.*;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.user.RegisterVo;
import com.cershy.linyuserver.vo.user.SearchUserVo;
import com.cershy.linyuserver.vo.user.UpdateVo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
public interface UserService extends IService<User> {
    JSONObject validateLogin(LoginVo loginVo, boolean isAdmin);

    List<UserDto> searchUser(SearchUserVo searchUserVo);

    HashMap<String, Integer> unreadInfo(String userId);

    UserDto info(String userId);

    boolean updateUserInfo(String userId, UpdateVo updateVo);

    boolean updateUserPortrait(String userId, String portrait);

    boolean register(RegisterVo registerVo);

    Page<User> userList(UserListVo userListVo);

    void offline(String userId);

    void online(String userId);

    boolean createUser(CreateUserVo createUserVo);

    boolean allUserOffline();

    boolean disableUser(String userId, DisableUserVo disableUserVo);

    boolean deleteUser(String userid, DeleteUserVo deleteUserVo);

    boolean undisableUser(UndisableUserVo undisableUserVo);

    boolean updateUser(UpdateUserVo updateUserVo);
}
