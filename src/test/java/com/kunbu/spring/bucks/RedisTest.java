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

        Integer uid_1 = 1;
        Integer uid_2 = 2;
        Integer uid_3 = 3;

        redisManager.setBit(bitmapKey, uid_1, true);
        redisManager.setBit(bitmapKey, uid_2, true);
        redisManager.setBit(bitmapKey, uid_3, true);

        Long bitCount = redisManager.bitCount(bitmapKey, 0, 1); // 3
        logger.info(">>> {}: {}", date, bitCount);

        logger.info(">>> null:{}", redisManager.bitCount("null"));
        logger.info(">>> null:{}", redisManager.getBit("bitmapKey", 2333));
        logger.info(">>> null:{}", redisManager.getBit("null", 2333));
    }

}
