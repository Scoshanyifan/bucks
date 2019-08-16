package com.kunbu.spring.bucks.service;

import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.dto.CategoryDTO;
import com.kunbu.spring.bucks.common.entity.CategoryEntity;

import java.util.Map;

/**
 * @author: KunBu
 * @time: 2019/8/16 16:23
 * @description:
 */
public interface CategoryManageService {

    /**
     * 保存商品类目
     *
     * @param saveDTO
     * @author kunbu
     * @time 2019/8/16 16:29
     * @return
     **/
    ServiceResult<CategoryEntity> saveCategory(CategoryDTO saveDTO);

    /**
     * 获取商品类目树
     *
     * @param categoryId
     * @author kunbu
     * @time 2019/8/16 16:28
     * @return
     **/
    ServiceResult<Map<String, Object>> getCategoryTree(String categoryId);

    /**
     * 获取商品类目描述（cache）
     *
     * @author kunbu
     * @time 2019/8/16 16:28
     * @return
     **/
    ServiceResult<Map<String, String>> getCategoryMap();
}
