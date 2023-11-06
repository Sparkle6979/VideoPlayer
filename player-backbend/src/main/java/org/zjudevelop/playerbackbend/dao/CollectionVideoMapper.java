package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zjudevelop.playerbackbend.domain.CollectionVideo;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;

import java.sql.Wrapper;
import java.util.List;

@Mapper
public interface CollectionVideoMapper extends BaseMapper<CollectionVideo> {
    String innerSelectSql = "select v.* from collection_video cv " +
            "inner join video v " +
            "on v.id = cv.video_id " +
            "where cv.collection_id = ${collectionId}";

    @Select(innerSelectSql)
    Page<VideoInfoDTO> selectVideosByCollectionId(Page page, Long collectionId);
}
