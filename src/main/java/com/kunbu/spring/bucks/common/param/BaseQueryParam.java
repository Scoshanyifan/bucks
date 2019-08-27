package com.kunbu.spring.bucks.common.param;

import java.io.Serializable;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-27 16:30
 **/
public abstract class BaseQueryParam implements Serializable {

    private int pageNum = 1;
    private int pageSize = 10;

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

    @Override
    public String toString() {
        return "BaseQueryParam{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
