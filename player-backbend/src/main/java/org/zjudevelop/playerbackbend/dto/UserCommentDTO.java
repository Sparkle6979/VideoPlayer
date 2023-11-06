package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 11:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommentDTO {
    // 评论的类型：视频(video) / 评论(comment)
    @ApiModelProperty(value = "评论的目标类型", required = true, allowableValues = "video, comment")
    String entityType;

    @ApiModelProperty(value = "评论的目标Id （videoId/commentId）",required = true)
    // 评论的对象Id： 视频Id / 评论Id
    Long entityId;

    @ApiModelProperty(value = "评论的目标userId（如果是对评论的评论）")
    // 如果对评论进行评论，评论的目标userId
    Long targetId;

    @ApiModelProperty(value = "评论内容")
    // 评论内容
    String content;
}
