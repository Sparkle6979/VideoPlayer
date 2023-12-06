package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 11:13
 */
@Data
@Builder
@TableName(value = "creates")
public class Creates {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;

    private Long videoId;
}