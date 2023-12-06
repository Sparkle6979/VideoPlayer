package org.zjudevelop.playerbackbend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoInfoDTO {
    Long videoId;
    String title;
    String description;
    Long likeCount;
    Long categoryId;
    String categoryName;
    String createTime;
    String videoUrl;
    String coverUrl;
}
