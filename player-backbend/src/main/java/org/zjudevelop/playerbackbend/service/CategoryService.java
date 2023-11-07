package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.dto.CategoryInfoDTO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:40
 */
public interface CategoryService {
    CategoryInfoDTO getCategoryInfoById(Long categoryId);

    List<CategoryInfoDTO> getCategoryInfoList();
}
