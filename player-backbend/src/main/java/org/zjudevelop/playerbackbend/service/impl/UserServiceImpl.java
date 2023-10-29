package org.zjudevelop.playerbackbend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.UserMapper;
import org.zjudevelop.playerbackbend.dto.UserLoginDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.pojo.User;
import org.zjudevelop.playerbackbend.pojo.exception.AccountNotFoundException;
import org.zjudevelop.playerbackbend.pojo.exception.BaseException;
import org.zjudevelop.playerbackbend.pojo.exception.PasswordErrorException;
import org.zjudevelop.playerbackbend.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO){
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException();
        }
        log.info("user: " + user.toString());
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException();
        }
        return user;
    }

    @Override
    public User registry(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        User user = new User().builder()
                .username(username)
                .password(password)
                .build();
        int registerResult = userMapper.insert(user);
        log.info("registerResult: " + registerResult);
        // TODO: fix the bug that id still auto-increment when insertion failure
        if (registerResult != 1) {
            // TODO: add specific exception class
            throw new BaseException("注册失败");
        }
        return user;
    }
}
