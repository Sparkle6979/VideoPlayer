package org.zjudevelop.playerbackbend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoUpdateDTO {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码（经md5加密后）")
    private String password;
    @ApiModelProperty(value = "头像文件")
    private MultipartFile avatarFile;
}
