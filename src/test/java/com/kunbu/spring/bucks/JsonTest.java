package com.kunbu.spring.bucks;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.kunbu.spring.bucks.common.entity.mysql.GoodEntity;

import java.util.List;

/**
 * https://blog.51cto.com/xiaok007/2174054
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-23 16:57
 **/
public class JsonTest {

    public static void main(String[] args) {

        GoodEntity g1 = new GoodEntity();
        g1.setId("5fwo12ew100321ds");
        g1.setGoodName("apple");
        GoodEntity g2 = new GoodEntity();
        g2.setId("8u7b76hd07hd2fcd");
        g2.setGoodName("orange");

        List<GoodEntity> goods = Lists.newArrayList(g1, g2);

        JSONObject data = new JSONObject();
        data.put("goods", goods);

        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", data);

        System.out.println(result.toJSONString());
    }

}
