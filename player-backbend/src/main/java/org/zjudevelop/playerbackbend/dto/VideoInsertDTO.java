package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/2 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoInsertDTO {
    /**
     * 视频名称
     */
    private String title;

    /**
     * 视频所属范围id
     */
    private Long categoryId;

    /**
     * 视频简介
     */
    private String description;

    /**
     * 视频云服务器对应url
     */
    private String videoUrl;

    /**
     * 视频封面对应url
     */
    private String coverUrl;
}
