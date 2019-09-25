package com.kunbu.spring.bucks.utils.mongo;

import com.kunbu.spring.bucks.utils.TimeUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * spring-data-mongo Criteria下API工具类
 *
 * @program: bucks
 * @author: kunbu
 * @create: 2019-08-27 17:19
 **/
public class MongoUtil {

    /**
     * mongodb保存的是0时区时间，查询取值有可能需要转换
     */
    public static final long HOURS_8 = 28800000L;

    /**
     * 追加条件
     *
     * @param query
     * @param criteria
     * @return
     **/
    public static void newCriteria(Query query, Criteria criteria) {
        if (query != null && criteria != null) {
            query.addCriteria(criteria);
        }
    }

    /**
     * or
     * <p>
     * 如果是同一个字段，使用此方法
     * <p>
     * 如果是不同字段的or，需要用andOperator把多个orOperator连接起来
     *
     * @param cs
     * @return
     **/
    public static Criteria or(Criteria... cs) {
        if (cs != null && cs.length > 0) {
            return new Criteria().orOperator(cs);
        } else {
            return new Criteria();
        }
    }

    /**
     * and
     * <p>
     * 如果是同一个字段做组合查询，Criteria.where("field").xxx().and("field).xxx() 写法错误，即不能用同一个Criteria接收
     * <p>
     * 会报错：InvalidMongoDbApiUsageException, you can't add a second 'field' expression specified as 'field
     * <p>
     * 解决：用两个Criteria接收
     *
     * @param cs
     * @return
     **/
    public static Criteria and(Criteria... cs) {
        if (cs != null && cs.length > 0) {
            return new Criteria().andOperator(cs);
        } else {
            return new Criteria();
        }
    }

    /**
     * 字符串模糊查询
     *
     * @param name
     * @param regex
     * @return
     **/
    public static Criteria strRegex(String name, String regex) {
        if (name != null && regex != null) {
            return Criteria.where(name).regex(regex);
        } else {
            return new Criteria();
        }
    }

    /**
     * 字符串精确查询
     *
     * @param name
     * @param value
     * @return
     **/
    public static Criteria strIs(String name, String value) {
        if (name != null && value != null) {
            return Criteria.where(name).is(value);
        } else {
            return new Criteria();
        }
    }

    /**
     * 对象精确查询
     *
     * @param name
     * @param value
     * @return
     **/
    public static Criteria objectIs(String name, Object value) {
        if (name != null && value != null) {
            return Criteria.where(name).is(value);
        } else {
            return new Criteria();
        }
    }

    /**
     * 时间区间过滤（需要加上8小时）
     *
     * @param name
     * @param start
     * @param end
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
            return new Criteria();
        }
    }

    /**
     * long值区间过滤
     *
     * @param name
     * @param min
     * @param max
     * @return
     **/
    public static Criteria longCompare(String name, Long min, Long max) {
        if (min != null && max != null) {
            return Criteria.where(name).gte(min).andOperator(Criteria.where(name).lte(max));
        } else {
            return new Criteria();
        }
    }

    //--------------------------------------


}
