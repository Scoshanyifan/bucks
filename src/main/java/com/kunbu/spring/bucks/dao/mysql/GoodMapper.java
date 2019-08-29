package com.kunbu.spring.bucks.dao.mysql;

import com.kunbu.spring.bucks.common.entity.GoodEntity;
import com.kunbu.spring.bucks.common.param.GoodQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodMapper {
    int deleteByPrimaryKey(String id);

    int insert(GoodEntity record);

    int insertSelective(GoodEntity record);

    GoodEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodEntity record);

    int updateByPrimaryKey(GoodEntity record);

    List<GoodEntity> queryByCondition(GoodQueryParam query);
}