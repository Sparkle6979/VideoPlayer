package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zjudevelop.playerbackbend.domain.po.MessagePO;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 10:57
 */
@Mapper
public interface MessageMapper extends BaseMapper<MessagePO> {
}
