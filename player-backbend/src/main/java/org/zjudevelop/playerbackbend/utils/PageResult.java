package org.zjudevelop.playerbackbend.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 17:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    @ApiModelProperty(value = "查询结果总数量")
    private long total;

    @ApiModelProperty(value = "查询结果")
    private List records;
}
