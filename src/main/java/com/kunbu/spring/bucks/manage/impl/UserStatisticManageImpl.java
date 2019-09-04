package com.kunbu.spring.bucks.manage.impl;

import com.google.common.collect.Maps;
import com.kunbu.spring.bucks.common.entity.redis.UserInfo;
import com.kunbu.spring.bucks.constant.CacheConstant;
import com.kunbu.spring.bucks.dao.redis.RedisManager;
import com.kunbu.spring.bucks.manage.UserStatisticManage;
import com.kunbu.spring.bucks.utils.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 16:43
 **/
@Service
public class UserStatisticManageImpl implements UserStatisticManage {

    private static final Logger logger = LoggerFactory.getLogger(UserStatisticManageImpl.class);

    @Autowired
    private RedisManager redisManager;

    @Override
    public void saveDayActive(UserInfo userInfo) {
        String today = DateFormatUtil.format(new Date(), DateFormatUtil.DATE_PATTERN_8);
        String dayActiveKey = CacheConstant.CACHE_KEY_STAT_ALL_ACTIVE + today;
        boolean saveResult = redisManager.setBit(dayActiveKey, userInfo.getUserId().intValue(), true);
        if (!saveResult) {
            logger.error(">>> UserStatistic saveDayActive error, uid:{}, date:{}", userInfo.getUserId(), today);
        }
    }

    @Override
    public Map<String, Object> statDayActive(Date start, Date end) {
        Map<String, Object> dayActiveMap = Maps.newHashMap();

        long startLong = start.getTime();
        long endLong = end.getTime();
        long interval = (endLong - startLong) / 24 / 3600 / 1000L;
        logger.info(">>> statDayActive start:{}, end:{}, interval:{}", start, end, interval);
        for (int i = 0; i < interval; i++) {
            Date date = new Date(startLong + i * 24 * 3600 * 1000L);
            String day = DateFormatUtil.format(date, DateFormatUtil.DATE_PATTERN_8);
            String dayActiveKey = CacheConstant.CACHE_KEY_STAT_ALL_ACTIVE + day;
            Long count = redisManager.bitCount(dayActiveKey);
            dayActiveMap.put(day, count);
        }
        return dayActiveMap;
    }

    @Override
    public void saveUserDayActive(UserInfo userInfo) {
        String today = DateFormatUtil.format(new Date(), DateFormatUtil.DATE_PATTERN_8);
        String userDayActiveKey = CacheConstant.CACHE_KEY_STAT_USER_ACTIVE + userInfo.getUserId();
        boolean saveResult = redisManager.setBit(userDayActiveKey, Integer.parseInt(today), true);
        if (!saveResult) {
            logger.error(">>> UserStatistic saveUserDayActive error, uid:{}", userInfo.getUserId());
        }
    }

    @Override
    public Map<String, Object> statUserDayActive(UserInfo userInfo, Date start, Date end) {
        String userDayActiveKey = CacheConstant.CACHE_KEY_STAT_USER_ACTIVE + userInfo.getUserId();

        return getBitValueMap(userDayActiveKey, start, end);
    }

    @Override
    public void saveUserSign(UserInfo userInfo) {
        String today = DateFormatUtil.format(new Date(), DateFormatUtil.DATE_PATTERN_8);
        String userDayActiveKey = CacheConstant.CACHE_KEY_STAT_USER_SIGN + userInfo.getUserId();
        boolean saveResult = redisManager.setBit(userDayActiveKey, Integer.parseInt(today), true);
        if (!saveResult) {
            logger.error(">>> UserStatistic saveUserSign error, uid:{}", userInfo.getUserId());
        }
    }

    @Override
    public Map<String, Object> statUserSign(UserInfo userInfo, Date start, Date end) {
        String userSignKey = CacheConstant.CACHE_KEY_STAT_USER_SIGN + userInfo.getUserId();
        
        return getBitValueMap(userSignKey, start, end);
    }

    private Map<String, Object> getBitValueMap(String key, Date start, Date end) {
        Map<String, Object> bitMap = Maps.newHashMap();

        long startLong = start.getTime();
        long endLong = end.getTime();
        long interval = (endLong - startLong) / 24 / 3600 / 1000L;
        logger.info(">>>{}, start:{}, end:{}, interval:{}", key, start, end, interval);
        for (int i = 0; i < interval; i++) {
            Date date = new Date(startLong + i * 24 * 3600 * 1000L);
            String day = DateFormatUtil.format(date, DateFormatUtil.DATE_PATTERN_8);
            boolean ifActive = redisManager.getBit(key, Integer.parseInt(day));
            if (ifActive) {
                bitMap.put(day, 1);
            }
        }
        return bitMap;
    }
}
