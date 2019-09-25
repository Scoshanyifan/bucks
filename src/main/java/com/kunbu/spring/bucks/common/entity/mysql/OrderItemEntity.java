package com.kunbu.spring.bucks.common.entity.mysql;

import com.kunbu.spring.bucks.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * order_item
 *
 * @author
 */
public class OrderItemEntity extends BaseEntity implements Serializable {

    private String orderId;

    private String userId;

    private String goodId;

    private String goodName;

    private Long number;

    /**
     * 单位（默认件）
     */
    private String unitId;

    private Long price;

    private Long totalPrice;

    private String ext;

    private String state;

    private static final long serialVersionUID = 1L;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", goodId='" + goodId + '\'' +
                ", goodName='" + goodName + '\'' +
                ", number=" + number +
                ", unitId='" + unitId + '\'' +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", ext='" + ext + '\'' +
                ", biz='" + state + '\'' +
                "} " + super.toString();
    }
}