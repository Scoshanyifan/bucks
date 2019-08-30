package com.kunbu.spring.bucks.constant.other;

/**
 * @author: KunBu
 * @time: 2019/8/7 11:02
 * @description: 日志类型
 */
public enum LogTypeEnum {

    //
    EXECUTOR("Executor"),
    METHOD_CONSUME("Method-consume"),

    ;

    LogTypeEnum(String name){
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
