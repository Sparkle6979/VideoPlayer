package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.po.Likes;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 20:39
 */
public interface LikeService extends IService<Likes> {
    Boolean IfLikes(Long userId,Long videoId);

    Likes getLikesByUserIdAndVideoId(Long userId,Long videoId);

    Likes getLikesById(Long likeId);
}
