package com.kunbu.spring.bucks.dao.mongodb;

import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.entity.mongo.OperateLog;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.OperateLogQueryParam;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.utils.mongo.MongoUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-26 17:38
 **/
@Component
public class LogMongoDB {

    private static final Logger logger = LoggerFactory.getLogger(LogMongoDB.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public RequestLog saveRequestLog(RequestLog log) {
        RequestLog result = mongoTemplate.save(log);
        return result;
    }

    public PageResult<List<RequestLog>> listRequestLog(RequestLogQueryParam param) {
        PageResult pageResult = PageResult.init(param.getPageNum(), param.getPageSize());

        Query query = new Query();

        Criteria ip = MongoUtil.strRegex("ip", param.getIpRegex());
        MongoUtil.newCriteria(query, ip);

        Criteria url = MongoUtil.strRegex("url", param.getUrlRegex());
        MongoUtil.newCriteria(query, url);

        Criteria className = MongoUtil.strRegex("className", param.getClassNameRegex());
        MongoUtil.newCriteria(query, className);

        Criteria methodName = MongoUtil.strRegex("methodName", param.getMethodNameRegex());
        MongoUtil.newCriteria(query, methodName);

        Criteria httpMethod = MongoUtil.strIs("httpMethod", param.getHttpMethod());
        MongoUtil.newCriteria(query, httpMethod);

        Criteria httpStatus = MongoUtil.strIs("httpStatus", param.getHttpStatus());
        MongoUtil.newCriteria(query, httpStatus);

        Criteria userId = MongoUtil.strIs("userId", param.getUserId());
        MongoUtil.newCriteria(query, userId);

        Criteria costTime = MongoUtil.longCompare("costTime", param.getCostTimeMin(), param.getCostTimeMax());
        MongoUtil.newCriteria(query, costTime);

        // 时区问题(springboot帮我们做了转换，入参和结果，数据库存的是0区时刻)
        Criteria createTime = MongoUtil.dateCompare("createTime", param.getStartTime(), param.getEndTime(), false);
        MongoUtil.newCriteria(query, createTime);

        // 总数
        long total = mongoTemplate.count(query, RequestLog.class);
        // 排序
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        // 分页
        query.skip((param.getPageNum() - 1) * param.getPageSize()).limit(param.getPageSize());
        logger.info(">>> mongo:{}", query);
        List<RequestLog> logList = mongoTemplate.find(query, RequestLog.class);
        if (CollectionUtils.isNotEmpty(logList)) {
            pageResult.setList(logList);
            pageResult.setTotal(total);
            pageResult.setPages(total / param.getPageSize() + 1);
        }
        return pageResult;
    }

    public OperateLog saveOperateLog(OperateLog log) {
        OperateLog result = mongoTemplate.save(log);
        return result;
    }

    public PageResult<List<OperateLog>> listOperateLog(OperateLogQueryParam param) {
        PageResult pageResult = PageResult.init(param.getPageNum(), param.getPageSize());

        Query query = new Query();

        Criteria operateType = MongoUtil.strIs("operateType", param.getOperateType());
        MongoUtil.newCriteria(query, operateType);

        Criteria content = MongoUtil.strRegex("content", param.getContent());
        MongoUtil.newCriteria(query, content);

        Criteria operatorName = MongoUtil.strRegex("operatorName", param.getOperatorName());
        MongoUtil.newCriteria(query, operatorName);

        Criteria operatorId = MongoUtil.strIs("operatorId", param.getOperatorId());
        MongoUtil.newCriteria(query, operatorId);

        Criteria operateTime = MongoUtil.dateCompare("operateTime", param.getOperateTimeStart(), param.getOperateTimeEnd(), false);
        MongoUtil.newCriteria(query, operateTime);

        long total = mongoTemplate.count(query, OperateLog.class);
        // 分页
        query.skip((param.getPageNum() - 1) * param.getPageSize()).limit(param.getPageSize());

        // 分页查询（使用Pageable，排序放在一起） 不推荐使用 TODO
//        Pageable pageable = PageRequest.of(
//                (param.getPageNum()-1) * param.getPageSize(),
//                param.getPageSize(),
//                Sort.by(Sort.Direction.DESC, "operateTime"));

        List<OperateLog> operateLogs = mongoTemplate.find(query, OperateLog.class);
        if (CollectionUtils.isNotEmpty(operateLogs)) {
            pageResult.setList(operateLogs);
            pageResult.setTotal(total);
            pageResult.setPages(total / param.getPageSize());
        }
        return pageResult;
    }

}
