package com.kunbu.spring.bucks.dao;

import com.kunbu.spring.bucks.common.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(CategoryEntity record);

    int insertSelective(CategoryEntity record);

    CategoryEntity selectByPrimaryKey(String id);

    CategoryEntity selectByCategoryName(@Param("categoryName") String categoryName, @Param("state") String state);

    int updateByPrimaryKeySelective(CategoryEntity record);

    int updateByPrimaryKey(CategoryEntity record);

    List<CategoryEntity> selectAll(String state);
}