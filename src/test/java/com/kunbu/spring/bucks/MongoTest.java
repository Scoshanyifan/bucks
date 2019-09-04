package com.kunbu.spring.bucks;

import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 16:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {

    private static final Logger logger = LoggerFactory.getLogger(MongoTest.class);

    @Autowired
    private LogMongoDB logMongoDB;

    @Test
    public void testMongo() {

        RequestLog log = new RequestLog();
        log.setDescription("mongodb");
        log.setCreateTime(new Date());
        RequestLog result = logMongoDB.saveRequestLog(log);
        logger.info(">>> log id:{}", result.getId());

        RequestLogQueryParam param = new RequestLogQueryParam();
        long nowTime = System.currentTimeMillis();
        // 时区问题，spring帮我们做了转换
        param.setStartTime(new Date(nowTime - 1000L * 60 * 10));
        param.setEndTime(new Date());
        PageResult pageInfo = logMongoDB.listRequestLog(param);
        logger.info(pageInfo.toString());
    }
}
