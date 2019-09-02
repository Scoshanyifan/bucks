package com.kunbu.spring.bucks.annotation;

import com.kunbu.spring.bucks.constant.other.OperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: KunBu
 * @time: 2019/8/3 15:31
 * @description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiNote {

    String value() default "";

    OperateTypeEnum type();
}
