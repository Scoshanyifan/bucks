package com.kunbu.spring.bucks.common.dto;

import java.io.Serializable;

/**
 * @program: bucks
 * @description: 商品类别DTO
 * @author: kunbu
 * @create: 2019-08-16 16:25
 **/
public class CategoryDTO implements Serializable {

    private String categoryId;
    private String categoryName;
    private String parentId;
    private Byte level;
    private String operatorId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level=" + level +
                ", operatorId='" + operatorId + '\'' +
                '}';
    }
}
