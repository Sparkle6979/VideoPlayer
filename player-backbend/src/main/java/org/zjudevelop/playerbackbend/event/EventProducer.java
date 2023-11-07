package org.zjudevelop.playerbackbend.event;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.zjudevelop.playerbackbend.pojo.Event;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:44
 */

/**
 * 事件通知的生产者，通过KafkaTemplate实现
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 发布事件
    public void fireEvent(Event event){
        // 将事件发送到指定主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
