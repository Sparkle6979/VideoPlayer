package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.MessageMapper;
import org.zjudevelop.playerbackbend.domain.MessagePO;
import org.zjudevelop.playerbackbend.service.MessageService;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 11:49
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<MessagePO> getPublishMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("from_id", userId);
        return messageMapper.selectList(wrapper);
    }

    @Override
    public List<MessagePO> getReceiveMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", userId);
        return messageMapper.selectList(wrapper);
    }
}
