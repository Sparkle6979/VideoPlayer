package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/1 16:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoUploadDTO {
    String videoUrl;
    String title;
    String description;
    Long categoryId;
    String coverUrl;
}
