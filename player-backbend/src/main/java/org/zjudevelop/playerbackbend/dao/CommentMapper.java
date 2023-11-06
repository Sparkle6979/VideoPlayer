package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zjudevelop.playerbackbend.domain.CommentPO;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/6 10:10
 */

@Mapper
public interface CommentMapper extends BaseMapper<CommentPO> {
}
