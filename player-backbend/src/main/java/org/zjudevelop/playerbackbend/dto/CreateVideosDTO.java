package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateVideosDTO {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "发布的视频id列表")
    private List<Long> videoIds;
}
