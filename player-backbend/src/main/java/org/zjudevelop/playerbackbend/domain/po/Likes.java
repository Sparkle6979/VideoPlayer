package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("`likes`")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Likes implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long videoId;

}
