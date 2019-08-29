package com.kunbu.spring.bucks.dao.redis.cache;

import java.io.Serializable;

/**
 * 缓存管理类
 *
 * @author kunbu
 * @time 2019/8/29 10:28
 * @return
 **/
public interface CacheManager {

    /**
     * 获取字符串
     *
     * @param key
     * @author kunbu
     * @time 2019/8/27 14:24
     * @return
     **/
    String getString(String key);

    /**
     * 获取对象
     *
     * @param key
     * @author kunbu
     * @time 2019/8/27 14:25
     * @return
     **/
    Object getObject(String key);

    /**
     * 设值
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, Serializable value);

    /**
     * 带过期时间的设值
     *
     * @param key
     * @param value
     * @param expire seconds
     *               @return
     */
    boolean set(String key, Serializable value, long expire);

    /**
     * 判断key是否存在
     *
     * @param key
     * @author kunbu
     * @time 2019/8/27 14:25
     * @return
     **/
    boolean existKey(String key);

    /**
     * 删除单个key
     *
     * @param key
     * @author kunbu
     * @time 2019/8/27 14:25
     * @return
     **/
    boolean delKey(String key);

    /**
     * 批量删除keys
     *
     * @param keys
     * @author kunbu
     * @time 2019/8/27 14:25
     * @return
     **/
    long delKeys(String[] keys);


    /**
     * 设置过期时间
     *
     * @param key
     * @param expire
     * @author kunbu
     * @time 2019/8/27 14:26
     * @return
     **/
    boolean expire(String key, long expire);

    /**
     * 获取key的过期时间
     *
     * @param key
     * @author kunbu
     * @time 2019/8/27 14:26
     * @return
     **/
    long getExpire(String key);

    /**
     * 递增
     *
     * @param key
     * @author kunbu
     * @time 2019/8/27 14:40
     * @return
     **/
    long incr(String key);

    /**
     * 增加
     *
     * @param key
     * @param delta
     * @author kunbu
     * @time 2019/8/27 14:42
     * @return
     **/
    long incr(String key, long delta);

    /**
     * 减少
     *
     * @param key
     * @param delta
     * @author kunbu
     * @time 2019/8/27 14:42
     * @return
     **/
    long decr(String key, long delta);

    //=================== map ======================

    Object hget(String key, String hashKey);

    boolean hput(String key, String hashKey, Object value);

    long hdel(String key, Object... hashKeys);

    //================== atomic =====================

    String getSet(String key, String value);

    boolean setnx(String key, Long value);

    boolean setnx(String key, String value);

}
