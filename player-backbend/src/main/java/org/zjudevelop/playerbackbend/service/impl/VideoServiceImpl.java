package org.zjudevelop.playerbackbend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CategoryMapper;
import org.zjudevelop.playerbackbend.dao.VideoMapper;
import org.zjudevelop.playerbackbend.domain.CategoryPO;
import org.zjudevelop.playerbackbend.domain.VideoPO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.service.VideoService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;

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
}
