package org.zjudevelop.playerbackbend.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionInfoDTO implements Serializable {

    @ApiModelProperty("收藏夹id")
    private Long id;

    @ApiModelProperty("收藏夹名字")
    private String collectionName;

}
