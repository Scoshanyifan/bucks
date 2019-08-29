package com.kunbu.spring.bucks.dao.mongodb;

import com.github.pagehelper.PageInfo;
import com.kunbu.spring.bucks.common.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.RequestLogQueryParam;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RequestLogMongo {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogMongo.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public RequestLog save(RequestLog log) {
        RequestLog result = mongoTemplate.save(log);
        return result;
    }

    public PageInfo<List<RequestLog>> list(RequestLogQueryParam param) {
        logger.info(">>> RequestLogMongo list, param:{}", param);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPages(0);
        pageInfo.setTotal(0);
        pageInfo.setPageSize(param.getPageSize());
        pageInfo.setPageNum(param.getPageNum());

        Query query = new Query();

        Criteria ip = MongoUtil.strRegex("ip", param.getIpRegex());
        MongoUtil.addCriteria(ip, query);

        Criteria url = MongoUtil.strRegex("url", param.getUrlRegex());
        MongoUtil.addCriteria(url, query);

        Criteria className = MongoUtil.strRegex("className", param.getClassNameRegex());
        MongoUtil.addCriteria(className, query);

        Criteria methodName = MongoUtil.strRegex("methodName", param.getMethodNameRegex());
        MongoUtil.addCriteria(methodName, query);

        Criteria httpMethod = MongoUtil.strIs("httpMethod", param.getHttpMethod());
        MongoUtil.addCriteria(httpMethod, query);

        Criteria httpStatus = MongoUtil.strIs("httpStatus", param.getHttpStatus());
        MongoUtil.addCriteria(httpStatus, query);

        Criteria userId = MongoUtil.strIs("userId", param.getUserId());
        MongoUtil.addCriteria(userId, query);

        Criteria costTime = MongoUtil.longCompare("costTime", param.getCostTimeMin(), param.getCostTimeMax());
        MongoUtil.addCriteria(costTime, query);

        // 时区问题，spring帮我们做了转换
        Criteria createTime = MongoUtil.dateCompare("createTime", param.getStartTime(), param.getEndTime());
        MongoUtil.addCriteria(createTime, query);

        long total = mongoTemplate.count(query, RequestLog.class);
        query.skip(param.getPageNum()).limit(param.getPageSize());
        List<RequestLog> logList = mongoTemplate.find(query, RequestLog.class);
        if (CollectionUtils.isNotEmpty(logList)) {
            pageInfo.setList(logList);
            pageInfo.setTotal(total);
            pageInfo.setPages((int) (total / param.getPageSize()));
        }
        return pageInfo;
    }

}
