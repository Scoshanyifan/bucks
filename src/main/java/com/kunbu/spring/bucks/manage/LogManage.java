package com.kunbu.spring.bucks.manage;

import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.common.param.mongo.OperateLogQueryParam;
import com.kunbu.spring.bucks.common.param.mongo.RequestLogQueryParam;

/**
 * @author: KunBu
 * @time: 2019/9/4 9:08
 * @description:
 */
public interface LogManage {

    /**
     * 操作日志列表
     *
     * @param param
     * @return
     * @author kunbu
     * @time 2019/9/4 9:09
     **/
    ApiResult queryOperateLog(OperateLogQueryParam param);

    /**
     * 请求调用日志列表
     *
     * @param param
     * @return
     * @author kunbu
     * @time 2019/9/4 9:10
     **/
    ApiResult queryRequestLog(RequestLogQueryParam param);

}
