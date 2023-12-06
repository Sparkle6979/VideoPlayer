package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 23:13
 */
@Data
@Builder
@TableName(value = "comment")
public class CommentPO {
    @TableId(type = IdType.AUTO)
    Long id;

    @TableField("user_id")
    Long userId;

    @TableField("entity_type")
    String entityType;

    @TableField("entity_id")
    Long entityId;

    @TableField("target_id")
    Long targetId;

    String content;

    @TableField("create_time")
    Timestamp createTime;
}
