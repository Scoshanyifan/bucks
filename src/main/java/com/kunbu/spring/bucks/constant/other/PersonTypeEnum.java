package com.kunbu.spring.bucks.constant.other;

public enum PersonTypeEnum {
    //

    USER("普通用户"),
    ADMIN("管理员"),


    ;

    private String value;

    PersonTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
