package org.zjudevelop.playerbackbend.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfoDTO implements Serializable {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "token", example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTg1OTE1MTksInVzZXIiOjF9.PKm44-5FrffRB34LLDW92R_6sPjBdX8TQdQrQxoF0mc")
    private String token;
}
