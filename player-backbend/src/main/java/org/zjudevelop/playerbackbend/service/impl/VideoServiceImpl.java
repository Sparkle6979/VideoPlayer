package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.*;
import org.zjudevelop.playerbackbend.domain.*;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:46
 */
@Service
@Slf4j
public class VideoServiceImpl extends MessageConstant implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CreatesMapper createsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public VideoInfoDTO getVideoInfoById(Long videoId) {
        VideoPO videoPO = videoMapper.selectById(videoId);
        CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());

        return DTOUtil.makeVideoInfoDTO(videoPO,categoryPO);
    }

    @Override
    public List<VideoInfoDTO> getVideoInfoListByCategoryId(Long categoryId) {
        List<VideoPO> videoPOS = videoMapper.selectByCategoryId(categoryId);
        CategoryPO categoryPO = categoryMapper.selectById(categoryId);

        List<VideoInfoDTO> result = new ArrayList<>();
        for (VideoPO videoPO : videoPOS) {
            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO, categoryPO);
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
            CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());
            VideoSearchInfoDTO videoSearchInfoDTO = DTOUtil.makeVideoSearchInfoDTO(keyword, videoPO, categoryPO);

            videoSearchInfoDTO.setFindTitle( (videoPO.getTitle().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE) );

            videoSearchInfoDTO.setFindDescription( (videoPO.getDescription().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            videoSearchInfoDTO.setFindCategoryName( (categoryPO.getCategoryName().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            if(videoSearchInfoDTO.getFindTitle()  ||
                    videoSearchInfoDTO.getFindDescription() ||
                    videoSearchInfoDTO.getFindCategoryName()){
                videoSearchInfoDTOS.add(videoSearchInfoDTO);
            }
        }
        return videoSearchInfoDTOS;
    }

    @Override
    public UserInfoDTO getCreaterInfoById(Long videoId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);

        Creates creates = createsMapper.selectOne(wrapper);

        User user = userMapper.selectById(creates.getUserId());

        return DTOUtil.makeUserInfoDTO(user);
    }

    @Override
    public PageResult getVideos(VideosPageQueryDTO videosPageQueryDTO) {
        Page<VideoPO> page = new Page<>(videosPageQueryDTO.getPage(), videosPageQueryDTO.getPageSize());
        Page<VideoPO> pageResult = videoMapper.selectPage(page, null);
        return new PageResult(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public List<VideoCommentDTO> getCommentByVideoId(Long videoId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("entity_type", COMMENT_TYPE_VIDEO);
        wrapper.eq("entity_id", videoId);
        List<CommentPO> comments = commentMapper.selectList(wrapper);
        List<VideoCommentDTO> result = makeVideoCommentDTO(comments);

        return  result;
    }

    @Override
    public PageResult getCommentByVideoId(VideoCommentsPageQueryDTO videoCommentsPageQueryDTO) {
        Page<CommentPO> page = new Page<>(videoCommentsPageQueryDTO.getPage(), videoCommentsPageQueryDTO.getPageSize());
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("entity_type", COMMENT_TYPE_VIDEO);
        wrapper.eq("entity_id", videoCommentsPageQueryDTO.getVideoId());
        Page<CommentPO> pageResult = commentMapper.selectPage(page, wrapper);

        List<VideoCommentDTO> result = makeVideoCommentDTO(pageResult.getRecords());
        return new PageResult(result.size(),result);
    }


    public List<VideoCommentDTO> makeVideoCommentDTO(List<CommentPO> comments){
        List<VideoCommentDTO> result = new ArrayList<>();
        for (CommentPO comment : comments) {
            VideoCommentDTO build = VideoCommentDTO.builder()
                    .commentId(comment.getId())
                    .commentUserId(comment.getUserId())
                    .commentUserName(userMapper.selectById(comment.getUserId()).getUsername())
                    .commentUserAvatarPath(userMapper.selectById(comment.getUserId()).getAvatarPath())
                    .content(comment.getContent())
                    .createTime(comment.getCreateTime().toString())
                    .build();

            Long commentId = comment.getId();
            QueryWrapper commentwrapper = new QueryWrapper<>();
            commentwrapper.eq("entity_type", COMMENT_TYPE_COMMENT);
            commentwrapper.eq("entity_id", commentId);
            List<CommentPO> commentPOS = commentMapper.selectList(commentwrapper);

            List<VideoCommentDTO> commentReply = new ArrayList<>();
            for (CommentPO commentPO : commentPOS) {
                VideoCommentDTO commentbuild = VideoCommentDTO.builder()
                        .commentId(comment.getId())
                        .commentUserId(commentPO.getUserId())
                        .commentUserName(userMapper.selectById(commentPO.getUserId()).getUsername())
                        .commentUserAvatarPath(userMapper.selectById(commentPO.getUserId()).getAvatarPath())
                        .content(commentPO.getContent())
                        .createTime(commentPO.getCreateTime().toString())
                        .targetUserId(commentPO.getTargetId())
                        .targetUserName(userMapper.selectById(commentPO.getTargetId()).getUsername())
                        .build();

                commentReply.add(commentbuild);
            }
            build.setCommentReply(commentReply);

            result.add(build);
        }
        return result;
    }
}
