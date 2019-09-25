package com.kunbu.spring.bucks.manage;

import com.kunbu.spring.bucks.common.entity.redis.UserInfo;

import java.util.Date;
import java.util.Map;

/**
 * @author: KunBu
 * @time: 2019/9/4 16:43
 * @description:
 */
public interface UserStatisticManage {

    /**
     * 记录每日活跃用户
     *
     * @param userInfo
     * @return
     * @author kunbu
     * @time 2019/9/4 16:55
     **/
    void saveDayActive(UserInfo userInfo);

    /**
     * 统计每日活跃用户情况
     *
     * @param start
     * @param end
     * @return
     * @author kunbu
     * @time 2019/9/4 16:56
     **/
    Map<String, Object> statDayActive(Date start, Date end);

    /**
     * 记录单个用户日活跃
     *
     * @param userInfo
     * @return
     * @author kunbu
     * @time 2019/9/4 16:54
     **/
    void saveUserDayActive(UserInfo userInfo);

    /**
     * 统计单个用户日活跃情况
     *
     * @return
     * @author kunbu
     * @time 2019/9/4 16:48
     **/
    Map<String, Object> statUserDayActive(UserInfo userInfo, Date start, Date end);

    /**
     * 记录单个用户签到
     *
     * @param userInfo
     * @return
     * @author kunbu
     * @time 2019/9/4 16:54
     **/
    void saveUserSign(UserInfo userInfo);

    /**
     * 统计单个用户签到情况
     *
     * @param userInfo
     * @param start
     * @param end
     * @return
     * @author kunbu
     * @time 2019/9/4 16:53
     **/
    Map<String, Object> statUserSign(UserInfo userInfo, Date start, Date end);

}
