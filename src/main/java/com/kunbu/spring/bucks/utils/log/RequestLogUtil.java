package com.kunbu.spring.bucks.utils.log;

import com.alibaba.fastjson.JSONObject;
import com.kunbu.spring.bucks.annotation.ApiNote;
import com.kunbu.spring.bucks.common.mongo.RequestLog;
import com.kunbu.spring.bucks.constant.cons.CommonConstant;
import com.kunbu.spring.bucks.constant.cons.HttpConstant;
import com.kunbu.spring.bucks.mongodb.RequestLogMongo;
import com.kunbu.spring.bucks.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @program: spring-practice
 * @description: 请求日志工具类
 * @author: kunbu
 * @create: 2019-08-03 15:53
 **/
@Component
@Aspect
public class RequestLogUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogUtil.class);

    @Autowired
    private RequestLogMongo requestLogMongo;

    /**
     * * com.kunbu.spring.controller..*.*(..) 表示controller包以及其下子包中的类，类中的所有方法
     * * com.kunbu.spring.controller.*.*(..)) 则仅表示controller包下的类
     *
     * 第一个*表示任意返回类型，修饰符可省略
     * ..表示0或多个，即所有，controller后面的..表示到此路径为止
     * ..*.中的*表示一个单词，即任意类，后面的.是包名的.
     * ..*.*(..)中第二个*表示任意方法，(..)表示方法中任意参数
     **/
    @Pointcut("execution(* com.kunbu.spring.bucks.controller..*.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object requestLog(ProceedingJoinPoint joinPoint) {
        RequestLog log = new RequestLog();
        long startTime = System.currentTimeMillis();
        log.setCreateTime(new Date(startTime));

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.setHttpStatus(HttpConstant.HTTP_STATUS_ERROR);
            logger.error(">>> RequestLogUtil aop proceed error:", e);
            // TODO 邮件通知
        } finally {
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();

                Signature signature = joinPoint.getSignature();
                long timeCost = System.currentTimeMillis() - startTime;
                if(timeCost >= CommonConstant.CONTROLLER_CONSUMPTION_MILLIONS) {
                    logger.warn("RequestLogUtil api consume:{} ms, method:{}", timeCost, signature.toShortString());
                }
                log.setClassName(signature.getDeclaringTypeName());
                log.setMethodName(signature.getName());
                log.setParameterJson(JSONObject.toJSONString(request.getParameterMap()));
                log.setCostTime(timeCost);
                fillUpApiNote(log, joinPoint);

                log.setUserAgent(request.getHeader(HttpConstant.HTTP_HEADER_USER_AGENT));
                log.setUrl(request.getRequestURL().toString());
                log.setIp(IpUtil.getIp(request));
                // GET POST
                log.setHttpMethod(request.getMethod().toUpperCase());
                if (log.getHttpStatus() == null) {
                    log.setHttpStatus(HttpConstant.HTTP_STATUS_OK);
                }
                String token = request.getHeader(HttpConstant.HTTP_HEADER_TOKEN);
                if (StringUtils.isNotBlank(token)) {
                    log.setToken(token);
                    //TODO redis取用户信息
                    log.setUserId(null);
                }
                logger.info(">>> RequestLogUtil responseResult:{}", JSONObject.toJSONString(result));
                requestLogMongo.save(log);
            } catch (Exception e) {
                logger.error(">>> RequestLogUtil aop handle error:", e);
            }
        }
        return result;
    }

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