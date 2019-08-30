package com.kunbu.spring.bucks.common.param.mongo;

import com.kunbu.spring.bucks.common.param.BaseQueryParam;

import java.util.Date;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-27 16:46
 **/
public class RequestLogQueryParam extends BaseQueryParam {

    private String classNameRegex;
    private String methodNameRegex;

    private String httpMethod;
    private String httpStatus;
    private String ipRegex;
    private String urlRegex;
    private String userId;

    private Long costTimeMin;
    private Long costTimeMax;
    private Date startTime;
    private Date endTime;

    public String getClassNameRegex() {
        return classNameRegex;
    }

    public void setClassNameRegex(String classNameRegex) {
        this.classNameRegex = classNameRegex;
    }

    public String getMethodNameRegex() {
        return methodNameRegex;
    }

    public void setMethodNameRegex(String methodNameRegex) {
        this.methodNameRegex = methodNameRegex;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getIpRegex() {
        return ipRegex;
    }

    public void setIpRegex(String ipRegex) {
        this.ipRegex = ipRegex;
    }

    public String getUrlRegex() {
        return urlRegex;
    }

    public void setUrlRegex(String urlRegex) {
        this.urlRegex = urlRegex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCostTimeMin() {
        return costTimeMin;
    }

    public void setCostTimeMin(Long costTimeMin) {
        this.costTimeMin = costTimeMin;
    }

    public Long getCostTimeMax() {
        return costTimeMax;
    }

    public void setCostTimeMax(Long costTimeMax) {
        this.costTimeMax = costTimeMax;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "RequestLogQueryParam{" +
                "classNameRegex='" + classNameRegex + '\'' +
                ", methodNameRegex='" + methodNameRegex + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", httpStatus='" + httpStatus + '\'' +
                ", ipRegex='" + ipRegex + '\'' +
                ", urlRegex='" + urlRegex + '\'' +
                ", userId='" + userId + '\'' +
                ", costTimeMin=" + costTimeMin +
                ", costTimeMax=" + costTimeMax +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "} " + super.toString();
    }
}
