package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.User;
import org.zjudevelop.playerbackbend.dto.*;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:27
 */
public interface VideoService {
    VideoInfoDTO getVideoInfoById(Long videoId);
    List<VideoInfoDTO> getVideoInfoListByCategoryId(Long categoryId);
    Long addVideoInfo(VideoInsertDTO videoInsertDTO);

    List<VideoSearchInfoDTO> getVideoInfoByKeyword(String keyword);

    UserInfoDTO getCreaterInfoById(Long videoId);

    List<VideoCommentDTO> getCommentByVideoId(Long videoId);
}
