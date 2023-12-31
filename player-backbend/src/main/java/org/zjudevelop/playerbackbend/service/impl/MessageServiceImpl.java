package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.MessageMapper;
import org.zjudevelop.playerbackbend.domain.po.MessagePO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.MessageService;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 11:49
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper,MessagePO> implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<MessagePO> getAllPublishMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("from_id", userId);
        return messageMapper.selectList(wrapper);
    }

    @Override
    public List<MessagePO> getAllReceiveMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", userId);
        return messageMapper.selectList(wrapper);
    }

    @Override
    public List<MessagePO> getUnreadReceiveMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", userId);
        wrapper.eq("status",0);

        return messageMapper.selectList(wrapper);
    }

    @Override
    public List<MessagePO> getUnreadReceiveLikeMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", userId);
        wrapper.eq("status",0);
        wrapper.eq("conversation_type",MessageConstant.EVENT_VIDEO_LIKE);

        return messageMapper.selectList(wrapper);
    }

    @Override
    public List<MessagePO> getUnreadReceiveFollowMessageByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", userId);
        wrapper.eq("status",0);
        wrapper.eq("conversation_type",MessageConstant.EVENT_USER_FOLLOW);

        return messageMapper.selectList(wrapper);
    }

    @Override
    public List<MessagePO> getUnreadReceiveCommentMessageByUserId(Long userId) {
        QueryWrapper<MessagePO> wrapper = new QueryWrapper<>();
        wrapper.eq("to_id", userId);
        wrapper.eq("status",0);
        wrapper.and( wq -> {
           wq.eq("conversation_type",MessageConstant.EVENT_USER_COMMENT)
                   .or()
                   .eq("conversation_type",MessageConstant.EVENT_VIDEO_COMMENT);
        });
        return messageMapper.selectList(wrapper);
    }

    @Override
    public int insertMessage(MessagePO messagePO) {
        return messageMapper.insert(messagePO);
    }

    @Override
    public MessagePO readMessage(Long messageId) {
        MessagePO messagePO = messageMapper.selectById(messageId);
        messagePO.setStatus((long) 1);
        messageMapper.updateById(messagePO);
        return messagePO;
    }
}
