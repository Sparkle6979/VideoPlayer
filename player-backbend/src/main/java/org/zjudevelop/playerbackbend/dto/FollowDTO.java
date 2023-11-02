package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDTO {
    @ApiModelProperty(value = "用户id")
    private Long followerId;
    @ApiModelProperty(value = "被关注用户id")
    private Long followingId;
}
