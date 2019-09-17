package com.kunbu.spring.bucks.dao.mongodb;

import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.entity.mongo.OperateLog;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.OperateLogQueryParam;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.utils.MongoUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        MongoUtil.newCriteria(ip, query);

        Criteria url = MongoUtil.strRegex("url", param.getUrlRegex());
        MongoUtil.newCriteria(url, query);

        Criteria className = MongoUtil.strRegex("className", param.getClassNameRegex());
        MongoUtil.newCriteria(className, query);

        Criteria methodName = MongoUtil.strRegex("methodName", param.getMethodNameRegex());
        MongoUtil.newCriteria(methodName, query);

        Criteria httpMethod = MongoUtil.strIs("httpMethod", param.getHttpMethod());
        MongoUtil.newCriteria(httpMethod, query);

        Criteria httpStatus = MongoUtil.strIs("httpStatus", param.getHttpStatus());
        MongoUtil.newCriteria(httpStatus, query);

        Criteria userId = MongoUtil.strIs("userId", param.getUserId());
        MongoUtil.newCriteria(userId, query);

        Criteria costTime = MongoUtil.longCompare("costTime", param.getCostTimeMin(), param.getCostTimeMax());
        MongoUtil.newCriteria(costTime, query);

        // 时区问题，需要自己做转换
        Criteria createTime = MongoUtil.dateCompare("createTime", param.getStartTime(), param.getEndTime(), true);
        MongoUtil.newCriteria(createTime, query);

        // 先查总数，再分页
        long total = mongoTemplate.count(query, RequestLog.class);
        // 排序
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        // 分页查询_1（直接使用skip+limit）
        query.skip((param.getPageNum()-1) * param.getPageSize()).limit(param.getPageSize());

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
        MongoUtil.newCriteria(operateType, query);

        Criteria content = MongoUtil.strRegex("content", param.getContent());
        MongoUtil.newCriteria(content, query);

        Criteria operatorName = MongoUtil.strRegex("operatorName", param.getOperatorName());
        MongoUtil.newCriteria(operatorName, query);

        Criteria operatorId = MongoUtil.strIs("operatorId", param.getOperatorId());
        MongoUtil.newCriteria(operatorId, query);

        Criteria operateTime = MongoUtil.dateCompare("operateTime", param.getOperateTimeStart(), param.getOperateTimeEnd(), false);
        MongoUtil.newCriteria(operateTime, query);

        long total = mongoTemplate.count(query, OperateLog.class);
        // 分页查询_2（使用Pageable，排序放在一起）
        Pageable pageable = PageRequest.of(
                (param.getPageNum()-1) * param.getPageSize(),
                param.getPageSize(),
                Sort.by(Sort.Direction.DESC, "operateTime"));
        List<OperateLog> operateLogs = mongoTemplate.find(query.with(pageable), OperateLog.class);
        if (CollectionUtils.isNotEmpty(operateLogs)) {
            pageResult.setList(operateLogs);
            pageResult.setTotal(total);
            pageResult.setPages(total / param.getPageSize());
        }
        return pageResult;
    }

}
