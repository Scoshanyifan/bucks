package com.kunbu.spring.bucks.manage.impl;

import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.entity.mongo.OperateLog;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.OperateLogQueryParam;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import com.kunbu.spring.bucks.manage.LogManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 09:11
 **/
@Service
public class LogManageImpl implements LogManage {

    private static final Logger logger = LoggerFactory.getLogger(LogManageImpl.class);

    @Autowired
    private LogMongoDB logMongoDB;

    @Override
    public ApiResult queryOperateLog(OperateLogQueryParam param) {
        logger.info(">>> LogManage queryOperateLog, param:{}", param);

        PageResult<List<OperateLog>> operateLogPage = logMongoDB.listOperateLog(param);
        return ApiResult.success(operateLogPage);
    }

    @Override
    public ApiResult queryRequestLog(RequestLogQueryParam param) {
        logger.info(">>> LogManage queryRequestLog, param:{}", param);

        PageResult<List<RequestLog>> requestLogPage = logMongoDB.listRequestLog(param);
        return ApiResult.success(requestLogPage);
    }
}
