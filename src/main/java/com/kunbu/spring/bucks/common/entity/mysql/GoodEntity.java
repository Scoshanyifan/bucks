package com.kunbu.spring.bucks.common.entity.mysql;

import com.kunbu.spring.bucks.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * good
 *
 * @author
 */
public class GoodEntity extends BaseEntity implements Serializable {

    private String goodName;

    private String description;

    /**
     * 主图url
     */
    private String mainUrl;

    private String categoryId;

    private Long price;

    private Long actualPrice;

    private String currency;

    private String state;

    private String operatorId;

    private static final long serialVersionUID = 1L;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Long actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
        return "GoodEntity{" +
                "goodName='" + goodName + '\'' +
                ", description='" + description + '\'' +
                ", mainUrl='" + mainUrl + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", price=" + price +
                ", actualPrice=" + actualPrice +
                ", currency='" + currency + '\'' +
                ", biz='" + state + '\'' +
                ", operatorId='" + operatorId + '\'' +
                "} " + super.toString();
    }
}