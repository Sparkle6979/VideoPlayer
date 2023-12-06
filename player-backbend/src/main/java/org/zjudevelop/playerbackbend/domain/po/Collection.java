package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value = "collection")
public class Collection {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String collectionName;

    // TODO: 是否需要其他字段，比如封面？
}