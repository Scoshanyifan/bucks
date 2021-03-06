package com.kunbu.spring.bucks.dao.mysql;

import com.kunbu.spring.bucks.common.entity.mysql.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderItemEntity record);

    int insertSelective(OrderItemEntity record);

    OrderItemEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderItemEntity record);

    int updateByPrimaryKey(OrderItemEntity record);
}