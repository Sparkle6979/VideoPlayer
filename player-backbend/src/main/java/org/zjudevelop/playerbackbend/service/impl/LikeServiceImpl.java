package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.LikesMapper;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.service.LikeService;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 20:40
 */
@Service
@Slf4j
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikesMapper likesMapper;

    @Override
    public Boolean IfLikes(Long userId,Long videoId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("video_id", videoId);

        Likes likes = likesMapper.selectOne(wrapper);
        return likes != null ? Boolean.TRUE : Boolean.FALSE;
    }
}
