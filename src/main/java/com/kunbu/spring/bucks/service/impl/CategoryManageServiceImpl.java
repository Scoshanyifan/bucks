package com.kunbu.spring.bucks.service.impl;

import com.google.common.collect.Maps;
import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.dto.CategoryDTO;
import com.kunbu.spring.bucks.common.entity.CategoryEntity;
import com.kunbu.spring.bucks.constant.CommonStateEnum;
import com.kunbu.spring.bucks.dao.CategoryMapper;
import com.kunbu.spring.bucks.error.CategoryErrorEnum;
import com.kunbu.spring.bucks.service.CategoryManageService;
import com.kunbu.spring.bucks.utils.IDGenerateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: bucks
 * @description: 商品类目管理
 * @author: kunbu
 * @create: 2019-08-16 16:29
 **/
@Service
public class CategoryManageServiceImpl implements CategoryManageService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryManageServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServiceResult<CategoryEntity> saveCategory(CategoryDTO saveDTO) {
        String categoryName = saveDTO.getCategoryName();
        CategoryEntity checkName = categoryMapper.selectByCategoryName(categoryName, CommonStateEnum.USE.name());
        if (checkName != null) {
            return ServiceResult.ERROR(CategoryErrorEnum.CATEGORY_NAME_EXIST.getMsg());
        }
        String oldId = saveDTO.getCategoryId();
        Date nowTime = new Date();
        if (StringUtils.isNotBlank(oldId)) {
            //修改
            CategoryEntity oldCategory = categoryMapper.selectByPrimaryKey(oldId);
            if (oldCategory == null || oldCategory.getState().equals(CommonStateEnum.DELETE.name())) {
                return ServiceResult.ERROR(CategoryErrorEnum.CATEGORY_NULL.getMsg());
            } else {
                oldCategory.setCategoryName(categoryName);
                oldCategory.setModifyTime(nowTime);
                oldCategory.setOperatorId(saveDTO.getOperatorId());
                categoryMapper.updateByPrimaryKeySelective(oldCategory);
            }
        } else {
            //新增
            CategoryEntity category = new CategoryEntity();
            category.setId(IDGenerateUtil.uniqueID());
            category.setCategoryName(categoryName);
            category.setParentId(saveDTO.getParentId());
            category.setLevel(saveDTO.getLevel());
            category.setState(CommonStateEnum.USE.name());
            category.setOperatorId(saveDTO.getOperatorId());
            category.setCreateTime(nowTime);
            category.setModifyTime(nowTime);
            categoryMapper.insertSelective(category);
        }
        return ServiceResult.SUCCESS;
    }

    private void updateCahe4CategoryMap() {
        List<CategoryEntity> all = categoryMapper.selectAll(CommonStateEnum.USE.name());


    }

    @Override
    public ServiceResult<Map<String, Object>> getCategoryTree(String categoryId) {
        List<CategoryEntity> all = categoryMapper.selectAll(CommonStateEnum.USE.name());
        Map<String, Object> tree = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(all)) {


        }
        return ServiceResult.SUCCESS;
    }

    @Override
    public ServiceResult<Map<String, String>> getCategoryMap() {

        //TODO cache

        return null;
    }
}
