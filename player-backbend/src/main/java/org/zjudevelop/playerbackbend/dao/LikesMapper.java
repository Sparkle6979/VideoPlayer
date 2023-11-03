package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zjudevelop.playerbackbend.domain.Likes;

@Mapper
public interface LikesMapper extends BaseMapper<Likes> {
}
