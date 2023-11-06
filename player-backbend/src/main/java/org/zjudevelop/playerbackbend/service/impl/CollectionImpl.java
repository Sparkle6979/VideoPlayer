package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CollectionMapper;
import org.zjudevelop.playerbackbend.dao.CollectionVideoMapper;
import org.zjudevelop.playerbackbend.domain.Collection;
import org.zjudevelop.playerbackbend.domain.CollectionVideo;
import org.zjudevelop.playerbackbend.domain.VideoPO;
import org.zjudevelop.playerbackbend.dto.CollectionInfoDTO;
import org.zjudevelop.playerbackbend.dto.PageQueryDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.service.CollectionService;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.sql.Wrapper;
import java.util.List;

@Service
@Slf4j
public class CollectionImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    CollectionVideoMapper collectionVideoMapper;

    @Override
    public int createCollection(Long userId, String collectionName) {
        Collection collection = Collection.builder()
                .userId(userId)
                .collectionName(collectionName)
                .build();
        int result = collectionMapper.insert(collection);

        return result;
    }

    @Override
    public PageResult getCollectionsByUserID(Long userId, PageQueryDTO pageQueryDTO) {
        Page<Collection> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Page<Collection> pageResult = collectionMapper.selectPage(page, wrapper);
        return new PageResult(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public int collect(Long collectionId, Long videoId) {
        CollectionVideo collectionVideo = CollectionVideo.builder()
                .collectionId(collectionId)
                .videoId(videoId)
                .build();
        return collectionVideoMapper.insert(collectionVideo);
    }

    @Override
    public int cancelCollect(Long collectionId, Long videoId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        queryWrapper.eq("video_id", videoId);
        return collectionVideoMapper.delete(queryWrapper);
    }

    @Override
    public PageResult getCollectionVideoByCollectionId(Long collectionId, PageQueryDTO pageQueryDTO) {
        Page<CollectionVideo> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        Page<VideoInfoDTO> pageResult = collectionVideoMapper.selectVideosByCollectionId(page, collectionId);
        return new PageResult(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    public int removeCollection(Long collectionId) {
        return collectionMapper.deleteById(collectionId);
    }
}
