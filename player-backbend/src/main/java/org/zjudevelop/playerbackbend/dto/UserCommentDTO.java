package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String entityType;
    // 评论的对象Id： 视频Id / 评论Id
    Long entityId;
    // 如果对评论进行评论，评论的目标userId
    Long targetId;
    // 评论内容
    String content;
}
