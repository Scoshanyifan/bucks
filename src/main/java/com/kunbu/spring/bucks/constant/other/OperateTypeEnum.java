package com.kunbu.spring.bucks.constant.other;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Deprecated
    private static Map<OperateTypeEnum, List<String>> operateType2MethodName;

    static {
        operateType2MethodName = new HashMap<>();
        operateType2MethodName.put(ADD, Lists.newArrayList("add", "save", "new"));
        operateType2MethodName.put(DELETE, Lists.newArrayList("delete", "del", "remove"));
        operateType2MethodName.put(MODIFY, Lists.newArrayList("modify", "mdf", "edit"));
        operateType2MethodName.put(QUERY, Lists.newArrayList("query", "get", "list"));
    }

    private String value;

    OperateTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Deprecated
    public static OperateTypeEnum getOperateType(String methodName) {
        String lowMethodName = methodName.toLowerCase();
        for (Map.Entry<OperateTypeEnum, List<String>> en : operateType2MethodName.entrySet()) {
            for (String operate : en.getValue()) {
                if (lowMethodName.indexOf(operate) > 0) {
                    return en.getKey();
                }
            }
        }
        return OTHER;
    }
}
