package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zjudevelop.playerbackbend.domain.Follows;
import org.zjudevelop.playerbackbend.domain.Likes;

@Mapper
public interface FollowsMapper extends BaseMapper<Follows> {
}
