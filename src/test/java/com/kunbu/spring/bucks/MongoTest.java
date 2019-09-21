package com.kunbu.spring.bucks;

import com.google.common.collect.Lists;
import com.kunbu.spring.bucks.common.PageResult;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import com.kunbu.spring.bucks.utils.DateFormatUtil;
import com.kunbu.spring.bucks.utils.mongo.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    /**
     * https://www.cnblogs.com/woshimrf/p/mongodb-pagenation-performance.html
     *
     **/
    @Test
    public void testPage() {

    }

    /**
     * Aggregation agg = newAggregation(
     *     pipelineOP1(),
     *     pipelineOP2(),
     *     pipelineOPn()
     * );
     *
     * 时区的坑：https://blog.csdn.net/zzq900503/article/details/85606222
     *
     **/
    @Test
    public void testAggregation() {

        Criteria httpMethodCriteria = MongoUtil.strIs("httpMethod", "GET");
        Criteria timeCriteria = MongoUtil.dateCompare("createTime",
                DateFormatUtil.parse("2019-09-09 16:00:00", DateFormatUtil.DEFAULT_DATE_PATTERN),
                DateFormatUtil.parse("2019-09-19 18:04:00", DateFormatUtil.DEFAULT_DATE_PATTERN),
                false);

        //统计时间段内，各个方法调用次数
        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project(),
                Aggregation.match(new Criteria().andOperator(timeCriteria, httpMethodCriteria)),
                Aggregation.group("methodName", "url").count().as("count")
        );
        logger.info(">>> aggregation:{}", aggregation);
        // 查询
        AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(aggregation, RequestLog.class, Document.class);
        logger.info(">>> rawResults:{}", aggregationResults.getRawResults());
        logger.info(">>> mappedResults:{}", aggregationResults.getMappedResults());
        //结果 document
        List<Document> mappedResults = aggregationResults.getMappedResults();
        List<CountVO> vos = Lists.newArrayList();
        for (Document doc : mappedResults) {
            vos.add(new CountVO((String) doc.get("_id"), (Integer) doc.get("count")));
        }
        logger.info(">>> size:{}, vos:{}", vos.size(), vos);

        //---------------------- api不支持group复杂写法

        Aggregation customerAgg = Aggregation.newAggregation(
                Aggregation
                        .project("buyerNick","payment","orders","num")
                        .andExpression("sendTime")
                        .plus(MongoUtil.HOURS_8)
                        .extractMonth()
                        .as("month"),
                Aggregation.match(httpMethodCriteria),
                Aggregation.unwind("orders"),
                Aggregation
                        .group("buyerNick")
                        .first("buyerNick").as("buyerNick")
                        .sum("payment").as("totalPayment")
                        .sum("num").as("itemNum")
                        .count().as("orderNum"),
                Aggregation.sort(Sort.Direction.DESC, "totalPayment"),
                Aggregation.skip(100L),
                Aggregation.limit(10)
            );
        logger.info(">>> aggregation:{}", customerAgg);

    }

    /**
     * 非api式写法
     * https://blog.csdn.net/congcong68/article/details/52821159
     *
     **/
    @Test
    public void testRawAggregation() {
        Set<String> onumberSet=new HashSet<>();
        onumberSet.add("abcd113");
        onumberSet.add("adxc332");
        //过滤条件
        BasicDBObject queryObject=new BasicDBObject("onumber", new BasicDBObject("$in",onumberSet));
        BasicDBObject queryMatch=new BasicDBObject("$match",queryObject);
        logger.info(">>> match:{}", queryMatch);
        //展开数组
        BasicDBObject queryUnwind=new BasicDBObject("$unwind","$items");
        //分组统计
        BasicDBObject groupObject=new BasicDBObject("_id",new BasicDBObject("ino","$items.ino"));
        groupObject.put("total", new BasicDBObject("$sum","$items.quantity"));
        BasicDBObject  queryGroup=new BasicDBObject("$group",groupObject);
        //过滤条件
        BasicDBObject finalizeMatch=new BasicDBObject("$match",new BasicDBObject("total",new BasicDBObject("$gt",1)));

        List<BasicDBObject> piplelines = Lists.newArrayList(queryMatch,queryUnwind,queryGroup,finalizeMatch);
        AggregateIterable<Document> results = mongoTemplate.getCollection("orders").aggregate(piplelines);
        for (Document doc : results) {
            logger.info(">>> doc:{}", doc);
        }

        //-------------------------------


        Date start = DateFormatUtil.parse("2019-09-09 16:00:00", DateFormatUtil.DEFAULT_DATE_PATTERN);
        Date end = DateFormatUtil.parse("2019-09-19 18:04:00", DateFormatUtil.DEFAULT_DATE_PATTERN);

        // match
        BasicDBObject match = new BasicDBObject();
        BasicDBObject[] timeAnd = new BasicDBObject[]{
                new BasicDBObject("createTime", new BasicDBObject("$gt", start)),
                new BasicDBObject("createTime", new BasicDBObject("$lt", end)),
        };
        match.put("$and", timeAnd);
        match.put("httpStatus", "200");
        //{$and=[{createTime={$gt=2019-09-09T16:00:00.000+0800}}, {createTime={$lt=2019-09-19T18:04:00.000+0800}}], httpStatus=200}
        logger.info(">>> match:{}", match);

        // group
        BasicDBObject group = new BasicDBObject();
        BasicDBObject _id = new BasicDBObject();
        // month:{$month:{$add:['$createTime',8]}}
        _id.put("methodName", "$methodName");
        _id.put("month", new BasicDBObject("$month", new BasicDBObject("$add", new Object[]{"$createTime", 8})));
        _id.put("day", new BasicDBObject("$dayOfMonth", new BasicDBObject("$add", new Object[]{"$createTime", 8})));
        group.put("_id", _id);
        group.put("count", new BasicDBObject("$sum", 1));
        //{_id={month={$month={$add=[$createTime, 8]}}, day={$dayOfMonth={$add=[$createTime, 8]}}}, count={$sum=1}}
        logger.info(">>> group:{}", group);

        List<BasicDBObject> piples = Lists.newArrayList(
                new BasicDBObject("$match", match),
                new BasicDBObject("$group", group)
        );
        AggregateIterable<Document> docs = mongoTemplate.getCollection("requestlog").aggregate(piples);
        for (Document doc : docs) {
            logger.info(">>> doc:{}", doc);
        }
    }

    /**
     * 底层聚合写法
     *
     **/
    @Test
    public void testSuperRawAggregation() {

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
                "                time:'$createTime',\n" + // 返回结果是正确的时区
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

