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

    /**
     * DB主键id
     *
     * @param
     * @author kunbu
     * @time 2019/8/23 14:27
     * @return
     **/
    public static String DBUniqueID() {
        return UUID.randomUUID().toString().replace(SPLITTER_MIDDLE, WHITE_CHAR);
    }

}
