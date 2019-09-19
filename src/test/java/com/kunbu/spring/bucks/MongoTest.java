package com.kunbu.spring.bucks;

import com.google.common.collect.Lists;
import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import com.kunbu.spring.bucks.utils.DateFormatUtil;
import com.kunbu.spring.bucks.utils.MongoUtil;
import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

        Date start = DateFormatUtil.parse("2019-09-19 16:00:00", DateFormatUtil.DEFAULT_DATE_PATTERN);
        Date end = DateFormatUtil.parse("2019-09-19 18:04:00", DateFormatUtil.DEFAULT_DATE_PATTERN);

        param.setStartTime(start);
        param.setEndTime(end);
        PageResult pageInfo = logMongoDB.listRequestLog(param);
        logger.info(pageInfo.toString());
    }

    @Test
    public void testAggregation() {
        Date start = DateFormatUtil.parse("2019-09-09 16:00:00", DateFormatUtil.DEFAULT_DATE_PATTERN);
        Date end = DateFormatUtil.parse("2019-09-19 18:04:00", DateFormatUtil.DEFAULT_DATE_PATTERN);
        Criteria dateCriteria = MongoUtil.dateCompare("createTime", start, end, false);

        //统计时间段内，各个方法调用次数
        Aggregation dayAggregation = Aggregation.newAggregation(
//                Aggregation.project(),
                Aggregation.match(dateCriteria),
                Aggregation.group("methodName").count().as("count")
        );

        AggregationResults result = mongoTemplate.aggregate(dayAggregation, RequestLog.class, Document.class);
        logger.info(">>> rawResults:{}", result.getRawResults());
        logger.info(">>> mappedResults:{}", result.getMappedResults());

        List<Document> mappedResults = result.getMappedResults();

        List<CountVO> vos = Lists.newArrayList();
        for (Document doc : mappedResults) {
            vos.add(new CountVO((String) doc.get("_id"), (Integer) doc.get("count")));
        }
        logger.info(">>> vos:{}", vos);
    }

    /**
     * 原生聚合写法
     *
     **/
    @Test
    public void testRawAggregation() {

        BasicDBObject bson = new BasicDBObject();
        bson.put("$eval", "db.requestlog.aggregate([\n" +
                "    {$match:{\n" +
                "        'createTime':{$gt:ISODate(\"2019-09-09T08:00:00.000Z\"),\n" +  // 查询条件需要 -8h 变为0时区
                "                $lt:ISODate(\"2019-09-19T08:59:59.000Z\")},\n" +
//                "        'createTime':{$gt:ISODate(\"2019-05-01T00:00:00.000Z\"),\n" +
//                "                $lt:ISODate(\"2019-12-30T23:59:59.000Z\")},\n" +
                "        }\n" +
                "    },\n" +
                "    {$group:{\n" +
                "            _id:{\n" +
//                "                time:'$createTime',\n" + // 返回结果是正确的时区
//                "                time:{$month:{$add:['$createTime',8]}},\n" +
                "                count:'$methodName'},\n" +
                "            count:{$sum:1}\n" +
                "           }\n" +
                "    }\n" +
                "])");

        Document document = mongoTemplate.getDb().runCommand(bson);
        logger.info(">>> {}", document);
        Map<String, Object> retval = (Map<String, Object>) document.get("retval");
        logger.info(">>> retval:{}", retval);
        List<Object> _batch = (List<Object>) retval.get("_batch");
        logger.info(">>> _batch:{}", _batch);

    }

    @Data
    @AllArgsConstructor
    class CountVO {
        private String methodName;
        private Integer count;

    }
}

