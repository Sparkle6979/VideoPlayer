package org.zjudevelop.playerbackbend.event;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.zjudevelop.playerbackbend.domain.po.MessagePO;
import org.zjudevelop.playerbackbend.pojo.Event;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.MessageService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:53
 */
@Slf4j
@Component
public class EventConsumer extends MessageConstant {
    @Autowired
    private MessageService messageService;

    @KafkaListener(topics = {TOPIC_FOLLOW, TOPIC_LIKE, TOPIC_COMMENT})
    public void handleCommentMessage(ConsumerRecord record) {

        if (record == null || record.value() == null) {
            log.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);

        if (event == null) {
            log.error("消息格式错误!");
            return;
        }

        MessagePO build = MessagePO.builder()
                .fromId(event.getUserId())
                .toId(event.getEntityUserId())
                .conversationType(event.getEntityType())
                .build();


        Map<String,Object> content = new HashMap<>();
        content.put("noticeType",event.getEntityType());
        content.put("noticeId",event.getEntityId());

        build.setContent(JSONObject.toJSONString(content));
        messageService.insertMessage(build);
    }

}
