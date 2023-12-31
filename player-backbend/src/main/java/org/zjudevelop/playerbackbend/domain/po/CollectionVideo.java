package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@TableName(value = "collection_video")
public class CollectionVideo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long collectionId;

    private Long videoId;
}