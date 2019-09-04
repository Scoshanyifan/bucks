package com.kunbu.spring.bucks;

import com.kunbu.spring.bucks.dao.redis.RedisManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 09:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisManager redisManager;

    @Test
    public void testBitmap() {

        String bitmapKey = "user_online_%s";

    }

}
