package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CommentMapper;
import org.zjudevelop.playerbackbend.dao.UserMapper;
import org.zjudevelop.playerbackbend.domain.po.CommentPO;
import org.zjudevelop.playerbackbend.domain.dto.VideoCommentDTO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:13
 */
@Service
@Slf4j
public class CommentServiceImpl extends MessageConstant implements CommentService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<VideoCommentDTO> getCommentByVideoId(Long videoId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("entity_type", COMMENT_TYPE_VIDEO);
        wrapper.eq("entity_id", videoId);
        List<CommentPO> comments = commentMapper.selectList(wrapper);
        List<VideoCommentDTO> result = new ArrayList<>();

        for (CommentPO comment : comments) {
            VideoCommentDTO build = VideoCommentDTO.builder()
                    .commentId(comment.getId())
                    .commentUserId(comment.getUserId())
                    .commentUserName(userMapper.selectById(comment.getUserId()).getUsername())
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

        return  result;
    }

    @Override
    public CommentPO getCommentById(Long commentId) {
        return commentMapper.selectById(commentId);
    }
}
