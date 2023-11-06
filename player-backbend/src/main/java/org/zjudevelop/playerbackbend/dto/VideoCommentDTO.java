package org.zjudevelop.playerbackbend.dto;

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
    Long commentId;

    Long commentUserId;

    String commentUserName;

    String content;

    Long targetUserId;

    String targetUserName;

    String createTime;

    List<VideoCommentDTO> commentReply;
}
