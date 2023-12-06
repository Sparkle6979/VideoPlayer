package org.zjudevelop.playerbackbend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 12:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoDetailInfoDTO {
    Long videoId;
    String title;
    String description;
    Long likeCount;
    Long categoryId;
    String categoryName;
    String createTime;
    String videoUrl;
    String coverUrl;
    Long createrId;
    Boolean isLike;
    List<VideoInfoDTO> createrWorks;

}
