package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CollectionMapper;
import org.zjudevelop.playerbackbend.dao.CollectionVideoMapper;
import org.zjudevelop.playerbackbend.domain.po.Collection;
import org.zjudevelop.playerbackbend.domain.po.CollectionVideo;
import org.zjudevelop.playerbackbend.domain.dto.PageQueryDTO;
import org.zjudevelop.playerbackbend.domain.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.domain.po.VideoPO;
import org.zjudevelop.playerbackbend.service.CollectionService;
import org.zjudevelop.playerbackbend.service.CollectionVideoService;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper,Collection> implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

//    @Autowired
//    CollectionVideoMapper collectionVideoMapper;

    @Autowired
    private CollectionVideoService collectionVideoService;

    @Autowired
    private VideoService videoService;

    /**
     * 创建收藏夹，insert
     * @param userId
     * @param collectionName
     * @return
     */
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

        return Boolean.TRUE == collectionVideoService.save(collectionVideo) ? 1 : 0;
//        return collectionVideoMapper.insert(collectionVideo);
    }

    @Override
    public int cancelCollect(Long collectionId, Long videoId) {
        QueryWrapper<CollectionVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        queryWrapper.eq("video_id", videoId);

        return Boolean.TRUE == collectionVideoService.remove(queryWrapper) ? 1 : 0;
//        return collectionVideoMapper.delete(queryWrapper);
    }

    @Override
    public PageResult getCollectionVideoByCollectionId(Long collectionId, PageQueryDTO pageQueryDTO) {
        Page<CollectionVideo> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        QueryWrapper<CollectionVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);

        Page<CollectionVideo> pageResult = collectionVideoService.page(page, queryWrapper);

        List<VideoInfoDTO> result = pageResult.getRecords()
                .stream()
                .map(collectionVideo -> collectionVideo.getVideoId())
                .map(videoId -> videoService.getVideoInfoById(videoId))
                .collect(Collectors.toList());

//        Page<VideoInfoDTO> pageResult = collectionVideoMapper.selectVideosByCollectionId(page, collectionId);
        return new PageResult(result.size(), result);
    }

    @Override
    public int removeCollection(Long collectionId) {
        return collectionMapper.deleteById(collectionId);
    }
}
