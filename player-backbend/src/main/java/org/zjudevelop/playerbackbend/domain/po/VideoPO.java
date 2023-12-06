package org.zjudevelop.playerbackbend.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 19:15
 */
@Data
@Builder
@TableName(value = "video")
public class VideoPO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 视频名称
     */
    private String title;

    /**
     * 视频所属范围id
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 视频点赞数
     */
    @TableField("like_count")
    private Long likeCount;

    /**
     * 视频简介
     */
    private String description;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Timestamp createTime;

    /**
     * 视频云服务器对应url
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * 视频封面对应url
     */
    @TableField("cover_url")
    private String coverUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoPO video = (VideoPO) o;
        return Objects.equals(id, video.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
