package com.kunbu.spring.bucks.utils.mongo;

import com.mongodb.BasicDBObject;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-21 11:33
 **/
public class MongoBsonAggregationUtil {


    /**
     * { $not: [ expression ] } >>> { $not: [ { $gt: [ "$count", 250 ] } ] }
     * <p/>
     * ps: 和聚合区别是聚合多了一层[]中括号
     *
     * @param expression
     **/
    public static BasicDBObject not(BasicDBObject expression) {
        if (expression != null) {
            return new BasicDBObject("$not", new Object[]{expression});
        } else {
            return new BasicDBObject();
        }
    }


    //-------------------------- Arithmetic Operators -------------------------

    /**
     * { $add: [ '$createTime' ,8 ] }
     *
     * @param name
     * @param value
     **/
    public static BasicDBObject add(String name, Object value) {
        if (name != null && value != null) {
            return new BasicDBObject("$add", new Object[]{name, value});
        } else {
            return new BasicDBObject();
        }
    }


}
