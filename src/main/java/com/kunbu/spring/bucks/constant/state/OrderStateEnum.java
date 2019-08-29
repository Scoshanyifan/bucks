package com.kunbu.spring.bucks.constant.state;

/**
 * @author: KunBu
 * @time: 2019/8/16 13:32
 * @description:
 *
 * 1.1 下单已支付确认：CREATED -> PAYED -> FINISHED
 * 1.2 下单已支付超时：CREATED -> PAYED -> FINISHED
 *
 * 2 下单未支付取消：CREATED -> CANCELED
 * 3 下单未支付超时：CREATED -> CLOSED
 * 4 下单已支付已完成退款：CREATED -> PAYED -> FINISHED -> REFUNDED
 *
 */
public enum OrderStateEnum {

    // 已创建（订单创建完成）
    CREATED,
    // 已支付（用户等待收获）
    PAYED,

    // 已完成（用户主动确认或系统15天自动确认）
    FINISHED,
    // 已取消（用户未付款主动触发）
    CANCELED,
    // 已关闭（用户未付款超时，系统30分钟自动关闭）
    CLOSED,
    // 已退款（订单退款完成）
    REFUNDED,


    ;


}
