package org.zjudevelop.playerbackbend.utils;

import lombok.extern.slf4j.Slf4j;
import org.zjudevelop.playerbackbend.domain.CategoryPO;
import org.zjudevelop.playerbackbend.domain.User;
import org.zjudevelop.playerbackbend.domain.VideoPO;
import org.zjudevelop.playerbackbend.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.dto.UserInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoSearchInfoDTO;

import java.text.DateFormat;
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
        String datetime = dateFormat.format(videoPO.getCreateTime());

        VideoInfoDTO videoInfoDTO = VideoInfoDTO.builder()
                .videoId(videoPO.getId())
                .title(videoPO.getTitle())
                .categoryId(videoPO.getCategoryId())
                .categoryName(categoryPO.getCategoryName())
                .description(videoPO.getDescription())
                .likeCount(videoPO.getLikeCount())
                .videoUrl(videoPO.getVideoUrl())
                .coverUrl(videoPO.getCoverUrl())
                .createTime(datetime)
                .build();

        return videoInfoDTO;
    }

    public static CategoryInfoDTO makeCategoryInfoDTO(CategoryPO categoryPO){
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO().builder()
                .categoryId(categoryPO.getId())
                .categoryName(categoryPO.getCategoryName())
                .build();
        return categoryInfoDTO;
    }

    public static VideoSearchInfoDTO makeVideoSearchInfoDTO(String keyword, VideoPO videoPO,CategoryPO categoryPO){
        VideoInfoDTO videoInfoDTO = makeVideoInfoDTO(videoPO, categoryPO);
        VideoSearchInfoDTO videoSearchInfoDTO = VideoSearchInfoDTO.builder()
                .keyword(keyword)
                .videoId(videoInfoDTO.getVideoId())
                .title(videoInfoDTO.getTitle())
                .categoryId(videoInfoDTO.getCategoryId())
                .categoryName(videoInfoDTO.getCategoryName())
                .createTime(videoInfoDTO.getCreateTime())
                .description(videoInfoDTO.getDescription())
                .likeCount(videoInfoDTO.getLikeCount())
                .coverUrl(videoInfoDTO.getCoverUrl())
                .videoUrl(videoInfoDTO.getVideoUrl())
                .build();
        return videoSearchInfoDTO;
    }

    public static UserInfoDTO makeUserInfoDTO(User user){
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarPath(user.getAvatarPath())
                .build();
        return userInfoDTO;
    }
}
