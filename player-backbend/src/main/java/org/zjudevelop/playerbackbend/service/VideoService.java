package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoInsertDTO;
import org.zjudevelop.playerbackbend.dto.VideoSearchInfoDTO;

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
}
