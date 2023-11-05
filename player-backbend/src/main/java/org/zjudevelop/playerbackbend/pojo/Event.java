package org.zjudevelop.playerbackbend.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    // topic名称
    private String topic;
    // 发送事件的userid
    private Long userId;
    // 事件发送目标的userid
    private Long entityUserId;
    // 事件的类型
    private String entityType;
    // 事件（点赞/关注）的Id
    private Long entityId;
}
