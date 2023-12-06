package org.zjudevelop.playerbackbend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.CategoryMapper;
import org.zjudevelop.playerbackbend.domain.po.CategoryPO;
import org.zjudevelop.playerbackbend.domain.dto.CategoryInfoDTO;
import org.zjudevelop.playerbackbend.service.CategoryService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:43
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryInfoDTO getCategoryInfoById(Long categoryId) {
        CategoryPO categoryPO = categoryMapper.selectById(categoryId);
        return new CategoryInfoDTO(categoryPO.getId(), categoryPO.getCategoryName());
    }

    @Override
    public List<CategoryInfoDTO> getCategoryInfoList() {
        List<CategoryPO> categoryPOS = categoryMapper.selectList(null);
        List<CategoryInfoDTO> result = new ArrayList<>();
        for (CategoryPO categoryPO : categoryPOS) {
            CategoryInfoDTO categoryInfoDTO = DTOUtil.makeCategoryInfoDTO(categoryPO);
            result.add(categoryInfoDTO);
        }
        return result;
    }
}
