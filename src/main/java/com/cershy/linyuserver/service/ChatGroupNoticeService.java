package com.cershy.linyuserver.service;

import com.cershy.linyuserver.entity.ChatGroupNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuserver.vo.chatGroupNotice.CreateNoticeVo;
import com.cershy.linyuserver.vo.chatGroupNotice.DeleteNoticeVo;
import com.cershy.linyuserver.vo.chatGroupNotice.NoticeListVo;
import com.cershy.linyuserver.vo.chatGroupNotice.UpdateNoticeVo;

import java.util.List;

/**
 * <p>
 * 聊天群公告表 服务类
 * </p>
 *
 * @author heath
 * @since 2024-09-02
 */
public interface ChatGroupNoticeService extends IService<ChatGroupNotice> {

    List<ChatGroupNotice> noticeList(String userId, NoticeListVo noticeListVo);

    boolean createNotice(String userId, CreateNoticeVo createNoticeVo);

    boolean deleteNotice(String userId, DeleteNoticeVo deleteNoticeVo);

    boolean updateNotice(String userId, UpdateNoticeVo updateNoticeVo);
}
