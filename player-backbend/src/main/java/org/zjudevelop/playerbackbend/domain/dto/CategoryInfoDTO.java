package org.zjudevelop.playerbackbend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryInfoDTO {
    Long categoryId;
    String categoryName;
}
