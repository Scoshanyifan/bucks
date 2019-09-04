package com.kunbu.spring.bucks;

import com.kunbu.spring.bucks.dao.redis.RedisManager;
import com.kunbu.spring.bucks.utils.DateFormatUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 09:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("test")
//@TestPropertySource("classpath:application.properties")
public class RedisTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisManager redisManager;

    @Test
    public void testBitmap() {

        String keyFormat = "user_online_%s";
        String date = DateFormatUtil.format(new Date(), DateFormatUtil.DATE_PATTERN_1);
        String bitmapKey = String.format(keyFormat, date);

        Integer uid_1 = 123456001;
        Integer uid_2 = 123456002;

        redisManager.setBit(bitmapKey, uid_1, true);
        redisManager.setBit(bitmapKey, uid_2, true);

        Long bitCount = redisManager.bitCount(bitmapKey);
        logger.info(">>> {}: {}", date, bitCount);
    }

}
