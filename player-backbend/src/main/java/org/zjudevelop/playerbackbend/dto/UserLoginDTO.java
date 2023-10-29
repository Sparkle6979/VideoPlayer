package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO implements Serializable {

    // 用户名
    private String username;

    // 密码（md5加密）
    private String password;
}