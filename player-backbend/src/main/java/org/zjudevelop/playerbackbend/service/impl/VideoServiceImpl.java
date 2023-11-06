package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CategoryMapper;
import org.zjudevelop.playerbackbend.dao.CreatesMapper;
import org.zjudevelop.playerbackbend.dao.UserMapper;
import org.zjudevelop.playerbackbend.dao.VideoMapper;
import org.zjudevelop.playerbackbend.domain.*;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.PageResult;


import java.util.ArrayList;
import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:46
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CreatesMapper createsMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public VideoInfoDTO getVideoInfoById(Long videoId) {
        VideoPO videoPO = videoMapper.selectById(videoId);
        CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());

        return DTOUtil.makeVideoInfoDTO(videoPO,categoryPO);
    }

    @Override
    public List<VideoInfoDTO> getVideoInfoListByCategoryId(Long categoryId) {
        List<VideoPO> videoPOS = videoMapper.selectByCategoryId(categoryId);
        CategoryPO categoryPO = categoryMapper.selectById(categoryId);

        List<VideoInfoDTO> result = new ArrayList<>();
        for (VideoPO videoPO : videoPOS) {
            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO, categoryPO);
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
            CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());
            VideoSearchInfoDTO videoSearchInfoDTO = DTOUtil.makeVideoSearchInfoDTO(keyword, videoPO, categoryPO);

            videoSearchInfoDTO.setFindTitle( (videoPO.getTitle().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE) );

            videoSearchInfoDTO.setFindDescription( (videoPO.getDescription().contains(keyword) ?
                    Boolean.TRUE : Boolean.FALSE));

            videoSearchInfoDTO.setFindCategoryName( (categoryPO.getCategoryName().contains(keyword) ?
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
    public UserInfoDTO getCreaterInfoById(Long videoId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);

        Creates creates = createsMapper.selectOne(wrapper);

        User user = userMapper.selectById(creates.getUserId());

        return DTOUtil.makeUserInfoDTO(user);
    }

    @Override
    public PageResult getVideos(VideosPageQueryDTO videosPageQueryDTO) {
        Page<VideoPO> page = new Page<>(videosPageQueryDTO.getPage(), videosPageQueryDTO.getPageSize());
        Page<VideoPO> pageResult = videoMapper.selectPage(page, null);
        return new PageResult(pageResult.getTotal(), pageResult.getRecords());
    }
}
