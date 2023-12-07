package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.dto.VideoCommentsPageQueryDTO;
import org.zjudevelop.playerbackbend.domain.po.CommentPO;
import org.zjudevelop.playerbackbend.domain.dto.VideoCommentDTO;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:12
 */
public interface CommentService extends IService<CommentPO> {
    Long comment(CommentPO commentPO);

    CommentPO getCommentById(Long commentId);

    PageResult getCommentByVideoId(VideoCommentsPageQueryDTO videoCommentsPageQueryDTO);

}
