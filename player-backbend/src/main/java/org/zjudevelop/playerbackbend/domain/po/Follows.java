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
@TableName("`follows`")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follows implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long followerId;

    private Long followingId;

}
