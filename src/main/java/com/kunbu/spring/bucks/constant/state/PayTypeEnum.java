package com.kunbu.spring.bucks.constant.state;

/**
 * @author: KunBu
 * @time: 2019/8/23 14:30
 * @description:
 */
public enum PayTypeEnum {
    //

    ALI("支付宝"),
    WX("微信支付"),


    ;

    private String value;

    PayTypeEnum(String value) {
        this.value = value;
    }
}
