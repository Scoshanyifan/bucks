package com.kunbu.spring.bucks.error.bis;

import com.kunbu.spring.bucks.error.MicroServiceError;

/**
 * @author: KunBu
 * @time: 2019/8/16 17:31
 * @description:
 */
public enum CategoryErrorEnum implements MicroServiceError {

    //
    CATEGORY_NAME_EXIST("商品类目名已存在"),
    CATEGORY_CODE_EXIST("商品类目编号已存在"),
    CATEGORY_NULL("商品类目不存在"),
    CATEGORY_SAVE_ERROR("保存类目树失败，原因：%s"),

    ;

    private String msg;

    CategoryErrorEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String getServiceErrorCode() {
        return name();
    }

    @Override
    public String getServiceErrorMsg() {
        return msg;
    }
}
