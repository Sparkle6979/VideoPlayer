package org.zjudevelop.playerbackbend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.domain.*;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.*;
import org.zjudevelop.playerbackbend.service.*;
import org.zjudevelop.playerbackbend.utils.PageResult;
import org.zjudevelop.playerbackbend.event.EventProducer;
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
public class UserController extends MessageConstant {

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @Autowired
    UploadService uploadService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;

    @Autowired
    CommentService commentService;

    @Autowired
    QNDataServer qnDataServer;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    MessageService messageService;

    @Value("${tmp_file_path}")
    String tmpFilePath;

    @Autowired
    EventProducer eventProducer;

    /**
     * 用户登录
     * */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    @CheckAuth(check = false)
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
    @CheckAuth(check = false)
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
    @CheckAuth(check = false)
    public RestResult<UserInfoDTO> getUserInfoById(@ApiParam(value = "用户id", required = true,
            example = "1") @PathVariable Long id) {
        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return RestResult.fail(ex.getMessage());
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);

        return RestResult.success(userInfoDTO);
    }

    /**
     * 更新用户信息
     * */
    @PutMapping()
    @ApiOperation("更新用户信息")
    public RestResult updateUserInfo(@ModelAttribute UserInfoUpdateDTO userInfoUpdateDTO) {
        Long userId = BaseContext.getCurrentUserId();

        // save file in local path
        MultipartFile file = userInfoUpdateDTO.getAvatarFile();
        String fileUrl = null;
        if (file != null) {
            String filePath = tmpFilePath + "/" + file.getOriginalFilename();
            File localFile = new File(filePath);
            try {
                file.transferTo(localFile);
                log.info("文件保存成功，保存路径为： " + filePath);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return RestResult.fail("文件上传失败");
            }

            // upload file to server
            fileUrl = uploadService.uploadfile(filePath, qnDataServer).getServerFileUrl();
            log.info("文件上传成功, url为： " + fileUrl);
        }

        User user = new User();
        BeanUtils.copyProperties(userInfoUpdateDTO, user);
        user.setId(userId);
        if (fileUrl != null) {user.setAvatarPath(fileUrl);}
        int returnValue = userService.update(user);
        if (returnValue <= 0) {
            return RestResult.fail("更新失败");
        }
        return RestResult.success();
    }
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ApiOperation("发表评论")
    public RestResult comment(@RequestBody UserCommentDTO userCommentDTO){

        Long currentUserId = BaseContext.getCurrentUserId();

        CommentPO build = CommentPO.builder()
                .userId(currentUserId)
                .entityType(userCommentDTO.getEntityType())
                .entityId(userCommentDTO.getEntityId())
                .targetId(userCommentDTO.getTargetId())
                .content(userCommentDTO.getContent())
                .build();

        Long commentId = userService.comment(build);


        Event event = Event.builder()
                .topic(TOPIC_COMMENT)
                .userId(currentUserId)
                .entityId(commentId)
                .build();

        // 如果对视频进行评论，entityUserId为视频作者Id，否则为targetId
        if(COMMENT_TYPE_VIDEO.equals(userCommentDTO.getEntityType())){
            event.setEntityType(EVENT_VIDEO_COMMENT);
            event.setEntityUserId(videoService.getCreaterInfoById(userCommentDTO.getEntityId()).getId());
        }else if(COMMENT_TYPE_COMMENT.equals(userCommentDTO.getEntityType())){
            event.setEntityType(EVENT_USER_COMMENT);
            event.setEntityUserId(userCommentDTO.getTargetId());
        }

        eventProducer.fireEvent(event);


        return RestResult.success();
    }

    /**
     * 关注
     * */
    @PostMapping("/follows/{followingId}")
    @ApiOperation("关注用户")
    public RestResult follow(@PathVariable Long followingId) {
        Long currentUserId = BaseContext.getCurrentUserId();
        Follows follows = new Follows().builder()
                .followerId(currentUserId)
                .followingId(followingId)
                .build();
        userService.follow(follows);

        Event build = Event.builder()
                .topic(TOPIC_FOLLOW)
                .userId(currentUserId)
                .entityType(EVENT_USER_FOLLOW)
                .entityId(followService.getFollowByFollowerIdAndFollowingId(currentUserId,followingId).getId())
                .entityUserId(followingId)
                .build();

        eventProducer.fireEvent(build);

        return RestResult.success();
    }


    /**
     * 取消关注
     * @Param [followingId]
     * @return org.zjudevelop.playerbackbend.utils.RestResult
     * */
    @DeleteMapping("/follows/{followingId}")
    @ApiOperation("取关用户")
    public RestResult unfollow(@PathVariable Long followingId) {
        Follows follows = new Follows().builder()
                .followerId(BaseContext.getCurrentUserId())
                .followingId(followingId)
                .build();
        int returnValue = userService.unfollow(follows);
        if (returnValue <= 0) {
            return RestResult.fail("取关失败");
        }
        messageService.readMessage(EVENT_USER_FOLLOW,followingId);
        return RestResult.success("取关成功");
    }

    /**
     * 查询粉丝列表
     * */
    @GetMapping("/follows/follower")
    @ApiOperation("分页查询给定id的粉丝列表")
    @CheckAuth(check = false)
    public RestResult<PageResult> getFollowers(FollowersPageQueryDTO followersPageQueryDTO) {
        PageResult followersList = userService.getFollowers(followersPageQueryDTO);
        return RestResult.success(followersList);
    }

    /**
     * 查询关注列表
     * */
    @GetMapping("/follows/following")
    @ApiOperation("分页查询给定id的关注列表")
    @CheckAuth(check = false)
    public RestResult<PageResult> getFollowings(FollowingsPageQueryDTO followingsPageQueryDTO) {
        PageResult followingsPageList = userService.getFollowings(followingsPageQueryDTO);
        return RestResult.success(followingsPageList);
    }

    /**
     * 点赞视频
     * */
    @PostMapping("/likes/{videoId}")
    @ApiOperation("点赞视频")
    public RestResult like(@PathVariable Long videoId) {
        Long currentUserId = BaseContext.getCurrentUserId();
        Likes likes = new Likes().builder()
                .userId(currentUserId)
                .videoId(videoId)
                .build();
        userService.like(likes);

        Event build = Event.builder()
                .topic(TOPIC_LIKE)
                .userId(currentUserId)
                .entityType(EVENT_VIDEO_LIKE)
                .entityId(likeService.getLikesByUserIdAndVideoId(currentUserId, videoId).getId())
                .entityUserId(videoService.getCreaterInfoById(videoId).getId())
                .build();

        eventProducer.fireEvent(build);

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
        messageService.readMessage(EVENT_VIDEO_LIKE,videoId);

        return RestResult.success("取消点赞成功");
    }


    /**
     * 查询点赞视频
     * */
    @GetMapping("/likes")
    @ApiOperation("分页查询点赞视频")
    public RestResult<PageResult> getLikeVideos(LikesPageQueryDTO likesPageQueryDTO) {
        Long userId = BaseContext.getCurrentUserId();
        PageResult likesList = userService.getLikes(userId, likesPageQueryDTO);
        return RestResult.success(likesList);
    }

    @GetMapping("/creates")
    @ApiOperation("查询发布视频")
    public RestResult<CreateVideosDTO> getCreateVideos() {
        Long userId = BaseContext.getCurrentUserId();
        List<Creates> creates = userService.getCreates(userId);
        List<Long> videoIds = creates.stream().map(Creates::getVideoId).collect(Collectors.toList());
        CreateVideosDTO createVideosDTO = new CreateVideosDTO().builder()
                .userId(userId)
                .videoIds(videoIds)
                .build();
        return RestResult.success(createVideosDTO);
    }
}
