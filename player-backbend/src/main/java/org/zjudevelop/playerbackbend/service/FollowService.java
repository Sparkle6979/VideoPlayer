package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.Follows;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:26
 */
public interface FollowService {
    Follows getFollowByFollowerIdAndFollowingId(Long FollowerId,Long FollowingId);

    Follows getFollowById(Long followId);
}
