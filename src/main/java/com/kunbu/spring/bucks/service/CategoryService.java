package com.kunbu.spring.bucks.service;

import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.dto.CategoryDTO;
import com.kunbu.spring.bucks.common.entity.mysql.CategoryEntity;

import java.util.Map;

/**
 * 类目微服务层
 *
 * @author: KunBu
 * @time: 2019/8/16 16:23
 * @description:
 */
public interface CategoryService {

    /**
     * 保存整颗类目树
     *
     * @param root
     * @return
     * @author kunbu
     * @time 2019/8/16 16:29
     **/
    ServiceResult<CategoryEntity> saveCategoryTree(CategoryDTO root, String operatorId);

    /**
     * 保存单个类目
     *
     * @param saveDTO
     * @return
     * @author kunbu
     * @time 2019/8/16 16:29
     **/
    ServiceResult<CategoryEntity> saveCategory(CategoryDTO saveDTO, String operatorId);

    /**
     * 获取商品类目树
     *
     * @return
     * @author kunbu
     * @time 2019/8/16 16:28
     **/
    ServiceResult<CategoryDTO> getCategoryTree();

    /**
     * 获取商品类目名称（全部）
     *
     * @return
     * @author kunbu
     * @time 2019/8/16 16:28
     **/
    ServiceResult<Map<String, String>> getCategoryMap(boolean ifCache);
}
