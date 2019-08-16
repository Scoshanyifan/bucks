package com.kunbu.spring.bucks.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * pay_order
 * @author 
 */
public class PayOrderEntity extends BaseEntity implements Serializable {

    private String orderId;

    /**
     * 交易类型
     */
    private String userId;

    private String tradeType;

    /**
     * 支付类型：ALI, WX
     */
    private String payType;

    /**
     * 第三方交易号
     */
    private String outTradeNo;

    private Long amount;

    private Date payTime;

    /**
     * 支付状态
     */
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PayOrderEntity{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", payType='" + payType + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", amount=" + amount +
                ", payTime=" + payTime +
                ", state='" + state + '\'' +
                "} " + super.toString();
    }
}