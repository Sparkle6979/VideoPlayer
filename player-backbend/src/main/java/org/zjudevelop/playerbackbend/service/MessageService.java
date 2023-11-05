package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.MessagePO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:58
 */
public interface MessageService {

    List<MessagePO> getAllPublishMessageByUserId(Long userId);
    List<MessagePO> getAllReceiveMessageByUserId(Long userId);

    List<MessagePO> getUnreadReceiveMessageByUserId(Long userId);

    int insertMessage(MessagePO messagePO);
}
