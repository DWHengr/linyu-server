package com.cershy.linyuserver.service.impl;

import com.cershy.linyuserver.entity.Message;
import com.cershy.linyuserver.mapper.MessageMapper;
import com.cershy.linyuserver.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
