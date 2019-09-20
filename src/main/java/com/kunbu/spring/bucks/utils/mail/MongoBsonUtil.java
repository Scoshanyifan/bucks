package com.kunbu.spring.bucks.utils.mail;

import com.mongodb.BasicDBObject;
import org.bson.conversions.Bson;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-20 20:32
 **/
public class MongoBsonUtil {

    //----------------------- Boolean Aggregation Operators -------------------------

    /**
     * { $and: [ <expression1>, <expression2>, ... ] }
     * <br/>
     * $and: [ { $gt: [ "$qty", 100 ] }, { $lt: [ "$qty", 250 ] } ]
     *
     * @param expression1
     * @param expression2
     **/
    public static Bson and(BasicDBObject expression1, BasicDBObject expression2) {
        if (expression1 != null && expression2 != null) {
            return new BasicDBObject("$and", new BasicDBObject[]{expression1, expression2});
        } else {
            return new BasicDBObject();
        }
    }

    /**
     * { $or: [ <expression1>, <expression2>, ... ] }
     *
     * @param expression1
     * @param expression2
     **/
    public static Bson or(BasicDBObject expression1, BasicDBObject expression2) {
        if (expression1 != null && expression2 != null) {
            return new BasicDBObject("$or", new BasicDBObject[]{expression1, expression2});
        } else {
            return new BasicDBObject();
        }
    }

    /**
     * { $not: [ <expression> ] }
     * <br/>
     * { $not: [ { $gt: [ "$qty", 250 ] } ] }
     *
     * @param expression
     **/
    public static Bson not(BasicDBObject expression) {
        if (expression != null) {
            return new BasicDBObject("$not", expression);
        } else {
            return new BasicDBObject();
        }
    }

    //------------------------------ Arithmetic Aggregation Operators ----------------

    /**
     * { $add: [ '$createTime' ,8 ] }
     *
     * @param name
     * @param value
     **/
    public static Bson add(String name, Object value) {
        if (name != null && value != null) {
            return new BasicDBObject("$add", new Object[]{name, value});
        } else {
            return new BasicDBObject();
        }
    }

}
