package com.cershy.linyuserver.service.impl;

import com.cershy.linyuserver.entity.ChatList;
import com.cershy.linyuserver.mapper.ChatListMapper;
import com.cershy.linyuserver.service.ChatListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天列表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements ChatListService {

}
