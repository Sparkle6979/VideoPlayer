package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.po.Follows;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:26
 */
public interface FollowService extends IService<Follows> {
    Follows getFollowByFollowerIdAndFollowingId(Long FollowerId,Long FollowingId);

    Follows getFollowById(Long followId);
}
