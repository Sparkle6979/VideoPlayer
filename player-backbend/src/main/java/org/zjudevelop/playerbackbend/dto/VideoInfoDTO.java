package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
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
public class VideoInfoDTO {
    Long videoId;
    String titile;
    String description;
    Long likeCount;
    Long categoryId;
    String categoryName;
    String createTime;
    String url;
}
