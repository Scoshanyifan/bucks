package com.kunbu.spring.bucks.utils.aop;

import com.alibaba.fastjson.JSONObject;
import com.kunbu.spring.bucks.annotation.ApiNote;
import com.kunbu.spring.bucks.common.entity.mongo.OperateLog;
import com.kunbu.spring.bucks.common.entity.mongo.RequestLog;
import com.kunbu.spring.bucks.common.entity.redis.UserInfo;
import com.kunbu.spring.bucks.constant.CommonConstant;
import com.kunbu.spring.bucks.constant.HttpConstant;
import com.kunbu.spring.bucks.dao.mongodb.LogMongoDB;
import com.kunbu.spring.bucks.dao.redis.RedisManager;
import com.kunbu.spring.bucks.utils.IpUtil;
import com.kunbu.spring.bucks.utils.TokenUtil;
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
    private LogMongoDB logMongoDB;

    @Autowired
    private RedisManager redisManager;

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
        long startTime = System.currentTimeMillis();

        boolean success = false;
        Object result = null;
        try {
            success = true;
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(">>> RequestLogUtil aop proceed error:", e);
            // TODO 邮件通知
        } finally {
            logger.info(">>> RequestLogUtil response result:{}", JSONObject.toJSONString(result));
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();

                Signature signature = joinPoint.getSignature();
                long timeCost = System.currentTimeMillis() - startTime;
                if(timeCost >= CommonConstant.CONTROLLER_CONSUMPTION_MILLIONS) {
                    logger.warn("RequestLogUtil api consume:{} ms, method:{}", timeCost, signature.toShortString());
                }

                RequestLog log = new RequestLog();

                String ip = IpUtil.getIp(request);
                String token = request.getHeader(HttpConstant.HTTP_HEADER_TOKEN);
                String methodName = signature.getName();
                ApiNote note = getApiNote(joinPoint);
                if (StringUtils.isNotBlank(token)) {
                    //redis取用户信息
                    UserInfo userInfo = (UserInfo) redisManager.getObject(token);
                    log.setToken(token);
                    log.setUserId(userInfo.getUid());
                    //身份是admin且执行成功才保存操作日志
                    if (TokenUtil.checkAdmin(token) && success) {
                        saveOperateLog(ip, startTime, userInfo.getUid(), getParams(joinPoint), note);
                    }
                }

                log.setClassName(signature.getDeclaringTypeName());
                log.setMethodName(methodName);
                log.setParameterJson(JSONObject.toJSONString(request.getParameterMap()));
                log.setUrl(request.getRequestURL().toString());
                log.setUserAgent(request.getHeader(HttpConstant.HTTP_HEADER_USER_AGENT));

                log.setCostTime(timeCost);
                log.setDescription(note != null ? note.value() : null);
                log.setCreateTime(new Date(startTime));
                log.setIp(ip);
                // GET POST
                log.setHttpMethod(request.getMethod().toUpperCase());
                // 200 500
                if (success) {
                    log.setHttpStatus(HttpConstant.HTTP_STATUS_OK);
                } else {
                    log.setHttpStatus(HttpConstant.HTTP_STATUS_ERROR);
                }
                logMongoDB.saveRequestLog(log);
            } catch (Exception e) {
                logger.error(">>> RequestLogUtil aop handle error:", e);
            }
        }
        return result;
    }

    /**
     * 保存操作日志
     * @param ip
     * @param startTime
     * @param operatorId
     * @param params
     * @param note
     */
    private void saveOperateLog(String ip, long startTime, String operatorId, String params, ApiNote note) {
        OperateLog log = new OperateLog();
        log.setOperateIp(ip);
        log.setOperateTime(new Date(startTime));
        log.setOperatorId(operatorId);
        log.setParams(params);
        if (note != null) {
            log.setContent(note.value());
            log.setOperateType(note.type().name());
        }
        logMongoDB.saveOperateLog(log);
    }

    /**
     * 接口功能说明
     *
     * @param joinPoint
     * @author kunbu
     * @time 2019/8/26 17:41
     * @return
     **/
    private ApiNote getApiNote(ProceedingJoinPoint joinPoint) {
        Method[] ms = joinPoint.getTarget().getClass().getDeclaredMethods();
        for (Method method : ms) {
            if (method.getName().equals(joinPoint.getSignature().getName())) {
                ApiNote apiNote = method.getAnnotation(ApiNote.class);
                if (apiNote != null) {
                    return apiNote;
                }
            }
        }
        return null;
    }

    /**
     * 获取接口参数
     *
     * @param joinPoint
     * @author kunbu
     * @time 2019/9/2 14:24
     * @return
     **/
    private String getParams(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            str.append(args[i] + ";");
        }
        return str.toString();
    }

}
