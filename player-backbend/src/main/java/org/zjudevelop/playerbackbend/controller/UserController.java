package org.zjudevelop.playerbackbend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.domain.Follows;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.domain.User;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.utils.JwtUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.io.File;
import java.io.IOException;
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

    @Value("${tmp_file_path}")
    String tmpFilePath;

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
    @PostMapping
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
    public RestResult updateUserInfo(@ModelAttribute UserInfoUpdateDTO userInfoUpdateDTO) {
        Long userId = BaseContext.getCurrentUserId();
        log.info("userInfoUpdateInfo" + userInfoUpdateDTO);
        // save file in local path
        MultipartFile file = userInfoUpdateDTO.getFile();
        String filePath = tmpFilePath + "/" + file.getOriginalFilename();
        File localFile = new File(filePath);
        try {
            file.transferTo(localFile);
            System.out.println("save successfully");
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return RestResult.success();
    }

    /**
     * 关注
     * */
    @PostMapping("/follows/{followingId}")
    @ApiOperation("关注用户")
    public RestResult follow(@PathVariable Long followingId) {
        Follows follows = new Follows().builder()
                .followerId(BaseContext.getCurrentUserId())
                .followingId(followingId)
                .build();
        userService.follow(follows);
        return RestResult.success();
    }


    /**
     * 取消关注
     * @Param [followingId]
     * @return org.zjudevelop.playerbackbend.utils.RestResult
     * */
    @DeleteMapping("/follows/{followingId}")
    public RestResult unfollow(@PathVariable Long followingId) {
        Follows follows = new Follows().builder()
                .followerId(BaseContext.getCurrentUserId())
                .followingId(followingId)
                .build();
        int returnValue = userService.unfollow(follows);
        if (returnValue <= 0) {
            return RestResult.fail("取关失败");
        }
        return RestResult.success("取关成功");
    }

    /**
     * 查询粉丝列表
     * */
    @GetMapping("/follows/follower/{id}")
    @ApiOperation("查询粉丝列表")
    public RestResult<FollowersDTO> getFollowers(@ApiParam("查询用户id") @PathVariable Long id) {
        List<Follows> followsList = userService.getFollowers(id);
        List<Long> follwersList = followsList.stream().map(Follows::getFollowerId).collect(Collectors.toList());
        FollowersDTO followersDTO = new FollowersDTO().builder()
                .id(id)
                .followerIds(follwersList)
                .build();
        return RestResult.success(followersDTO);
    }

    /**
     * 查询关注列表
     * */
    @GetMapping("/follows/following/{id}")
    @ApiOperation("查询关注列表")
    public RestResult<FollowingsDTO> getFollowings(@ApiParam("查询用户id") @PathVariable Long id) {
        List<Follows> followsList = userService.getFollowings(id);
        List<Long> follwingsList = followsList.stream().map(Follows::getFollowingId).collect(Collectors.toList());
        FollowingsDTO followingsDTO = new FollowingsDTO().builder()
                .id(id)
                .followingIds(follwingsList)
                .build();
        return RestResult.success(followingsDTO);
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
        int returnValue = userService.unlike(likes);
        if (returnValue <= 0) {
            RestResult.fail("取消点赞失败");
        }
        return RestResult.success("取消点赞成功");
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
                .userId(userId)
                .videoIds(videoIds)
                .build();
        return RestResult.success(likeVideosDTO);
    }
}
