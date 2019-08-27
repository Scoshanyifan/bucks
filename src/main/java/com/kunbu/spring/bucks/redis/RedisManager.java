package com.kunbu.spring.bucks.redis;

import com.kunbu.spring.bucks.redis.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author kunbu
 * @time 2019/8/27 13:16
 * @return
 **/
@Component
public class RedisManager implements CacheManager {
    
    @Autowired
    private RedisTemplate<String, Object>           redisTemplate;
    @Autowired
    private StringRedisTemplate                     stringRedisTemplate;

    //================================ string ==================================

    @Override
    public String getString(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getObject(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean set(String key, Serializable value) {
        return set(key, value, -1L);
    }

    @Override
    public boolean set(String key, Serializable value, long expire) {
        if (key == null) {
            return false;
        }
        //设置过期时间
        if (expire > 0) {
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(key, (String) value, expire, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            }
        } else {
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(key, (String) value);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
        }
        return true;
    }

    @Override
    public boolean existKey(String key) {
        if (key == null) {
            return false;
        } else {
            return redisTemplate.hasKey(key);
        }
    }

    @Override
    public boolean delKey(String key) {
        if (key != null) {
            return redisTemplate.delete(key);
        } else {
            return false;
        }
    }

    @Override
    public long delKeys(String[] keys) {
        if (keys != null && keys.length > 0) {
            return redisTemplate.delete(Arrays.asList(keys));
        } else {
            return 0;
        }
    }

    @Override
    public boolean expire(String key, long expire) {
        if (expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (expire == null) {
            return 0;
        }
        return expire.longValue();
    }

    @Override
    public long incr(String key) {
        return incr(key, 1L);
    }

    @Override
    public long incr(String key, long delta) {
        if (key == null) {
            throw new RuntimeException("key cant be null");
        }
        if (delta <= 0) {
            throw new RuntimeException("delta must be positive");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public long decr(String key, long delta) {
        if (key == null) {
            throw new RuntimeException("key cant be null");
        }
        if (delta <= 0) {
            throw new RuntimeException("delta must be positive");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    //================================ map ===================================

    @Override
    public boolean hput(String key, String hashKey, Object value) {
        if (key != null && hashKey != null && value != null) {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long hdel(String key, Object... hashKeys) {
        if (hashKeys != null && hashKeys.length > 0) {
            return redisTemplate.opsForHash().delete(key, hashKeys);
        } else {
            return -1L;
        }
    }

    @Override
    public Object hget(String key, String hashKey) {
        if (key != null && hashKey != null) {
            return redisTemplate.opsForHash().get(key, hashKey);
        } else {
            return null;
        }
    }

    //============================== atomic ===================================

    @Override
    public boolean setnx(String key, Long value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }
    
    @Override
    public String getSet(String key, String value) {
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }

}
