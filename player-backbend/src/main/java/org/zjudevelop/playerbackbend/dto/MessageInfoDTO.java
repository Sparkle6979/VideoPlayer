package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageInfoDTO {
    @ApiModelProperty(value = "通知id")
    Long messageId;

    @ApiModelProperty(value = "当前登陆用户id")
    Long currentUserId;

    @ApiModelProperty(value = "当前登陆用户name")
    String currentUserName;

    @ApiModelProperty(value = "当前通知类型" ,allowableValues = "video-like, user-follow, video-comment, user-comment")
    String conversationType;

    @ApiModelProperty(value = "发送通知的userId")
    Long eventUserId;

    @ApiModelProperty(value = "发送通知的userName")
    String eventUserName;

    @ApiModelProperty(value = "通知事件的id (videoId / userId / commentId)")
    Long eventEntityId;


}
