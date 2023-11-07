package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zjudevelop.playerbackbend.domain.VideoPO;
import org.zjudevelop.playerbackbend.dto.VideoUploadDTO;

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

    @Select(" select * from video " +
            "where id = #{id} for update")
    VideoPO selectByIdWithLock(@Param("id") Long id);

    @Insert("INSERT INTO video " +
            "(title, category_id, like_count, description, create_time, video_url, cover_url) " +
            "VALUES(#{title}, #{categoryId}, 0, #{description}, CURRENT_TIMESTAMP, #{videoUrl}, #{coverUrl})")
    Integer insertVideoPO(@Param("title") String title,
                          @Param("categoryId") Long categoryId,
                          @Param("description") String description,
                          @Param("videoUrl") String videoUrl,
                          @Param("coverUrl") String coverUrl);

}
