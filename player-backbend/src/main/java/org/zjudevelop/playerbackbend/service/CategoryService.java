package org.zjudevelop.playerbackbend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zjudevelop.playerbackbend.domain.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.domain.po.CategoryPO;

import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:40
 */
public interface CategoryService extends IService<CategoryPO> {
    CategoryInfoDTO getCategoryInfoById(Long categoryId);

    List<CategoryInfoDTO> getCategoryInfoList();
}
