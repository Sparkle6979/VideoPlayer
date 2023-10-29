package org.zjudevelop.playerbackbend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.pojo.User;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.utils.JwtUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
@Slf4j
@Api(tags = "用户")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtProperties jwtProperties;

    /**
     * 用户登录
     * */
    @ApiOperation("用户登录")
    @PostMapping("login")
    public RestResult<UserLoginInfoDTO> login(@RequestBody UserLoginDTO userLoginDTO){
        User user = null;
        try {
            user = userService.login(userLoginDTO);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return RestResult.fail(ex.getMessage());
        }

        // generate token
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginInfoDTO userLoginInfoDTO = UserLoginInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

        return RestResult.success(userLoginInfoDTO);
    }

    /**
     * 用户注册
     * */
    @ApiOperation("用户注册")
    @PostMapping("register")
    public RestResult<UserRegisterInfoDTO> userRegistry(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = null;
        try {
            user = userService.registry(userRegisterDTO);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return RestResult.fail("注册失败");
        }

        UserRegisterInfoDTO userRegisterInfoDTO = UserRegisterInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
        return RestResult.success(userRegisterInfoDTO);
    }

    /**
     * 查询用户信息
     * */
    @ApiOperation("查询用户信息")
    @GetMapping("/{id}")
    public RestResult<UserInfoDTO> getUserInfoById(@ApiParam(value = "用户id", required = true,
            example = "1") @PathVariable Long id) {
        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return RestResult.fail(ex.getMessage());
        }
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
        return RestResult.success(userInfoDTO);
    }
}
