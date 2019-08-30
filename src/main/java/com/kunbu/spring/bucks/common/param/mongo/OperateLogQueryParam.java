package com.kunbu.spring.bucks.common.param.mongo;

import com.kunbu.spring.bucks.common.param.BaseQueryParam;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-27 16:46
 **/
public class OperateLogQueryParam extends BaseQueryParam {


    private String httpMethod;
    private String httpStatus;
    private String ipRegex;
    private String urlRegex;
    private String userId;

}
