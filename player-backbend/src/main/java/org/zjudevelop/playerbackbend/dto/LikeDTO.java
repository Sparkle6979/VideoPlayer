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
public class LikeDTO {
    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "点赞视频id")
    private Long videoId;
}
