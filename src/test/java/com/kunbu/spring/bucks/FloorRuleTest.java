package com.kunbu.spring.bucks;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-16 14:18
 **/
public class FloorRuleTest {

    public static void main(String[] args) {

        checkFloorRule("-2,-1,1:7");
    }

    public static void checkFloorRule(String ruleText) {
        List<String> floorNameList = Lists.newArrayList();
        try {
            floorNameList = convertFloor(ruleText);
        } catch (Exception e) {
            System.err.println(">>> 转换楼层规则异常." + e);
        }
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < floorNameList.size(); i++) {
            map.put(floorNameList.get(i), i);
        }
        System.out.println(JSONObject.toJSON(map));
    }

    /**
     * 楼层规则转换：
     * B2,B1,1:6 >>> [B2, B1, 1, 2, 3, 4, 5, 6]
     *
     * @param ruleText
     * @return
     * @author kunbu
     * @time 2019/9/3 10:26
     **/
    private static List<String> convertFloor(String ruleText) {
        //顺序保存
        List<String> floorNameList = new ArrayList<>();
        String[] items = ruleText.split(",|，");

        for (String item : items) {
            //处理 1:5 / -3:-1 （错误：-1:3）
            if (item.indexOf(":") > 0 || item.indexOf("：") > 0) {
                String[] startEnd = item.split(":|：");
                if (startEnd.length != 2) {
                    throw new RuntimeException("组合楼层，参数缺失");
                }
                int start;
                int end;
                try {
                    start = Integer.parseInt(startEnd[0]);
                    end = Integer.parseInt(startEnd[1]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("组合楼层，参数须为数字");
                }
                if (start == 0 || end == 0) {
                    throw new RuntimeException("组合楼层，不能出现0层");
                }
                if (start >= end) {
                    throw new RuntimeException("组合楼层，首尾层数有误");
                }
                if (start < 0 && end > 0) {
                    throw new RuntimeException("组合楼层，不能同时包含正负楼层");
                }
                for (int i = start; i < end + 1; i++) {
                    floorNameList.add(Integer.toString(i));
                }
                continue;
            }
            //普通item：B2 / F1 / 0
            floorNameList.add(item);
        }
        return floorNameList;
    }

}
