package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterInfoDTO implements Serializable {

    @ApiModelProperty(value = "用户id", example = "29")
    private Long id;

    @ApiModelProperty(value = "用户名", example = "周兴哲")
    private String username;

}
