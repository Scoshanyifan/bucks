package com.kunbu.spring.bucks.common.entity;

import java.io.Serializable;

/**
 * category
 * @author 
 */
public class CategoryEntity extends BaseEntity implements Serializable {

    private String categoryName;

    private String parentId;

    /**
     * 类目级别
     */
    private Byte level;

    private String state;

    private String operatorId;

    private static final long serialVersionUID = 1L;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "categoryName='" + categoryName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level=" + level +
                ", state='" + state + '\'' +
                ", operatorId='" + operatorId + '\'' +
                "} " + super.toString();
    }
}