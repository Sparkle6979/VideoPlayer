package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zjudevelop.playerbackbend.domain.po.CategoryPO;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:10
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryPO> {
}
