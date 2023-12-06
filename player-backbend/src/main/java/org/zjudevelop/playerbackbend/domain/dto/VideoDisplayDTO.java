package org.zjudevelop.playerbackbend.domain.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/4 14:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDisplayDTO {
    Long videoId;
    String title;
    String description;
    Long likeCount;
    Long categoryId;
    String categoryName;
    String createTime;
    String videoUrl;
    String coverUrl;
    Boolean isLike;
}
