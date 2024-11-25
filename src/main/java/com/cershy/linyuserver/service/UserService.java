package com.cershy.linyuserver.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.admin.vo.user.*;
import com.cershy.linyuserver.dto.UserDto;
import com.cershy.linyuserver.entity.User;
import com.cershy.linyuserver.vo.login.LoginVo;
import com.cershy.linyuserver.vo.login.QrCodeLoginVo;
import com.cershy.linyuserver.vo.user.*;
import org.springframework.web.multipart.MultipartFile;

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
    JSONObject validateLogin(LoginVo loginVo, String userIp, boolean isAdmin);

    List<UserDto> searchUser(SearchUserVo searchUserVo);

    HashMap<String, Integer> unreadInfo(String userId);

    UserDto info(String userId);

    boolean updateUserInfo(String userId, UpdateVo updateVo);

    boolean updateUserInfo(String userId, UpdatePasswordVo updateVo);

    boolean updateUserPortrait(String userId, String portrait);

    boolean register(RegisterVo registerVo);

    boolean forget(ForgetVo forgetVo);

    Page<User> userList(UserListVo userListVo);

    void offline(String userId);

    void online(String userId);

    boolean createUser(CreateUserVo createUserVo);

    String createThirdPartyUser(MultipartFile portrait, String name);

    boolean updateThirdPartyUser(MultipartFile portrait, String name, String userId);

    boolean allUserOffline();

    boolean disableUser(String userId, DisableUserVo disableUserVo);

    boolean deleteUser(String userid, DeleteUserVo deleteUserVo);

    boolean undisableUser(UndisableUserVo undisableUserVo);

    boolean updateUser(UpdateUserVo updateUserVo);

    boolean restPassword(ResetPasswordVo resetPasswordVo);

    boolean setAdmin(String userid, SetAdminVo setAdminVo);

    boolean cancelAdmin(String userid, CancelAdminVo cancelAdminVo);

    boolean deleteThirdPartyUser(String userId);

    List<User> getUserByEmail(String email);

    User getUserByAccount(String account);

    JSONObject validateQrCodeLogin(QrCodeLoginVo qrCodeLoginVo, String userid);

    void emailVerifyByAccount(String account);
}
