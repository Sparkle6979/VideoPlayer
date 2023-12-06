package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zjudevelop.playerbackbend.domain.po.Likes;

@Mapper
public interface LikesMapper extends BaseMapper<Likes> {
}
