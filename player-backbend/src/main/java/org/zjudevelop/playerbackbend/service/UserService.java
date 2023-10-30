package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.dto.UserLoginDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.domain.User;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);

    User registry(UserRegisterDTO userRegisterDTO);

    User getUserById(Long id);
}
