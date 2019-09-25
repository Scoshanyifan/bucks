package com.kunbu.spring.bucks.common.entity.mysql;

import com.kunbu.spring.bucks.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * category
 *
 * @author
 */
public class CategoryEntity extends BaseEntity implements Serializable {

    private Integer categoryCode;

    private String categoryName;

    private String parentId;

    /**
     * 类目级别
     */
    private Byte level;

    private String state;

    private String operatorId;

    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level=" + level +
                ", biz='" + state + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", version=" + version +
                "} " + super.toString();
    }
}