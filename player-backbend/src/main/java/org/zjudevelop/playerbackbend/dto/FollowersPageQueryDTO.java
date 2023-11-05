package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowersPageQueryDTO implements Serializable {

    @ApiModelProperty(value = "用户id", required = true)
    private Long id;

    @ApiModelProperty(value = "页数", required = true)
    private int page;

    @ApiModelProperty(value = "每页大小", required = true)
    private int pageSize;
}
