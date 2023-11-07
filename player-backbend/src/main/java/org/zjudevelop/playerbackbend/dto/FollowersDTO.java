package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowersDTO {
    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "粉丝列表")
    private List<Long> followerIds;
}
