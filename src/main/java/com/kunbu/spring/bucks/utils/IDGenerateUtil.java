package com.kunbu.spring.bucks.utils;

import java.util.UUID;

/**
 * @program: bucks
 * @description: ID生成器
 * @author: kunbu
 * @create: 2019-08-16 17:17
 **/
public class IDGenerateUtil {

    private static final String SPLITTER_MIDDLE = "-";
    private static final String WHITE_CHAR = "";

    public static String uniqueID() {
        return UUID.randomUUID().toString().replace(SPLITTER_MIDDLE, WHITE_CHAR);
    }

}
