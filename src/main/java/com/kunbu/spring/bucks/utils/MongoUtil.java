package com.kunbu.spring.bucks.utils;

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

    /**
     * 追加条件
     *
     * @param criteria
     * @param query
     * @author kunbu
     * @time 2019/9/3 15:03
     * @return
     **/
    public static void newCriteria(Criteria criteria, Query query) {
        if (criteria != null) {
            query.addCriteria(criteria);
        }
    }

    /**
     * or
     * 
     * @param c1
     * @param c2
     * @param query       
     * @author kunbu
     * @time 2019/9/16 16:52
     * @return       
     **/
    public static void orCriteria(Criteria c1, Criteria c2 ,Query query) {
        if (c1 != null && c2 != null) {
            query.addCriteria(new Criteria().orOperator(c1, c2));
        }
    }

    /**
     * and
     *
     * @param c1
     * @param c2
     * @param query
     * @author kunbu
     * @time 2019/9/16 16:52
     * @return
     **/
    public static void andCriteria(Criteria c1, Criteria c2 , Query query) {
        if (c1 != null && c2 != null) {
            query.addCriteria(new Criteria().andOperator(c1, c2));
        }
    }

    /**
     * 字符串模糊查询
     *
     * @param name
     * @param regex
     * @author kunbu
     * @time 2019/9/3 15:01
     * @return
     **/
    public static Criteria strRegex(String name, String regex) {
        if (name != null && regex != null) {
            return Criteria.where(name).regex(regex);
        } else {
            return null;
        }
    }

    /**
     * 字符串精确查询
     *
     * @param name
     * @param value
     * @author kunbu
     * @time 2019/9/3 15:02
     * @return
     **/
    public static Criteria strIs(String name, String value) {
        if (name != null && value != null) {
            return Criteria.where(name).is(value);
        } else {
            return null;
        }
    }

    /**
     * 对象精确查询
     *
     * @param name
     * @param value
     * @author kunbu
     * @time 2019/9/3 15:03
     * @return
     **/
    public static Criteria objectIs(String name, Object value) {
        if (name != null && value != null) {
            return Criteria.where(name).is(value);
        } else {
            return null;
        }
    }

    /**
     * 时间区间过滤
     *
     * @param name
     * @param start
     * @param end
     * @author kunbu
     * @time 2019/9/3 15:02
     * @return
     **/
    public static Criteria dateCompare(String name, Date start, Date end, boolean plus8Hours) {
        if (start != null && end != null) {
            if (plus8Hours) {
                return Criteria.where(name).gte(TimeUtil.plusHours(start, 8))
                        .andOperator(Criteria.where(name).lte(TimeUtil.plusHours(end, 8)));
            } else {
                return Criteria.where(name).gte(start)
                        .andOperator(Criteria.where(name).lte(end));
            }
        } else {
            return null;
        }
    }

    /**
     * long值区间过滤
     *
     * @param name
     * @param min
     * @param max
     * @author kunbu
     * @time 2019/9/3 15:03
     * @return
     **/
    public static Criteria longCompare(String name, Long min, Long max) {
        if (min != null && max != null) {
            return Criteria.where(name).gte(min).andOperator(Criteria.where(name).lte(max));
        } else {
            return null;
        }
    }
}