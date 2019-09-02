package com.kunbu.spring.bucks.constant.other;

/**
 * @author: KunBu
 * @time: 2019/8/7 11:02
 * @description: 日志类型
 */
public enum SysLogTypeEnum {

    //
    EXECUTOR("Executor"),
    METHOD_CONSUME("Method-consume"),

    ;

    SysLogTypeEnum(String name){
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
