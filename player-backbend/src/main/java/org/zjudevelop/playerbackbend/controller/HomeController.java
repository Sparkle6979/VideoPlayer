package org.zjudevelop.playerbackbend.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zjudevelop.playerbackbend.domain.dto.*;
import org.zjudevelop.playerbackbend.pojo.CheckAuth;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.service.CategoryService;
import org.zjudevelop.playerbackbend.service.LikeService;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.JwtUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 21:00
 */

@Slf4j
@RestController
@RequestMapping(value = "/home")
@Api(tags = "分类页")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpServletRequest request;

//    @Autowired
//    private LikeService likeService;

    @Autowired
    private JwtProperties jwtProperties;

    @CheckAuth(check = false)
    @ApiOperation("获取所有分类信息")
    @RequestMapping(value = "/category/detail",method = RequestMethod.GET)
        public RestResult<List<CategoryInfoDTO>> getCategoryInfoList(){
        List<CategoryInfoDTO> categoryInfoList = categoryService.getCategoryInfoList();
        return RestResult.success(categoryInfoList);
    }

    @CheckAuth(check = false)
    @ApiOperation("根据categoryId获取视频信息")
    @RequestMapping(value = "/videolist", method = RequestMethod.GET)
    public RestResult<List<VideoDisplayDTO>> getVideoInfoListByCategoryId(@RequestParam Long categoryId){
        List<VideoInfoDTO> videoInfoList = videoService.getVideoInfoListByCategoryId(categoryId);

        Long currentUserId = getUserIdFromRequest(request, jwtProperties);
        List<VideoDisplayDTO> result = new ArrayList<>();
        for (VideoInfoDTO videoInfoDTO : videoInfoList) {
            VideoDisplayDTO build = VideoDisplayDTO.builder()
                    .videoId(videoInfoDTO.getVideoId())
                    .title(videoInfoDTO.getTitle())
                    .description(videoInfoDTO.getDescription())
                    .categoryId(videoInfoDTO.getCategoryId())
                    .categoryName(videoInfoDTO.getCategoryName())
                    .createTime(videoInfoDTO.getCreateTime())
                    .likeCount(videoInfoDTO.getLikeCount())
                    .videoUrl(videoInfoDTO.getVideoUrl())
                    .coverUrl(videoInfoDTO.getCoverUrl())
                    .build();
//            if(null != currentUserId && likeService.IfLikes(currentUserId,videoInfoDTO.getVideoId())){
            if(null != currentUserId && userService.isAlreadyLiked(currentUserId,videoInfoDTO.getVideoId())){
                build.setIsLike(Boolean.TRUE);
            }else {
                build.setIsLike(Boolean.FALSE);
            }
            result.add(build);
        }
        return RestResult.success(result);
    }

    @ApiOperation("根据videoId获取视频信息")
    @RequestMapping(value = "/videoinfo", method = RequestMethod.GET)
    @CheckAuth(check = false)
    public RestResult<VideoDetailInfoDTO> getVideoInfoByVideoId(@RequestParam Long videoId){
        VideoInfoDTO videoInfo = videoService.getVideoInfoById(videoId);
//        UserInfoDTO createrInfoById = videoService.getCreaterInfoById(videoId);
        UserInfoDTO createrInfoById = userService.getCreaterInfoById(videoId);

        Long currentUserId = getUserIdFromRequest(request, jwtProperties);


        // 取前3个作为代表作
        List<VideoInfoDTO> ownVideosById = videoService.getVideosByCreatorId(createrInfoById.getId())
                .stream()
                .filter(videoInfoDTO -> videoInfoDTO.getVideoId() != videoId)
                .sorted(new Comparator<VideoInfoDTO>() {
                    @Override
                    public int compare(VideoInfoDTO o1, VideoInfoDTO o2) {
                        return (int)(o2.getLikeCount() - o1.getLikeCount());
                    }
                })
                .limit(3)
                .collect(Collectors.toList());


        VideoDetailInfoDTO build = VideoDetailInfoDTO.builder()
                .videoId(videoId)
                .title(videoInfo.getTitle())
                .description(videoInfo.getDescription())
                .categoryId(videoInfo.getCategoryId())
                .categoryName(videoInfo.getCategoryName())
                .createTime(videoInfo.getCreateTime())
                .likeCount(videoInfo.getLikeCount())
                .videoUrl(videoInfo.getVideoUrl())
                .coverUrl(videoInfo.getCoverUrl())
                .createrId(createrInfoById.getId())
                .createrWorks(ownVideosById)
//                .isLike(Boolean.TRUE ? likeService.IfLikes(currentUserId,videoId) : Boolean.FALSE)
                .isLike(userService.isAlreadyLiked(currentUserId,videoId) ? Boolean.TRUE : Boolean.FALSE)
                .build();

        return RestResult.success(build);
    }
    @CheckAuth(check = false)
    @ApiOperation("根据categoryId获取分类信息")
    @RequestMapping(value = "/categoryinfo", method = RequestMethod.GET)
    public RestResult<CategoryInfoDTO> getCategoryInfoByCategoryId(@RequestParam Long categoryId){
        CategoryInfoDTO categoryInfo = categoryService.getCategoryInfoById(categoryId);
        return RestResult.success(categoryInfo);
    }


    public static Long getUserIdFromRequest(HttpServletRequest request, JwtProperties jwtProperties){
        String token  = request.getHeader(jwtProperties.getUserTokenName());

        Long currentUserId = null;

        if(StringUtils.isNotBlank(token) && !("null".equals(token))){
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            currentUserId = Long.valueOf(claims.get("user").toString());
        }
        return currentUserId;
    }
}
