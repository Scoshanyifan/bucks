package com.kunbu.spring.bucks.common.entity;

import java.util.Date;

/**
 * @program: bucks
 * @description: 实体基类
 * @author: kunbu
 * @create: 2019-08-16 13:26
 **/
public abstract class BaseEntity {

    private String id;
    private Date createTime;
    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
