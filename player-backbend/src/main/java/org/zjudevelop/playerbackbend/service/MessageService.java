package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.MessagePO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:58
 */
public interface MessageService {

    List<MessagePO> getPublishMessageByUserId(Long userId);
    List<MessagePO> getReceiveMessageByUserId(Long userId);
}
