package com.kunbu.spring.bucks.dao.mongodb;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-27 17:19
 **/
public class MongoUtil {

    public static void addCriteria(Criteria criteria, Query query) {
        if (criteria != null) {
            query.addCriteria(criteria);
        }
    }

    public static Criteria strRegex(String name, String regex) {
        if (name != null && regex != null) {
            return Criteria.where(name).regex(regex);
        } else {
            return null;
        }
    }

    public static Criteria strIs(String name, String value) {
        if (name != null && value != null) {
            return Criteria.where(name).is(value);
        } else {
            return null;
        }
    }

    public static Criteria objectIs(String name, Object value) {
        if (name != null && value != null) {
            return Criteria.where(name).is(value);
        } else {
            return null;
        }
    }

    public static Criteria dateCompare(String name, Date start, Date end) {
        if (start != null && end != null) {
            return Criteria.where(name).gte(start).andOperator(Criteria.where(name).lte(end));
        } else {
            return null;
        }
    }

    public static Criteria longCompare(String name, Long min, Long max) {
        if (min != null && max != null) {
            return Criteria.where(name).gte(min).andOperator(Criteria.where(name).lte(max));
        } else {
            return null;
        }
    }
}
