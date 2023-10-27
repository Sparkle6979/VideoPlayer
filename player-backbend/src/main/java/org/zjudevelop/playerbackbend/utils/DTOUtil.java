package org.zjudevelop.playerbackbend.utils;

import org.zjudevelop.playerbackbend.domain.CategoryPO;
import org.zjudevelop.playerbackbend.domain.VideoPO;
import org.zjudevelop.playerbackbend.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:50
 */
public class DTOUtil {
    public static VideoInfoDTO makeVideoInfoDTO(VideoPO videoPO, CategoryPO categoryPO){
        VideoInfoDTO videoInfoDTO = new VideoInfoDTO();
        videoInfoDTO.setVideoId(videoPO.getId());
        videoInfoDTO.setTitile(videoPO.getTitle());
        videoInfoDTO.setCategoryId(videoPO.getCategoryId());
        videoInfoDTO.setCategoryName(categoryPO.getCategoryName());
        videoInfoDTO.setDescription(videoPO.getDescription());
        videoInfoDTO.setCreateTime(videoPO.getCreateTime());
        videoInfoDTO.setUrl(videoPO.getUrl());
        return videoInfoDTO;
    }

    public static CategoryInfoDTO makeCategoryInfoDTO(CategoryPO categoryPO){
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO();
        categoryInfoDTO.setCategoryId(categoryPO.getId());
        categoryInfoDTO.setCategoryName(categoryPO.getCategoryName());
        return categoryInfoDTO;
    }
}
