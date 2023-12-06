package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.po.CommentPO;
import org.zjudevelop.playerbackbend.domain.dto.VideoCommentDTO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:12
 */
public interface CommentService extends IService<CommentPO> {
    List<VideoCommentDTO> getCommentByVideoId(Long videoId);

    CommentPO getCommentById(Long commentId);
}
