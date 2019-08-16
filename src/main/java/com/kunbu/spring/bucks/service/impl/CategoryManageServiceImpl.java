package com.kunbu.spring.bucks.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult<CategoryEntity> saveCategoryTree(CategoryDTO saveDTO, String operatorId) {
        Set<String> cateNameSet = Sets.newHashSet();
        Set<Integer> cateCodeSet = Sets.newHashSet();
        List<CategoryDTO> cateDTOList = Lists.newArrayList();
        String checkResult = checkCategoryTree(saveDTO, cateNameSet, cateCodeSet, cateDTOList, null, 1);
        if (checkResult != null) {
            String msg = String.format(CategoryErrorEnum.CATEGORY_SAVE_ERROR.getMsg(), checkResult);
            return ServiceResult.ERROR(msg);
        }
        try {
            Date nowTime = new Date();
            //TODO 将原先类目树version+1留存
            CategoryEntity oneUse = categoryMapper.selectOneUse(CommonStateEnum.USE.name());
            Integer version = 1;
            if (oneUse != null) {
                version = oneUse.getVersion();
                Map<String, Object> params = Maps.newHashMap();
                params.put("setState", CommonStateEnum.DELETE.name());
                params.put("queryState", CommonStateEnum.USE.name());
                params.put("modifyTime", nowTime);
                params.put("operatorId", operatorId);
                int discardCount = categoryMapper.discardCategoryTree(params);
                logger.info(">>> discard count:{}", discardCount);
            }
            //TODO 将新类目树保存
            List<CategoryEntity> entityList = Lists.newArrayListWithCapacity(cateDTOList.size());
            Integer newVersion = version + 1;
            cateDTOList.stream().forEach(x -> {
                CategoryEntity entity = new CategoryEntity();
                entity.setCategoryName(x.getCategoryName());
                entity.setOperatorId(operatorId);
                entity.setLevel((byte) x.getLevel());
                entity.setParentId(x.getParentId());
                entity.setId(x.getCategoryId());
                entity.setCategoryCode(x.getCategoryCode());
                entity.setState(CommonStateEnum.USE.name());
                entity.setVersion(newVersion);
                entity.setCreateTime(nowTime);
                entity.setModifyTime(nowTime);
                entityList.add(entity);
            });

            return ServiceResult.SUCCESS;
        } catch (Exception e) {
            logger.error(">>> saveCategoryTree error.", e);
            throw new RuntimeException(e);
        }
    }

    private String checkCategoryTree(
            CategoryDTO dto,
            Set<String> cateNameSet,
            Set<Integer> cateCodeSet,
            List<CategoryDTO> cateDTOList,
            String parentId,
            int level) {
        cateDTOList.add(dto);

        boolean checkName = cateNameSet.add(dto.getCategoryName());
        boolean checkCode = cateCodeSet.add(dto.getCategoryCode());
        if (!checkName) {
            return dto.getCategoryName();
        }
        if (!checkCode) {
            return dto.getCategoryCode().toString();
        }
        if (dto.getLevel() != level) {
            return "level";
        }
        //填入id
        String categoryId = IDGenerateUtil.uniqueID();
        dto.setCategoryId(categoryId);
        dto.setParentId(parentId);

        String checkResult = null;
        List<CategoryDTO> subs = dto.getSubs();
        if (CollectionUtils.isNotEmpty(subs)) {
            for (CategoryDTO sub : subs) {
                checkResult = checkCategoryTree(sub, cateNameSet, cateCodeSet, cateDTOList, categoryId, level++);
                if (checkResult != null) {
                    break;
                }
            }
        }
        return checkResult;
    }

    @Override
    public ServiceResult<CategoryEntity> saveCategory(CategoryDTO saveDTO, String operatorId) {
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
                oldCategory.setOperatorId(operatorId);
                categoryMapper.updateByPrimaryKeySelective(oldCategory);
            }
        } else {
            //新增
            CategoryEntity category = new CategoryEntity();
            category.setId(IDGenerateUtil.uniqueID());
            category.setCategoryName(categoryName);
            category.setParentId(saveDTO.getParentId());
            category.setLevel((byte) saveDTO.getLevel());
            category.setState(CommonStateEnum.USE.name());
            category.setOperatorId(operatorId);
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
