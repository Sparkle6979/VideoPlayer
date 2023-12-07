package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CommentMapper;
import org.zjudevelop.playerbackbend.dao.UserMapper;
import org.zjudevelop.playerbackbend.domain.dto.VideoCommentsPageQueryDTO;
import org.zjudevelop.playerbackbend.domain.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.domain.po.CommentPO;
import org.zjudevelop.playerbackbend.domain.dto.VideoCommentDTO;
import org.zjudevelop.playerbackbend.domain.po.Creates;
import org.zjudevelop.playerbackbend.domain.po.VideoPO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.CommentService;
import org.zjudevelop.playerbackbend.service.UserService;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:13
 */
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper,CommentPO> implements CommentService{
//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @Override
    public Long comment(CommentPO commentPO) {
         commentMapper.insert(commentPO);
         return commentPO.getId();
    }

    @Override
    public CommentPO getCommentById(Long commentId) {
        return commentMapper.selectById(commentId);
    }

    @Override
    public PageResult getCommentByVideoId(VideoCommentsPageQueryDTO videoCommentsPageQueryDTO) {
        Page<CommentPO> page = new Page<>(videoCommentsPageQueryDTO.getPage(), videoCommentsPageQueryDTO.getPageSize());
        QueryWrapper<CommentPO> wrapper = new QueryWrapper<>();
        wrapper.eq("entity_type", MessageConstant.COMMENT_TYPE_VIDEO);
        wrapper.eq("entity_id", videoCommentsPageQueryDTO.getVideoId());

//        Page<CommentPO> pageResult = commentService.page(page, wrapper);
        Page<CommentPO> pageResult = commentMapper.selectPage(page, wrapper);
//        Page<CommentPO> pageResult = commentMapper.selectPage(page, wrapper);

        List<VideoCommentDTO> result = makeVideoCommentDTO(pageResult.getRecords());
        return new PageResult(result.size(),result);
    }


    public List<VideoCommentDTO> makeVideoCommentDTO(List<CommentPO> comments){
        List<VideoCommentDTO> result = new ArrayList<>();
        for (CommentPO comment : comments) {
            VideoCommentDTO build = VideoCommentDTO.builder()
                    .commentId(comment.getId())
                    .commentUserId(comment.getUserId())
                    .commentUserName(userService.getUserById(comment.getUserId()).getUsername())
//                    .commentUserName(userMapper.selectById(comment.getUserId()).getUsername())
                    .commentUserAvatarPath(userService.getUserById(comment.getUserId()).getAvatarPath())
                    .content(comment.getContent())
                    .createTime(comment.getCreateTime().toString())
                    .build();

            Long commentId = comment.getId();

            QueryWrapper<CommentPO> commentwrapper = new QueryWrapper<>();
            commentwrapper.eq("entity_type", MessageConstant.COMMENT_TYPE_COMMENT);
            commentwrapper.eq("entity_id", commentId);
            List<CommentPO> commentPOS = commentMapper.selectList(commentwrapper);
//            List<CommentPO> commentPOS = commentMapper.selectList(commentwrapper);

            List<VideoCommentDTO> commentReply = new ArrayList<>();
            for (CommentPO commentPO : commentPOS) {
                VideoCommentDTO commentbuild = VideoCommentDTO.builder()
                        .commentId(comment.getId())
                        .commentUserId(commentPO.getUserId())
                        .commentUserName(userService.getUserById(commentPO.getUserId()).getUsername())
//                        .commentUserName(userMapper.selectById(commentPO.getUserId()).getUsername())
                        .commentUserAvatarPath(userService.getUserById(commentPO.getUserId()).getAvatarPath())
//                        .commentUserAvatarPath(userMapper.selectById(commentPO.getUserId()).getAvatarPath())
                        .content(commentPO.getContent())
                        .createTime(commentPO.getCreateTime().toString())
                        .targetUserId(commentPO.getTargetId())
                        .targetUserName(userService.getUserById(commentPO.getTargetId()).getUsername())
//                        .targetUserName(userMapper.selectById(commentPO.getTargetId()).getUsername())
                        .build();

                commentReply.add(commentbuild);
            }
            build.setCommentReply(commentReply);

            result.add(build);
        }
        return result;
    }

}
