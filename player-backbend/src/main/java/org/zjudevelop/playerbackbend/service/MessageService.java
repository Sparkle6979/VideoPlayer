package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.po.MessagePO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:58
 */
public interface MessageService extends IService<MessagePO> {

    List<MessagePO> getAllPublishMessageByUserId(Long userId);
    List<MessagePO> getAllReceiveMessageByUserId(Long userId);

    List<MessagePO> getUnreadReceiveMessageByUserId(Long userId);

    List<MessagePO> getUnreadReceiveLikeMessageByUserId(Long userId);

    List<MessagePO> getUnreadReceiveFollowMessageByUserId(Long userId);

    List<MessagePO> getUnreadReceiveCommentMessageByUserId(Long userId);

    int insertMessage(MessagePO messagePO);

    MessagePO readMessage(Long MessageId);
}
