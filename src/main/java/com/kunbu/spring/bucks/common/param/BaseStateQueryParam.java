package com.kunbu.spring.bucks.common.param;

/**
 * @program: bucks
 * @description: 查询基类
 * @author: kunbu
 * @create: 2019-08-16 15:32
 **/
public abstract class BaseStateQueryParam extends BaseQueryParam {

    private String state;
    private String exceptState;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExceptState() {
        return exceptState;
    }

    public void setExceptState(String exceptState) {
        this.exceptState = exceptState;
    }

    @Override
    public String toString() {
        return "BaseStateQueryParam{" +
                "biz='" + state + '\'' +
                ", exceptState='" + exceptState + '\'' +
                "} " + super.toString();
    }
}
