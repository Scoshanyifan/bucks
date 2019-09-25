package com.kunbu.spring.bucks;

import com.google.common.base.Splitter;

import java.util.Arrays;
import java.util.List;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-23 16:57
 **/
public class StringTest {

    public static void main(String[] args) {

        //如果是：5,//// 然后使用String.split，会有bug，不能正确划分，会出现空数据情况（改用guava的Splitter）
        testSplit("////");
        testSplit("2/-5///");
    }

    public static void testSplit(String historyTemp) {
        //String.split()
        String[] tempStrArr = historyTemp.split("/");
        System.out.println("String.split(): " + Arrays.toString(tempStrArr));

        //guava.Splitter
        List<String> tempStrList = Splitter.on("/").splitToList(historyTemp);
        System.out.println("Splitter: " + tempStrList);
    }


}
