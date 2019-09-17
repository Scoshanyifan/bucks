package com.kunbu.spring.bucks;

import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import com.kunbu.spring.bucks.utils.DateFormatUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongo() {

//        RequestLog log = new RequestLog();
//        log.setDescription("mongodb");
//        log.setCreateTime(new Date());
//        RequestLog result = logMongoDB.saveRequestLog(log);
//        logger.info(">>> log id:{}", result.getId());

        RequestLogQueryParam param = new RequestLogQueryParam();

        Date start = DateFormatUtil.parse("2019-08-27 10:00:00", DateFormatUtil.DEFAULT_DATE_PATTERN);
        Date end = DateFormatUtil.parse("2019-08-27 10:04:00", DateFormatUtil.DEFAULT_DATE_PATTERN);

        param.setStartTime(start);
        param.setEndTime(end);
        PageResult pageInfo = logMongoDB.listRequestLog(param);
        logger.info(pageInfo.toString());
    }

    @Test
    public void testAggregation() {
//        GroupOperation group = new GroupOperation();
        //按天统计
        Aggregation dayAggregation = Aggregation.newAggregation(
                Aggregation.project(),
                Aggregation.match(new Criteria()),
                Aggregation.group()
        );


    }
}
