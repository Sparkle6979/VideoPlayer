package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.dto.*;
import org.zjudevelop.playerbackbend.domain.po.VideoPO;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:27
 */
public interface VideoService extends IService<VideoPO> {
    VideoInfoDTO getVideoInfoById(Long videoId);
    List<VideoInfoDTO> getVideoInfoListByCategoryId(Long categoryId);
    Long addVideoInfo(VideoInsertDTO videoInsertDTO);

    List<VideoSearchInfoDTO> getVideoInfoByKeyword(String keyword);

//    UserInfoDTO getCreaterInfoById(Long videoId);

    PageResult getVideos(VideosPageQueryDTO videosPageQueryDTO);

//    List<VideoCommentDTO> getCommentByVideoId(Long videoId);

//    PageResult getCommentByVideoId(VideoCommentsPageQueryDTO videoCommentsPageQueryDTO);

    List<VideoInfoDTO> getVideosByCreatorId(Long userId);
}
