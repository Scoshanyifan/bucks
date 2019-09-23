package com.kunbu.spring.bucks.utils.mongo;

import com.mongodb.BasicDBObject;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-21 11:33
 **/
public class MongoBsonAggregationUtil {

    //------------------------ Boolean Operators ---------------------------

    /**
     * 同query and
     *
     * @param expressions
     **/
    public static BasicDBObject and(BasicDBObject... expressions) {
        return MongoBsonQueryUtil.and(expressions);
    }

    /**
     * 同query or
     *
     * @param expressions
     **/
    public static BasicDBObject or(BasicDBObject... expressions) {
        return MongoBsonQueryUtil.or(expressions);
    }

    /**
     * { $not: [ expression ] } >>> { $not: [ { $gt: [ "$price", 2.33 ] } ] }
     *
     * ps: 注意和Query下的区别：{ "price": { $not: { $gt: 1.99 } } }
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

    // abs / add / ceil / divide / exp / floor / ln / log / log10 / mod / multiply / pow / sqrt / subtract / trunc

    /**
     * { $add: [ expression1, expression2, ... ] } >>> { $add: [ '$createTime' ,8 ] }
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

    //------------------------- String Operators ------------------------------

    /**
     * 字符串拼接
     * { $concat: [ expression1, expression2, ... ] } >>> { $concat: [ "$item", " - ", "$description" ] }
     * 
     * @param fields 如果是字段，需要自带$
     * @return
     **/
    public static BasicDBObject concat(String... fields) {
        if (fields != null && fields.length > 0) {
            return new BasicDBObject("$concat", new Object[]{fields});
        } else {
            return new BasicDBObject();
        }
    }


}
