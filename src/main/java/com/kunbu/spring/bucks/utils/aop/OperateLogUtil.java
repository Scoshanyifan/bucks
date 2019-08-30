package com.kunbu.spring.bucks.utils.aop;

import com.kunbu.spring.bucks.annotation.ApiNote;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-08-30 17:12
 **/
@Component
@Aspect
public class OperateLogUtil {



    /**
     * 接口说明
     *
     * @param log
     * @param joinPoint
     * @author kunbu
     * @time 2019/8/26 17:41
     * @return
     **/
    private void fillUpApiNote(RequestLog log, ProceedingJoinPoint joinPoint) {
        Method[] ms = joinPoint.getTarget().getClass().getDeclaredMethods();
        for (Method method : ms) {
            if (method.getName().equals(joinPoint.getSignature().getName())) {
                ApiNote apiNote = method.getAnnotation(ApiNote.class);
                if (apiNote != null) {
                    log.setDescription(apiNote.value());
                }
            }
        }
    }

}
