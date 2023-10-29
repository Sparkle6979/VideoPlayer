package org.zjudevelop.playerbackbend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterInfoDTO;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.pojo.User;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.utils.JwtUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;
import org.zjudevelop.playerbackbend.dto.UserLoginInfoDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtProperties jwtProperties;

    /**
     * user login
     * */
    @PostMapping("login")
    public RestResult<UserLoginInfoDTO> login(@RequestBody org.zjudevelop.playerbackbend.dto.UserLoginDTO userLoginDTO){
        User user = null;
        try {
            user = userService.login(userLoginDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return RestResult.fail(e.getMessage());
        }

        // generate token
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginInfoDTO userLoginVO = UserLoginInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

        return RestResult.success(userLoginVO);
    }

    /**
     * user registry
     * */
    @PostMapping("register")
    public RestResult<UserRegisterInfoDTO> userRegistry(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = null;
        try {
            user = userService.registry(userRegisterDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return RestResult.fail("注册失败");
        }

        UserRegisterInfoDTO userRegisterInfoDTO = UserRegisterInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
        return RestResult.success(userRegisterInfoDTO);
    }
}
