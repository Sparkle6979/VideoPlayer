package org.zjudevelop.playerbackbend.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {

    @ApiModelProperty(value = "查询结果总数量")
    private long total;

    @ApiModelProperty(value = "查询结果")
    private List records;

}
