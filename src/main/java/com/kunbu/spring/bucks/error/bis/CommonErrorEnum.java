package com.kunbu.spring.bucks.error.bis;

import com.kunbu.spring.bucks.error.ApiError;

/**
 * @author: KunBu
 * @time: 2019/8/3 16:42
 * @description:
 */
public enum CommonErrorEnum implements ApiError {
    //
    PARAM_ILLEGAL("参数错误"),
    SYSTEM_BUSY("系统繁忙"),
    OPERATION_ILLEGAL("操作过于频繁"),
    TOKEN_EXPIRE("请重新登入"),


    ;

    private String value;

    CommonErrorEnum(String value) {
        this.value = value;
    }

    @Override
    public String getErrorCode() {
        return name();
    }

    @Override
    public String getErrorMsg() {
        return value;
    }
}
