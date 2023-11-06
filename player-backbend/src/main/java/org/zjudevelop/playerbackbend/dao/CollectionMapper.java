package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zjudevelop.playerbackbend.domain.Collection;
import org.zjudevelop.playerbackbend.domain.Follows;

@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {
}
