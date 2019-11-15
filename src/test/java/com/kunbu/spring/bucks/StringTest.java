package com.kunbu.spring.bucks;

import com.google.common.base.Splitter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * https://blog.51cto.com/xiaok007/2174054
 *
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-23 16:57
 **/
public class StringTest {

    public static void main(String[] args) {

        testSplit("////");
        testSplit("2/-5///");

        System.out.println();
        testStringFormat();

        System.out.println();
        testMessageFormat();
    }

    /**
     * 如果是：//// 然后使用String.split，会有bug，不能正确划分，会出现空数据情况（改用guava的Splitter）
     *
     **/
    public static void testSplit(String historyTemp) {
        //String.split()
        String[] tempStrArr = historyTemp.split("/");
        System.out.println("String.split(): " + Arrays.toString(tempStrArr));

        //guava.Splitter
        List<String> tempStrList = Splitter.on("/").splitToList(historyTemp);
        System.out.println("Splitter: " + tempStrList);
    }


    /**
     * 字符串：%s（%[idx]$[m]s，[idx]表示用第一个参数填充，[m]表示此处填充几个空格）
     * 浮点数：%f（%[index$][标识][最少宽度][.精度]转换方式）
     *
     *
     * https://blog.csdn.net/yaerfeng/article/details/7328092
     **/
    public static void testStringFormat() {
        /**
         * '-'    在最小宽度内左对齐，不可以与“用0填充”同时使用
         * '#'    只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x
         * '+'    结果总是包括一个符号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
         * ' '    正值前加空格，负值前加负号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
         * '0'    结果将用零来填充
         * ','    只适用于10进制，每3位数字之间用“，”分隔
         * '('    若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（同‘+’具有同样的限制）
         *
         **/
        System.out.println("num：" + String.format("%06d", -233));
        System.out.println("num：" + String.format("%6d", -233));
        System.out.println("num：" + String.format("%-6d", -233));
        System.out.println("num：" + String.format("%#6x", 5689));

        //eg
        String msg = String.format("用户%s，收到打款%2$.2f，收款人：%1$3s", "李四", 1024.6);
        System.out.println("msg: " + msg);
    }


    /**
     * 单引号''用于忽略特定占位符，比如左花括号{，必须成对出现
     * MessageFormat.format静态方法每次回创建一个对象，所以为了复用可以用 new MessageFormat(message)
     *
     *
     * https://blog.csdn.net/qq_36538061/article/details/78506758
     **/
    public static void testMessageFormat() {

        String format = "这里展示不同数据类型[{0}]，[{1}]，[{2}]，['{3}']，[{4}]，[{5}]的区别";

        Object[] params = {"string", 'a', null, 988, 999};
        String message =  MessageFormat.format(format, params);
        System.out.println("message: " + message);

        String pattern = "单引号用于忽略特定符号，如左花括号：'{'-}，而双引号需要转义：\"";
        MessageFormat mf = new MessageFormat(pattern);
        String messageRes = mf.format(null);
        System.out.println("messageRes: " + messageRes);
    }
}
