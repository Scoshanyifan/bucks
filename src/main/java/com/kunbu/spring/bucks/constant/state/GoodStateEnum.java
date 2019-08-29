package com.kunbu.spring.bucks.constant.state;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: KunBu
 * @time: 2019/8/16 16:32
 * @description:
 */
public enum GoodStateEnum {

    //
    ON("已上架"),
    OFF("已下架"),
    PRIVATE("私密"),
    DELETE("已删除"),


    ;

    private String msg;

    GoodStateEnum(String msg) {
        this.msg = msg;
    }

    public static String getStateMsg(String stateName) {
        if (StringUtils.isNotBlank(stateName)) {
            for (GoodStateEnum e : values()) {
                if (e.name().equals(stateName)) {
                    return e.msg;
                }
            }
        }
        return null;
    }
}
