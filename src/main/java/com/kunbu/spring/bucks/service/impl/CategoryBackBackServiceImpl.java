package com.kunbu.spring.bucks.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.dto.CategoryDTO;
import com.kunbu.spring.bucks.common.entity.mysql.CategoryEntity;
import com.kunbu.spring.bucks.constant.biz.CommonStateEnum;
import com.kunbu.spring.bucks.constant.CacheConstant;
import com.kunbu.spring.bucks.dao.mysql.CategoryMapper;
import com.kunbu.spring.bucks.error.bis.CategoryErrorEnum;
import com.kunbu.spring.bucks.dao.redis.RedisManager;
import com.kunbu.spring.bucks.service.CategoryBackService;
import com.kunbu.spring.bucks.utils.ExecutorUtil;
import com.kunbu.spring.bucks.utils.IDGenerateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: bucks
 * @description: 商品类目管理
 * @author: kunbu
 * @create: 2019-08-16 16:29
 **/
@Service
public class CategoryBackBackServiceImpl implements CategoryBackService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryBackBackServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisManager redisManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult<CategoryEntity> saveCategoryTree(CategoryDTO root, String operatorId) {
        Set<String> cateNameSet = Sets.newHashSet();
        Set<Integer> cateCodeSet = Sets.newHashSet();
        List<CategoryDTO> cateDTOList = Lists.newArrayList();
        //检查类木属，因为有根节点，所以从0开始（实际节点：1 -> n）
        String checkResult = checkCategoryTree(root, cateNameSet, cateCodeSet, cateDTOList, null, 0);
        if (checkResult != null) {
            String msg = String.format(CategoryErrorEnum.CATEGORY_SAVE_ERROR.getMsg(), checkResult);
            return ServiceResult.ERROR(msg);
        }
        logger.info(">>> saveCategoryTree, cateCodeSet:{}", cateCodeSet);
        logger.info(">>> saveCategoryTree, cateNameSet:{}", cateNameSet);
        try {
            Date nowTime = new Date();
            //默认version=1
            Integer version = 1;
            //将原先类目树留存
            CategoryEntity oneUse = categoryMapper.selectOneUse(CommonStateEnum.USE.name());
            if (oneUse != null) {
                version = oneUse.getVersion();
                Map<String, Object> params = Maps.newHashMap();
                params.put("setState", CommonStateEnum.DELETE.name());
                params.put("queryState", CommonStateEnum.USE.name());
                params.put("modifyTime", nowTime);
                params.put("operatorId", operatorId);
                int discardCount = categoryMapper.discardCategoryTree(params);
                logger.info(">>> saveCategoryTree, discardCount:{}", discardCount);
            }
            //保存新类目树，并且version+1
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
            int batchInsertRes = categoryMapper.insertBatch(entityList);
            logger.info(">>> saveCategoryTree, batchInsertRes:{}", batchInsertRes);
            // update cache
            ExecutorUtil.addTask(() -> updateCache4CategoryMap());
            return ServiceResult.SUCCESS;
        } catch (Exception e) {
            logger.error(">>> saveCategoryTree error.", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查类目树，并做一些初始化操作
     *
     * @param dto
     * @param cateNameSet
     * @param cateCodeSet
     * @param cateDTOList
     * @param parentId
     * @param level
     * @return
     * @author kunbu
     * @time 2019/8/17 10:28
     **/
    private static String checkCategoryTree(CategoryDTO dto, Set<String> cateNameSet, Set<Integer> cateCodeSet,
                                            List<CategoryDTO> cateDTOList, String parentId, int level) {
        //非根节点进行检查和保存（根节点不需要id）
        String categoryId = null;
        if (!dto.isRoot()) {
            boolean checkName = cateNameSet.add(dto.getCategoryName());
            boolean checkCode = cateCodeSet.add(dto.getCategoryCode());
            if (!checkName) {
                return "name: " + dto.getCategoryName();
            }
            if (!checkCode) {
                return "code:" + dto.getCategoryCode().toString();
            }
            if (dto.getLevel() != level) {
                return "level: " + level;
            }
            //处理id
            categoryId = IDGenerateUtil.UUID();
            dto.setCategoryId(categoryId);
            dto.setParentId(parentId);
            //保存在list中方便之后db
            cateDTOList.add(dto);
        }
        //下一级
        level++;

        String checkResult = null;
        List<CategoryDTO> subs = dto.getSubs();
        if (CollectionUtils.isNotEmpty(subs)) {
            for (CategoryDTO sub : subs) {
                checkResult = checkCategoryTree(sub, cateNameSet, cateCodeSet, cateDTOList, categoryId, level);
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
            return ServiceResult.ERROR(CategoryErrorEnum.CATEGORY_NAME_EXIST);
        }
        if (checkName.getCategoryCode().compareTo(saveDTO.getCategoryCode()) == 0) {
            return ServiceResult.ERROR(CategoryErrorEnum.CATEGORY_CODE_EXIST);
        }
        String oldId = saveDTO.getCategoryId();
        Date nowTime = new Date();
        if (StringUtils.isNotBlank(oldId)) {
            //修改
            CategoryEntity oldCategory = categoryMapper.selectByPrimaryKey(oldId);
            if (oldCategory == null || oldCategory.getState().equals(CommonStateEnum.DELETE.name())) {
                return ServiceResult.ERROR(CategoryErrorEnum.CATEGORY_NULL);
            } else {
                oldCategory.setCategoryName(categoryName);
                oldCategory.setModifyTime(nowTime);
                oldCategory.setOperatorId(operatorId);
                categoryMapper.updateByPrimaryKeySelective(oldCategory);
            }
        } else {
            //新增
            CategoryEntity category = new CategoryEntity();
            category.setId(IDGenerateUtil.UUID());
            category.setCategoryName(categoryName);
            category.setParentId(saveDTO.getParentId());
            category.setLevel((byte) saveDTO.getLevel());
            category.setState(CommonStateEnum.USE.name());
            category.setOperatorId(operatorId);
            category.setCreateTime(nowTime);
            category.setModifyTime(nowTime);
            categoryMapper.insertSelective(category);
        }
        // update cache
        ExecutorUtil.addTask(() -> updateCache4CategoryMap());
        return ServiceResult.SUCCESS;
    }

    @Override
    public ServiceResult<CategoryDTO> getCategoryTree() {
        //设置根节点
        CategoryDTO root = new CategoryDTO();
        root.setRoot(true);
        root.setLevel(0);
        root.setSubs(Lists.newArrayList());

        List<CategoryEntity> all = categoryMapper.selectAll(CommonStateEnum.USE.name());
        if (CollectionUtils.isNotEmpty(all)) {
            // convert
            List<CategoryDTO> cateDTOAll = Lists.newArrayListWithCapacity(all.size());
            all.stream().forEach(x -> {
                CategoryDTO cateDTO = CategoryDTO.of(x.getId(), x.getCategoryName(), x.getCategoryCode(), x.getParentId(), x.getLevel());
                cateDTO.setSubs(Lists.newArrayList());
                cateDTOAll.add(cateDTO);
            });
            // level -> listRequestLog
            Map<Integer, List<CategoryDTO>> level2cateMap = cateDTOAll.stream().collect(Collectors.groupingBy(CategoryDTO::getLevel));
            logger.info(">>> levels:{}", level2cateMap.keySet());
            for (int i = 1; i < level2cateMap.size(); i++) {
                List<CategoryDTO> cateListOne = level2cateMap.get(i);
                if (i == 1) {
                    //根节点插入level one
                    root.setSubs(cateListOne);
                }
                List<CategoryDTO> cateListNext = level2cateMap.get(i + 1);
                for (CategoryDTO one : cateListOne) {
                    for (CategoryDTO next : cateListNext) {
                        if (one.getCategoryId().equals(next.getParentId())) {
                            one.getSubs().add(next);
                        }
                    }
                }
            }
        }
        return ServiceResult.SUCCESS(root);
    }

    @Override
    public ServiceResult<Map<String, String>> getCategoryMap(boolean ifCache) {
        //非缓存直接走DB
        if (!ifCache) {
            return ServiceResult.SUCCESS(getCategoryMapDB());
        } else {
            HashMap<String, String> categoryMap = (HashMap<String, String>) redisManager.getObject(CacheConstant.CACHE_KEY_CATEGORY_MAP);
            if (categoryMap != null) {
                return ServiceResult.SUCCESS(categoryMap);
            } else {
                return ServiceResult.SUCCESS(getCategoryMapDB());
            }
        }
    }

    private Map<String, String> getCategoryMapDB() {
        Map<String, String> cateId2NameMap = Maps.newHashMap();
        List<CategoryEntity> all = categoryMapper.selectAll(CommonStateEnum.USE.name());
        if (CollectionUtils.isNotEmpty(all)) {
            all.stream().forEach(x -> cateId2NameMap.put(x.getId(), x.getCategoryName()));
        }
        return cateId2NameMap;
    }

    /**
     * 先更新数据库，再删除缓存
     *
     * @param
     * @return
     * @author kunbu
     * @time 2019/8/28 17:32
     **/
    private void updateCache4CategoryMap() {
        boolean delResult = redisManager.delKey(CacheConstant.CACHE_KEY_CATEGORY_MAP);
        if (!delResult) {
            logger.error(">>> updateCache4CategoryMap failure");
        }

//        HashMap<String, String> categoryMap = (HashMap<String, String>) getCategoryMapDB();
//        boolean updateResult = redisManager.set(CacheConstant.CACHE_KEY_CATEGORY_MAP, categoryMap);
//        if (!updateResult) {
//            logger.error(">>> updateCache4CategoryMap failure");
//        }
    }

    @Override
    public ServiceResult<List<CategoryEntity>> getCategoryListSort() {
        List<CategoryEntity> categoryListSort = Lists.newArrayList();

        List<CategoryEntity> allSort = categoryMapper.selectAllByCodeAsc(CommonStateEnum.USE.name());
        if (CollectionUtils.isNotEmpty(allSort)) {
            // 一级类目
            List<CategoryEntity> parentList = allSort
                    .stream()
                    .filter(x -> x.getLevel() == 1)
                    .collect(Collectors.toList());
            // code > sub
            Map<Integer, List<CategoryEntity>> code2SubListMap = allSort
                    .stream()
                    .filter(x -> x.getLevel() != 1)
                    .collect(Collectors.groupingBy(CategoryEntity::getCategoryCode));
            //fillUp
            fillUpListSort(parentList, code2SubListMap, categoryListSort);
        }
        return ServiceResult.SUCCESS(categoryListSort);
    }

    private void fillUpListSort(List<CategoryEntity> parentList,
                                Map<Integer, List<CategoryEntity>> code2SubListMap,
                                List<CategoryEntity> listSort) {
        for (CategoryEntity parent : parentList) {
            listSort.add(parent);
            List<CategoryEntity> subList = code2SubListMap.get(parent.getCategoryCode());
            if (CollectionUtils.isNotEmpty(subList)) {
                fillUpListSort(subList, code2SubListMap, listSort);
            }
        }
    }
}
