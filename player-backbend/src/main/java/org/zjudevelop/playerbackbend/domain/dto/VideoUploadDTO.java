package org.zjudevelop.playerbackbend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    MultipartFile videoFile;
    String title;
    String description;
    Long categoryId;
    MultipartFile coverFile;
}
