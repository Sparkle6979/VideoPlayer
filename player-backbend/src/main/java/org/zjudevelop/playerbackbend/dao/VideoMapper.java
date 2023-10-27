package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zjudevelop.playerbackbend.domain.VideoPO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 19:48
 */
@Mapper
public interface VideoMapper extends BaseMapper<VideoPO> {

    @Select(" select * from video "
            + "where category_id = #{categoryId} order by id")
    List<VideoPO> selectByCategoryId(@Param("categoryId") Long categoryId);
}
