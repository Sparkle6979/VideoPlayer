package org.zjudevelop.playerbackbend.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:15
 */
@Data
@Builder
public class VideoCommentDTO {
    @ApiModelProperty(value = "评论id")
    Long commentId;

    @ApiModelProperty(value = "评论者的userId")
    Long commentUserId;

    @ApiModelProperty(value = "评论者的userName")
    String commentUserName;

    @ApiModelProperty(value = "评论者的头像URL")
    String commentUserAvatarPath;

    @ApiModelProperty(value = "评论内容")
    String content;

    @ApiModelProperty(value = "被评论者的userId")
    Long targetUserId;

    @ApiModelProperty(value = "被评论者的userName")
    String targetUserName;

    @ApiModelProperty(value = "评论时间")
    String createTime;

    @ApiModelProperty(value = "展示对话框")
    boolean isdisplay;

    @ApiModelProperty(value = "追评")
    List<VideoCommentDTO> commentReply;
}
