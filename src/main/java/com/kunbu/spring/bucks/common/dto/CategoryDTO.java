package com.kunbu.spring.bucks.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @program: bucks
 * @description: 商品类别DTO
 * @author: kunbu
 * @create: 2019-08-16 16:25
 **/
public class CategoryDTO implements Serializable {

    private String categoryId;
    private Integer categoryCode;
    private String categoryName;
    private String parentId;
    private int level;

    private List<CategoryDTO> subs;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<CategoryDTO> getSubs() {
        return subs;
    }

    public void setSubs(List<CategoryDTO> subs) {
        this.subs = subs;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level=" + level +
                ", subs=" + subs +
                '}';
    }
}
