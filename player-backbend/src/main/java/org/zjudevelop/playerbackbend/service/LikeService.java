package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.Likes;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 20:39
 */
public interface LikeService {
    Boolean IfLikes(Long userId,Long videoId);
}
