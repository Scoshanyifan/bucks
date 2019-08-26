package com.kunbu.spring.bucks.mongodb;

import com.kunbu.spring.bucks.common.mongo.RequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

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
        return mongoTemplate.save(log);
    }

}
