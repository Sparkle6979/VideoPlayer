package org.zjudevelop.playerbackbend.utils;

import lombok.extern.slf4j.Slf4j;
import org.zjudevelop.playerbackbend.domain.CategoryPO;
import org.zjudevelop.playerbackbend.domain.VideoPO;
import org.zjudevelop.playerbackbend.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:50
 */
@Slf4j
public class DTOUtil {

    /**
     * 日期格式
     */
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static VideoInfoDTO makeVideoInfoDTO(VideoPO videoPO, CategoryPO categoryPO) {
        VideoInfoDTO videoInfoDTO = new VideoInfoDTO();
        videoInfoDTO.setVideoId(videoPO.getId());
        videoInfoDTO.setTitile(videoPO.getTitle());
        videoInfoDTO.setCategoryId(videoPO.getCategoryId());
        videoInfoDTO.setDescription(videoPO.getDescription());
        videoInfoDTO.setLikeCount(videoPO.getLikeCount());
        videoInfoDTO.setUrl(videoPO.getUrl());
        videoInfoDTO.setCategoryName(categoryPO.getCategoryName());
        System.out.println(videoPO.getCreateTime());
        String datetime = dateFormat.format(videoPO.getCreateTime());
        videoInfoDTO.setCreateTime(datetime);
        return videoInfoDTO;
    }

    public static CategoryInfoDTO makeCategoryInfoDTO(CategoryPO categoryPO){
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO();
        categoryInfoDTO.setCategoryId(categoryPO.getId());
        categoryInfoDTO.setCategoryName(categoryPO.getCategoryName());
        return categoryInfoDTO;
    }
}
