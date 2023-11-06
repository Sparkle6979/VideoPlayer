package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoCommentsPageQueryDTO {
    @ApiModelProperty(value = "视频Id", required = true)
    private Long videoId;

    @ApiModelProperty(value = "页数", required = true)
    private int page;

    @ApiModelProperty(value = "每页大小", required = true)
    private int pageSize;
}
