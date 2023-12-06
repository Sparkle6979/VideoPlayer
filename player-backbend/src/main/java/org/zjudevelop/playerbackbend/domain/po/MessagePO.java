package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:32
 */

@Data
@Builder
@TableName(value = "message")
public class MessagePO implements Serializable {
    @TableId(type = IdType.AUTO)
    Long id;

    /**
     * 发送者的id
     */
    @TableField("from_id")
    Long fromId;

    /**
     * 接收者的id
     */
    @TableField("to_id")
    Long toId;

    /**
     * 消息类型
     */
    @TableField("conversation_type")
    String conversationType;

    /**
     * 消息内容
     */
    String content;

    Long status;

    @TableField("create_time")
    private Timestamp createTime;
}
