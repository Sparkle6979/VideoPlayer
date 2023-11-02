package org.zjudevelop.playerbackbend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.domain.User;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.utils.JwtUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
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
    @PostMapping("/login")
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
    @PostMapping()
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

    /**
     * 更新用户信息
     * */
    @PutMapping()
    @ApiOperation("更新用户信息")
    public RestResult updateUserInfo(@RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
        return RestResult.success();
    }

    /**
     * 关注
     * */
    @PostMapping("/follow")
    @ApiOperation("关注用户")
    public RestResult follow(@RequestBody FollowDTO followDTO) {
        return RestResult.success();
    }

    /**
     * 查询粉丝列表
     * */
    @GetMapping("/follower/{id}")
    @ApiOperation("查询粉丝列表")
    public RestResult<FollowersDTO> getFollowers(@ApiParam("查询用户id") @PathVariable Long id) {
        return null;
    }

    /**
     * 查询关注列表
     * */
    @GetMapping("/following/{id}")
    @ApiOperation("查询关注列表")
    public RestResult<FollowingsDTO> getFollowings(@ApiParam("查询用户id") @PathVariable Long id) {
        return null;
    }

    /**
     * 点赞视频
     * */
    @PostMapping("/likes/{videoId}")
    @ApiOperation("点赞视频")
    public RestResult like(@PathVariable Long videoId) {
        Likes likes = new Likes().builder()
                .userId(BaseContext.getCurrentUserId())
                .videoId(videoId)
                .build();
        userService.like(likes);
        return RestResult.success();
    }

    /**
     * 取消点赞
     * @Param [videoId]
     * @return org.zjudevelop.playerbackbend.utils.RestResult
     * */
    @DeleteMapping("/likes/{videoId}")
    @ApiOperation("取消点赞")
    public RestResult unlike(@PathVariable Long videoId) {
        Likes likes = new Likes().builder()
                .userId(BaseContext.getCurrentUserId())
                .videoId(videoId)
                .build();
        userService.unlike(likes);
        return RestResult.success();
    }


    /**
     * 查询点赞视频
     * */
    @GetMapping("/likes")
    @ApiOperation("查询点赞视频")
    public RestResult<LikeVideosDTO> getLikeVideos() {
        Long userId = BaseContext.getCurrentUserId();
        List<Likes> likes = userService.getLikes(userId);
        List<Long> videoIds = likes.stream().map(Likes::getVideoId).collect(Collectors.toList());
        LikeVideosDTO likeVideosDTO = new LikeVideosDTO().builder()
                .id(userId)
                .videoIds(videoIds)
                .build();
        return RestResult.success(likeVideosDTO);
    }
}
