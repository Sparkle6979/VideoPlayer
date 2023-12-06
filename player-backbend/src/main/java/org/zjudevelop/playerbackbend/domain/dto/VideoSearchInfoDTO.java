package org.zjudevelop.playerbackbend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/3 11:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoSearchInfoDTO {
    String keyword;

    Long videoId;
    String title;
    String description;
    Long likeCount;
    Long categoryId;
    String categoryName;
    String createTime;
    String videoUrl;
    String coverUrl;

    Boolean findTitle;

    Boolean findDescription;

    Boolean findCategoryName;

    Boolean isLike;
}
