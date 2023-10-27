package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    Long categoryId;
    String categoryName;
    Timestamp createTime;
    String url;
}
