package com.kunbu.spring.bucks.common.entity;

import java.io.Serializable;

/**
 * order
 * @author 
 */
public class OrderEntity extends BaseEntity implements Serializable {

    /**
     * 订单类型：
     */
    private String orderType;

    private String serviceId;

    private String userId;

    private Long totalAmount;

    /**
     * 额外信息
     */
    private String ext;

    private String state;

    private static final long serialVersionUID = 1L;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
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
        return "OrderEntity{" +
                "orderType='" + orderType + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", totalAmount=" + totalAmount +
                ", ext='" + ext + '\'' +
                ", state='" + state + '\'' +
                "} " + super.toString();
    }
}