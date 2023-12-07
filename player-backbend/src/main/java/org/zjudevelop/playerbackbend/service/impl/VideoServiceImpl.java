package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.VideoMapper;

import org.zjudevelop.playerbackbend.domain.dto.*;
import org.zjudevelop.playerbackbend.domain.po.CommentPO;
import org.zjudevelop.playerbackbend.domain.po.Creates;
import org.zjudevelop.playerbackbend.domain.po.User;
import org.zjudevelop.playerbackbend.domain.po.VideoPO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.*;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;


import java.util.ArrayList;
import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:46
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper,VideoPO> implements VideoService {

    @Autowired
    VideoMapper videoMapper;

//    @Autowired
//    private CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

//
//    @Autowired
//    private CreatesMapper createsMapper;

    @Autowired
    CreateService createService;
//
//    @Autowired
//    private UserMapper userMapper;

//    @Autowired
//    UserService userService;

//    @Autowired
//    private CommentMapper commentMapper;

//    @Autowired
//    CommentService commentService;

    @Override
    public VideoInfoDTO getVideoInfoById(Long videoId) {
        VideoPO videoPO = videoMapper.selectById(videoId);
        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryInfoById(videoPO.getCategoryId());

        return DTOUtil.makeVideoInfoDTO(videoPO,categoryInfoDTO.getCategoryName());
    }

    @Override
    public List<VideoInfoDTO> getVideoInfoListByCategoryId(Long categoryId) {
        List<VideoPO> videoPOS = videoMapper.selectByCategoryId(categoryId);
        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryInfoById(categoryId);

        List<VideoInfoDTO> result = new ArrayList<>();
        for (VideoPO videoPO : videoPOS) {
            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO, categoryInfoDTO.getCategoryName());
            result.add(videoInfoDTO);
        }
        return result;
    }

    @Override
    public Long addVideoInfo(VideoInsertDTO videoDTO) {
        VideoPO videoInsertPO = VideoPO.builder()
                .videoUrl(videoDTO.getVideoUrl())
                .title(videoDTO.getTitle())
                .categoryId(videoDTO.getCategoryId())
                .description(videoDTO.getDescription())
                .coverUrl(videoDTO.getCoverUrl()).build();

        int insertRows = videoMapper.insert(videoInsertPO);

        return videoInsertPO.getId();
    }

    @Override
    public List<VideoSearchInfoDTO> getVideoInfoByKeyword(String keyword) {
        List<VideoPO> videoPOS = videoMapper.selectList(null);
        List<VideoSearchInfoDTO> videoSearchInfoDTOS = new ArrayList<>();

        for (VideoPO videoPO : videoPOS) {
            CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryInfoById(videoPO.getCategoryId());

            VideoSearchInfoDTO videoSearchInfoDTO = DTOUtil.makeVideoSearchInfoDTO(keyword, videoPO, categoryInfoDTO.getCategoryName());

            videoSearchInfoDTO.setFindTitle( (videoPO.getTitle().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE) );

            videoSearchInfoDTO.setFindDescription( (videoPO.getDescription().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            videoSearchInfoDTO.setFindCategoryName( (categoryInfoDTO.getCategoryName().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            if(videoSearchInfoDTO.getFindTitle()  ||
                    videoSearchInfoDTO.getFindDescription() ||
                    videoSearchInfoDTO.getFindCategoryName()){
                videoSearchInfoDTOS.add(videoSearchInfoDTO);
            }
        }
        return videoSearchInfoDTOS;
    }

    /**
     * 通过videoId获取作者信息
     * @param videoId
     * @return
     */
//    @Override
//    public UserInfoDTO getCreaterInfoById(Long videoId) {
//        QueryWrapper wrapper = new QueryWrapper<>();
//        wrapper.eq("video_id", videoId);
//
//        Creates creates = createService.getOne(wrapper);
//
//        User user = userService.getUserById(creates.getUserId());
//
//        return DTOUtil.makeUserInfoDTO(user);
//    }

    @Override
    public PageResult getVideos(VideosPageQueryDTO videosPageQueryDTO) {
        Page<VideoPO> page = new Page<>(videosPageQueryDTO.getPage(), videosPageQueryDTO.getPageSize());
        Page<VideoPO> pageResult = videoMapper.selectPage(page, null);
        return new PageResult(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * 通过videoId获取该视频下评论相关信息
     * @param videoId
     * @return
     */
//    @Override
//    public List<VideoCommentDTO> getCommentByVideoId(Long videoId) {
//        QueryWrapper<CommentPO> wrapper = new QueryWrapper<>();
//        wrapper.eq("entity_type", MessageConstant.COMMENT_TYPE_VIDEO);
//        wrapper.eq("entity_id", videoId);
//        List<CommentPO> comments = commentService.list(wrapper);
////        List<CommentPO> comments = commentMapper.selectList(wrapper);
//        List<VideoCommentDTO> result = makeVideoCommentDTO(comments);
//
//        return  result;
//    }

    /**
     * 分页查询视频评论
     * @param videoCommentsPageQueryDTO
     * @return
     */
//    @Override
//    public PageResult getCommentByVideoId(VideoCommentsPageQueryDTO videoCommentsPageQueryDTO) {
//        Page<CommentPO> page = new Page<>(videoCommentsPageQueryDTO.getPage(), videoCommentsPageQueryDTO.getPageSize());
//        QueryWrapper<CommentPO> wrapper = new QueryWrapper<>();
//        wrapper.eq("entity_type", MessageConstant.COMMENT_TYPE_VIDEO);
//        wrapper.eq("entity_id", videoCommentsPageQueryDTO.getVideoId());
//
//        Page<CommentPO> pageResult = commentService.page(page, wrapper);
////        Page<CommentPO> pageResult = commentMapper.selectPage(page, wrapper);
//
//        List<VideoCommentDTO> result = makeVideoCommentDTO(pageResult.getRecords());
//        return new PageResult(result.size(),result);
//    }

    @Override
    public List<VideoInfoDTO> getVideosByCreatorId(Long userId) {
        QueryWrapper<Creates> createsWrapper = new QueryWrapper<>();
        createsWrapper.eq("user_id", userId);

//        List<Creates> createsList = createsMapper.selectList(createsWrapper);
        List<Creates> createsList = createService.list(createsWrapper);

        List<VideoInfoDTO> result = new ArrayList<>();
        for (Creates creates : createsList) {
//            VideoPO videoPO = videoMapper.selectById(creates.getVideoId());
//            VideoPO videoPO = videoService.getById(creates.getVideoId());
            VideoPO videoPO = videoMapper.selectById(creates.getVideoId());

            String categoryName = categoryService.getById(videoPO.getCategoryId()).getCategoryName();
//            CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());


            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO,categoryName);
            result.add(videoInfoDTO);
        }
        return result;
    }


//    public List<VideoCommentDTO> makeVideoCommentDTO(List<CommentPO> comments){
//        List<VideoCommentDTO> result = new ArrayList<>();
//        for (CommentPO comment : comments) {
//            VideoCommentDTO build = VideoCommentDTO.builder()
//                    .commentId(comment.getId())
//                    .commentUserId(comment.getUserId())
//                    .commentUserName(userService.getUserById(comment.getUserId()).getUsername())
////                    .commentUserName(userMapper.selectById(comment.getUserId()).getUsername())
//                    .commentUserAvatarPath(userService.getUserById(comment.getUserId()).getAvatarPath())
//                    .content(comment.getContent())
//                    .createTime(comment.getCreateTime().toString())
//                    .build();
//
//            Long commentId = comment.getId();
//
//            QueryWrapper<CommentPO> commentwrapper = new QueryWrapper<>();
//            commentwrapper.eq("entity_type", MessageConstant.COMMENT_TYPE_COMMENT);
//            commentwrapper.eq("entity_id", commentId);
//            List<CommentPO> commentPOS = commentService.list(commentwrapper);
////            List<CommentPO> commentPOS = commentMapper.selectList(commentwrapper);
//
//            List<VideoCommentDTO> commentReply = new ArrayList<>();
//            for (CommentPO commentPO : commentPOS) {
//                VideoCommentDTO commentbuild = VideoCommentDTO.builder()
//                        .commentId(comment.getId())
//                        .commentUserId(commentPO.getUserId())
//                        .commentUserName(userService.getUserById(commentPO.getUserId()).getUsername())
////                        .commentUserName(userMapper.selectById(commentPO.getUserId()).getUsername())
//                        .commentUserAvatarPath(userService.getUserById(commentPO.getUserId()).getAvatarPath())
////                        .commentUserAvatarPath(userMapper.selectById(commentPO.getUserId()).getAvatarPath())
//                        .content(commentPO.getContent())
//                        .createTime(commentPO.getCreateTime().toString())
//                        .targetUserId(commentPO.getTargetId())
//                        .targetUserName(userService.getUserById(commentPO.getTargetId()).getUsername())
////                        .targetUserName(userMapper.selectById(commentPO.getTargetId()).getUsername())
//                        .build();
//
//                commentReply.add(commentbuild);
//            }
//            build.setCommentReply(commentReply);
//
//            result.add(build);
//        }
//        return result;
//    }
}
