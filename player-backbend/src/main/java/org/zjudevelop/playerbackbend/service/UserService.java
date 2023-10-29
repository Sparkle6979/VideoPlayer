package org.zjudevelop.playerbackbend.service;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.zjudevelop.playerbackbend.dto.UserLoginDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.pojo.User;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);

    User registry(UserRegisterDTO userRegisterDTO);
}
