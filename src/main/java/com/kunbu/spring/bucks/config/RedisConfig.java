package com.kunbu.spring.bucks.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: bucks
 * @description: http://spring.hhui.top/spring-blog/2018/11/01/181101-SpringBoot高级篇Redis之Jedis配置/
 * @author: kunbu
 * @create: 2019-08-26 17:57
 **/
@Configuration
public class RedisConfig {

    /**
     * 自定义RedisConnectionFactory为Jedis
     *
     * @param singleConfig
     * @param poolConfig
     * @author kunbu
     * @time 2019/8/27 13:31
     * @return
     **/
//    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration singleConfig, JedisPoolConfig poolConfig) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(singleConfig);
        connectionFactory.setPoolConfig(poolConfig);
        return connectionFactory;
    }

    /**
     * 泛型为<String,Object>形式的RedisTemplate
     * https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
     * 
     * @param factory
     * @author kunbu
     * @time 2019/8/26 18:04
     * @return
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //json
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //String
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

}
