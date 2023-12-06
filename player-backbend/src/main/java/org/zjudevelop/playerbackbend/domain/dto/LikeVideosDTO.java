package org.zjudevelop.playerbackbend.domain.dto;

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
public class LikeVideosDTO {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "点赞过的视频id列表")
    private List<Long> videoIds;
}
