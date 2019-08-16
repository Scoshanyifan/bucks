package com.kunbu.spring.bucks.common.param;

/**
 * @program: bucks
 * @description: 查询基类
 * @author: kunbu
 * @create: 2019-08-16 15:32
 **/
public abstract class BaseQueryParam {

    private String state;
    private String exceptState;
    private int pageNum;
    private int pageSize;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getExceptState() {
        return exceptState;
    }

    public void setExceptState(String exceptState) {
        this.exceptState = exceptState;
    }

    @Override
    public String toString() {
        return "BaseQueryParam{" +
                "state='" + state + '\'' +
                ", exceptState='" + exceptState + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
