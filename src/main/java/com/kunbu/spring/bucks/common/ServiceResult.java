package com.kunbu.spring.bucks.common;

import com.kunbu.spring.bucks.error.MicroServiceError;

import java.io.Serializable;

/**
 * @program: bucks
 * @description: 服务数据
 * @author: kunbu
 * @create: 2019-08-16 15:22
 **/
public class ServiceResult<T> implements Serializable {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_ERROR = 0;

    public static final ServiceResult SUCCESS = SUCCESS();

    private T data;
    private int code;
    private MicroServiceError serviceError;
    @Deprecated
    private String msg;

    private ServiceResult() {}

    private static ServiceResult SUCCESS() {
        ServiceResult result = new ServiceResult();
        result.setCode(CODE_SUCCESS);
        return result;
    }

    public static ServiceResult SUCCESS(Object data) {
        ServiceResult result = new ServiceResult();
        result.setData(data);
        result.setCode(CODE_SUCCESS);
        return result;
    }

    @Deprecated
    public static ServiceResult ERROR(String msg) {
        ServiceResult result = new ServiceResult();
        result.setCode(CODE_ERROR);
        result.setMsg(msg);
        return result;
    }

    public static ServiceResult ERROR(MicroServiceError serviceError) {
        ServiceResult result = new ServiceResult();
        result.setCode(CODE_ERROR);
        result.setServiceError(serviceError);
        return result;
    }

    public MicroServiceError getServiceError() {
        return serviceError;
    }

    public void setServiceError(MicroServiceError serviceError) {
        this.serviceError = serviceError;
    }

    public boolean ok() {
        return code == CODE_SUCCESS;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Deprecated
    public String getMsg() {
        return msg;
    }

    @Deprecated
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
