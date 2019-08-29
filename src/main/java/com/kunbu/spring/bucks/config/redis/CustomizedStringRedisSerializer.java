package com.kunbu.spring.bucks.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * 自定义String序列化(即key="age", value=12 会被作为"12")
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-08-29 10:19
 **/
public class CustomizedStringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public CustomizedStringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public CustomizedStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return o == null ? null : String.valueOf(o).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : new String(bytes, charset);
    }
}
