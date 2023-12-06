package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.FollowsMapper;
import org.zjudevelop.playerbackbend.domain.po.Follows;
import org.zjudevelop.playerbackbend.service.FollowService;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:28
 */
@Service
@Slf4j
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowsMapper followsMapper;
    @Override
    public Follows getFollowByFollowerIdAndFollowingId(Long FollowerId, Long FollowingId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", FollowerId);
        wrapper.eq("following_id", FollowingId);

        return followsMapper.selectOne(wrapper);
    }

    @Override
    public Follows getFollowById(Long followId) {
        return followsMapper.selectById(followId);
    }
}
