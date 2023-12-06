package org.zjudevelop.playerbackbend.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageQueryDTO implements Serializable {

    @ApiModelProperty(value = "页数", required = true)
    private int page;

    @ApiModelProperty(value = "每页大小", required = true)
    private int pageSize;
}
