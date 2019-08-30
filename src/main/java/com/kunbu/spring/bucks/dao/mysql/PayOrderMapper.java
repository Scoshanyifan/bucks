package com.kunbu.spring.bucks.dao.mysql;

import com.kunbu.spring.bucks.common.entity.mysql.PayOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PayOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(PayOrderEntity record);

    int insertSelective(PayOrderEntity record);

    PayOrderEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PayOrderEntity record);

    int updateByPrimaryKey(PayOrderEntity record);
}