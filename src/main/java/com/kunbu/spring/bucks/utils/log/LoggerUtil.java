package com.kunbu.spring.bucks.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: spring-practice
 * @description: 日志工厂
 * @author: kunbu
 * @create: 2019-08-07 10:57
 **/
public class LoggerUtil {

    /**
     * 获取线程池任务的日志管理器
     *
     * @param
     * @author kunbu
     * @time 2019/8/19 10:01
     * @return
     **/
    public static Logger getExecutorLogger() {
        return LoggerFactory.getLogger(LogEnum.EXECUTOR.getName());
    }

    /**
     * 获取方法时间统计的日志管理器
     *
     * @param
     * @author kunbu
     * @time 2019/8/19 10:01
     * @return
     **/
    public static Logger getMethodConsumeLogger() {
        return LoggerFactory.getLogger(LogEnum.METHOD_CONSUME.getName());
    }
}
