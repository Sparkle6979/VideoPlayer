package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zjudevelop.playerbackbend.domain.po.CollectionVideo;
import org.zjudevelop.playerbackbend.domain.dto.VideoInfoDTO;

@Mapper
public interface CollectionVideoMapper extends BaseMapper<CollectionVideo> {
    String innerSelectSql = "select * from collection_video cv " +
            "inner join video v " +
            "on v.id = cv.video_id " +
            "where cv.collection_id = ${collectionId}";

    @Select(innerSelectSql)
    Page<VideoInfoDTO> selectVideosByCollectionId(Page page, Long collectionId);
}
