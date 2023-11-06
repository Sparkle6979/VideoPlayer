package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.CommentPO;
import org.zjudevelop.playerbackbend.dto.VideoCommentDTO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:12
 */
public interface CommentService {
    List<VideoCommentDTO> getCommentByVideoId(Long videoId);

    CommentPO getCommentById(Long commentId);
}
