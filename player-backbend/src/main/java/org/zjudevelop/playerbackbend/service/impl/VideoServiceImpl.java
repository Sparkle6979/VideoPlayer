package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.VideoMapper;

import org.zjudevelop.playerbackbend.domain.dto.*;
import org.zjudevelop.playerbackbend.domain.po.CommentPO;
import org.zjudevelop.playerbackbend.domain.po.Creates;
import org.zjudevelop.playerbackbend.domain.po.User;
import org.zjudevelop.playerbackbend.domain.po.VideoPO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.*;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;
import org.zjudevelop.playerbackbend.utils.RedisKeyUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:46
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper,VideoPO> implements VideoService {

    @Autowired
    VideoMapper videoMapper;

//    @Autowired
//    private CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

//
//    @Autowired
//    private CreatesMapper createsMapper;

    @Autowired
    CreateService createService;
//
//    @Autowired
//    private UserMapper userMapper;

//    @Autowired
//    UserService userService;

//    @Autowired
//    private CommentMapper commentMapper;

//    @Autowired
//    CommentService commentService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public VideoInfoDTO getVideoInfoById(Long videoId) {

        VideoInfoDTO videoCache = getVideoCache(videoId);
        return videoCache != null ? videoCache : initVideoCache(videoId);
    }

    @Override
    public List<VideoInfoDTO> getVideoInfoListByCategoryId(Long categoryId) {
        List<VideoPO> videoPOS = videoMapper.selectByCategoryId(categoryId);
//        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryInfoById(categoryId);

        List<VideoInfoDTO> result = new ArrayList<>();
        for (VideoPO videoPO : videoPOS) {
            VideoInfoDTO videoInfoDTO = getVideoInfoById(videoPO.getId());
//            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO, categoryInfoDTO.getCategoryName());
            result.add(videoInfoDTO);
        }
        return result;
    }

    @Override
    public Long addVideoInfo(VideoInsertDTO videoDTO) {
        VideoPO videoInsertPO = VideoPO.builder()
                .videoUrl(videoDTO.getVideoUrl())
                .title(videoDTO.getTitle())
                .categoryId(videoDTO.getCategoryId())
                .description(videoDTO.getDescription())
                .coverUrl(videoDTO.getCoverUrl()).build();

        int insertRows = videoMapper.insert(videoInsertPO);

        return videoInsertPO.getId();
    }

    @Override
    public List<VideoSearchInfoDTO> getVideoInfoByKeyword(String keyword) {
        List<VideoPO> videoPOS = videoMapper.selectList(null);
        List<VideoSearchInfoDTO> videoSearchInfoDTOS = new ArrayList<>();

        for (VideoPO videoPO : videoPOS) {
            CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryInfoById(videoPO.getCategoryId());

            VideoSearchInfoDTO videoSearchInfoDTO = DTOUtil.makeVideoSearchInfoDTO(keyword, videoPO, categoryInfoDTO.getCategoryName());


            videoSearchInfoDTO.setFindTitle( (StringUtils.isNotBlank(videoPO.getTitle()) && videoPO.getTitle().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE) );


            videoSearchInfoDTO.setFindDescription( (StringUtils.isNotBlank(videoPO.getDescription()) && videoPO.getDescription().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            videoSearchInfoDTO.setFindCategoryName( (categoryInfoDTO.getCategoryName().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            if(videoSearchInfoDTO.getFindTitle()  ||
                    videoSearchInfoDTO.getFindDescription() ||
                    videoSearchInfoDTO.getFindCategoryName()){
                videoSearchInfoDTOS.add(videoSearchInfoDTO);
            }
        }
        return videoSearchInfoDTOS;
    }

    @Override
    public PageResult getVideos(VideosPageQueryDTO videosPageQueryDTO) {
        Page<VideoPO> page = new Page<>(videosPageQueryDTO.getPage(), videosPageQueryDTO.getPageSize());
        Page<VideoPO> pageResult = videoMapper.selectPage(page, null);
        return new PageResult(pageResult.getTotal(), pageResult.getRecords());
    }


    @Override
    public List<VideoInfoDTO> getVideosByCreatorId(Long userId) {
        QueryWrapper<Creates> createsWrapper = new QueryWrapper<>();
        createsWrapper.eq("user_id", userId);

//        List<Creates> createsList = createsMapper.selectList(createsWrapper);
        List<Creates> createsList = createService.list(createsWrapper);

        List<VideoInfoDTO> result = new ArrayList<>();
        for (Creates creates : createsList) {
//            VideoPO videoPO = videoMapper.selectById(creates.getVideoId());
//            VideoPO videoPO = videoService.getById(creates.getVideoId());
//            VideoPO videoPO = videoMapper.selectById(creates.getVideoId());
//
//            String categoryName = categoryService.getById(videoPO.getCategoryId()).getCategoryName();
////            CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());
//
//
//            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO,categoryName);

            VideoInfoDTO videoInfoDTO = getVideoInfoById(creates.getVideoId());
            result.add(videoInfoDTO);
        }
        return result;
    }

    // 当缓存中没有相关信息时，进行存储
    private VideoInfoDTO initVideoCache(Long videoId){
        VideoPO videoPO = videoMapper.selectById(videoId);
        CategoryInfoDTO categoryInfoDTO = categoryService.getCategoryInfoById(videoPO.getCategoryId());
        VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO, categoryInfoDTO.getCategoryName());


        redisTemplate.opsForValue().set(RedisKeyUtil.getVideoKey(videoId),videoInfoDTO,3000, TimeUnit.SECONDS);
        return videoInfoDTO;
    }
    // 向缓存中存储对应数据
    private VideoInfoDTO getVideoCache(Long videoId){
        String videoKey = RedisKeyUtil.getVideoKey(videoId);

        return (VideoInfoDTO) redisTemplate.opsForValue().get(videoKey);
    }
    // 当修改数据库数据时，删除缓存
    private void clearCache(Long videoId){
        String videoKey = RedisKeyUtil.getVideoKey(videoId);
        redisTemplate.delete(videoKey);
    }
}
