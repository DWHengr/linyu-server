package com.cershy.linyuserver.service;

import com.cershy.linyuserver.admin.vo.notify.DeleteNotifyVo;
import com.cershy.linyuserver.dto.FriendNotifyDto;
import com.cershy.linyuserver.dto.SystemNotifyDto;
import com.cershy.linyuserver.entity.Notify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.notify.FriendApplyNotifyVo;
import com.cershy.linyuserver.vo.notify.ReadNotifyVo;

import java.util.List;

/**
 * <p>
 * 通知 服务类
 * </p>
 *
 * @author heath
 * @since 2024-05-28
 */
public interface NotifyService extends IService<Notify> {

    boolean friendApplyNotify(String userId, FriendApplyNotifyVo friendApplyNotifyVo);

    List<FriendNotifyDto> friendListNotify(String userId);

    int unread(String userId);

    int unreadByType(String userId, String type);

    boolean readNotify(String userId, ReadNotifyVo readNotifyVo);

    List<SystemNotifyDto> SystemListNotify(String userId);

    boolean deleteNotify(DeleteNotifyVo deleteNotifyVo);

    boolean createNotify(String url, String title, String text);
}
