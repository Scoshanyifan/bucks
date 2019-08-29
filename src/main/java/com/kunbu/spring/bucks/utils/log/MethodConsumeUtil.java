package com.kunbu.spring.bucks.utils.log;

import com.kunbu.spring.bucks.constant.CommonConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @program: spring-practice
 * @description: 统计业务处理时长
 * @author: kunbu
 * @create: 2019-08-07 11:28
 **/
@Component
@Aspect
public class MethodConsumeUtil {

    private static final Logger logger = LoggerUtil.getMethodConsumeLogger();

    @Pointcut("execution(* com.kunbu.spring.bucks.service..*.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object methodConsume(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(">>> method consume aop error:", e);
        } finally {
            long timeCost = System.currentTimeMillis() - startTime;
            if(timeCost >= CommonConstant.SERVICE_CONSUMPTION_MILLIONS) {
                logger.warn("consume:{} ms, method:{}", timeCost, joinPoint.getSignature().toShortString());
            }
        }
        return null;
    }

}
