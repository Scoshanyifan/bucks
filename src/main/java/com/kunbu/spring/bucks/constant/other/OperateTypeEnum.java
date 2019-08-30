package com.kunbu.spring.bucks.constant.other;

/**
 * @author: KunBu
 * @time: 2019/8/30 16:54
 * @description:
 */
public enum OperateTypeEnum {
    //

    ADD("新增"),
    DELETE("删除"),
    MODIFY("修改"),
    QUERY("查询"),
    OTHER("其他"),
    ;

    private String value;

    OperateTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
