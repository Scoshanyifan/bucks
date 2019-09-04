package com.kunbu.spring.bucks.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-04 10:01
 **/
public class DateFormatUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateFormatUtils.class);

    public static final String DEFAULT_DATE_PATTERN					    = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_1 					        = "yyyy-MM-dd";
    public static final String DATE_PATTERN_2 					        = "yyyy.MM.dd";
    public static final String DATE_PATTERN_3					        = "yyyy年MM月dd日";
    public static final String DATE_PATTERN_4					        = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_PATTERN_7                           = "yyyy-MM-dd HH:mm:ss,SSS";

    public static final String EMPTY_DATE_STR							= "-";

    private static final ThreadLocal<SimpleDateFormat> DEFAULT_SDF 		= ThreadLocal.withInitial(
            () -> new SimpleDateFormat(DEFAULT_DATE_PATTERN)
    );

    public static String formatDefault(Date date) {
        if (date != null) {
            return DEFAULT_SDF.get().format(date);
        } else {
            return EMPTY_DATE_STR;
        }
    }

    public static Date parseDefault(String source) throws ParseException {
        try {
            return DEFAULT_SDF.get().parse(source);
        } catch (ParseException e) {
            logger.error(">>> parseDefault error", e);
            return null;
        }
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            return new SimpleDateFormat(pattern).format(date);
        } else {
            return EMPTY_DATE_STR;
        }
    }

    public static Date parse(String source, String pattern) throws ParseException {
        try {
            return new SimpleDateFormat(pattern).parse(source);
        } catch (ParseException e) {
            logger.error(">>> parse error", e);
            return null;
        }
    }
}