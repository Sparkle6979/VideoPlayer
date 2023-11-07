package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO implements Serializable {

    // 用户名
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    // 密码（md5加密）
    @ApiModelProperty(value = "密码（md5加密后）", required = true, example = "e10adc3949ba59abbe56e057f20f883e")
    private String password;
}
