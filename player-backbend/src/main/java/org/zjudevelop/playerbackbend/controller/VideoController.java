package org.zjudevelop.playerbackbend.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.domain.Creates;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.CheckAuth;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.LikeService;
import org.zjudevelop.playerbackbend.service.UploadService;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.JwtUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;
import org.zjudevelop.playerbackbend.utils.RestResult;
import org.zjudevelop.playerbackbend.utils.FileProcessUtil;
import org.zjudevelop.playerbackbend.utils.VideoProcessUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/2 14:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/video")
@Api(tags = "视频")
public class VideoController {
    @Autowired
    private QNDataServer qnDataServer;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("视频上传")
    @RequestMapping(value = "/upload",method = RequestMethod.PUT)
    public RestResult<VideoInfoDTO> uploadVideo(@ModelAttribute VideoUploadDTO videoUploadDTO){

        try {
            UploadFileInfoDTO videoServerFile = uploadService.uploadfile(null,
                    videoUploadDTO.getVideoFile().getBytes(),qnDataServer);

            UploadFileInfoDTO coverServerFile = uploadService.uploadfile(null,
                    videoUploadDTO.getCoverFile().getBytes(), qnDataServer);
//            if(null == videoUploadDTO.getCoverFile()){
//                byte[] coverFromVideoPath = VideoProcessUtil.getCoverFromVideoPath(videoServerFile.getServerFileUrl(),1146,717);
//                coverServerFile = uploadService.uploadfile(FileProcessUtil.getFileOriginName(videoServerFile.getServerFileUrl()) + ".jpg",
//                        coverFromVideoPath, qnDataServer);
//            }else{
//                coverServerFile = uploadService.uploadfile(videoUploadDTO.getCoverFile().getOriginalFilename(),
//                        videoUploadDTO.getCoverFile().getBytes(), qnDataServer);
//            }


            String videoTitle = StringUtils.isBlank(videoUploadDTO.getTitle()) ?
                    FileProcessUtil.getFileOriginName(videoServerFile.getServerFileUrl()) :
                    videoUploadDTO.getTitle();

            VideoInsertDTO videoInsertDTO = VideoInsertDTO.builder()
                    .title(videoTitle)
                    .categoryId(videoUploadDTO.getCategoryId())
                    .description(videoUploadDTO.getDescription())
                    .videoUrl(videoServerFile.getServerFileUrl())
                    .coverUrl(coverServerFile.getServerFileUrl())
                    .build();


            Long videoId = videoService.addVideoInfo(videoInsertDTO);

            Long currentUserId = BaseContext.getCurrentUserId();
            Creates creates = Creates.builder()
                    .userId(currentUserId)
                    .videoId(videoId)
                    .build();
            userService.create(creates);

            VideoInfoDTO videoInfoById = videoService.getVideoInfoById(videoId);
            return RestResult.success(videoInfoById);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ApiOperation("视频搜索")
    @CheckAuth(check = false)
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public RestResult<List<VideoSearchInfoDTO>> getVideoSearchInfoByKeyword(@RequestParam String keyword) {
        List<VideoSearchInfoDTO> videoInfoByKeyword = videoService.getVideoInfoByKeyword(keyword);

        Long userIdFromRequest = getUserIdFromRequest(request, jwtProperties);
        Long currentUserId = getUserIdFromRequest(request, jwtProperties);

        for (VideoSearchInfoDTO videoSearchInfoDTO : videoInfoByKeyword) {
            if(null != currentUserId && likeService.IfLikes(currentUserId,videoSearchInfoDTO.getVideoId())){
                videoSearchInfoDTO.setIsLike(Boolean.TRUE);
            }else {
                videoSearchInfoDTO.setIsLike(Boolean.FALSE);
            }
        }
        return RestResult.success(videoInfoByKeyword);
    }


    /**
     * 分页获取视频
     * @param videosPageQueryDTO
     * @return org.zjudevelop.playerbackbend.utils.RestResult<org.zjudevelop.playerbackbend.utils.PageResult>
     * */
    @GetMapping
    @ApiOperation("分页获取视频")
    @CheckAuth(check = false)
    public RestResult<PageResult> getVideos(VideosPageQueryDTO videosPageQueryDTO) {
        // TODO: 打乱后，再分页取
        PageResult videoList = videoService.getVideos(videosPageQueryDTO);
        return RestResult.success(videoList);
    }
//    @ApiOperation("查看视频评论")
//    @CheckAuth(check = false)
//    @RequestMapping(value = "/comment",method = RequestMethod.GET)
//    public RestResult<List<VideoCommentDTO>> getVideoSearchInfoByKeyword(@RequestParam Long videoId) {
//        return RestResult.success(videoService.getCommentByVideoId(videoId));
//    }

    @ApiOperation("分页查看视频评论")
    @CheckAuth(check = false)
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public RestResult<PageResult> getVideoCommentsInfo(@RequestBody VideoCommentsPageQueryDTO videoCommentsPageQueryDTO) {
        return RestResult.success(videoService.getCommentByVideoId(videoCommentsPageQueryDTO));
    }

    public static Long getUserIdFromRequest(HttpServletRequest request, JwtProperties jwtProperties){
        String token  = request.getHeader(jwtProperties.getUserTokenName());

        Long currentUserId = null;
        if(StringUtils.isNotBlank(token)){
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            currentUserId = Long.valueOf(claims.get("user").toString());
        }
        return currentUserId;
    }


}
