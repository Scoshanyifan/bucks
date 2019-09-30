package com.kunbu.spring.bucks.dao.mysql;

import com.kunbu.spring.bucks.common.entity.mysql.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(CategoryEntity record);

    int insertBatch(List<CategoryEntity> list);

    int insertSelective(CategoryEntity record);

    CategoryEntity selectByPrimaryKey(String id);

    CategoryEntity selectOneUse(String state);

    CategoryEntity selectByCategoryName(@Param("categoryName") String categoryName, @Param("biz") String state);

    int updateByPrimaryKeySelective(CategoryEntity record);

    int updateByPrimaryKey(CategoryEntity record);

    int discardCategoryTree(Map<String, Object> params);

    List<CategoryEntity> selectAll(String state);

    List<CategoryEntity> selectAllByCodeAsc(String state);
}