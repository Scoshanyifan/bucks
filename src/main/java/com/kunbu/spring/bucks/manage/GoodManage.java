package com.kunbu.spring.bucks.manage;

import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.common.param.mysql.GoodQueryParam;

/**
 * @author: KunBu
 * @time: 2019/8/23 11:29
 * @description:
 */
public interface GoodManage {

    /**
     * 查询商品
     *
     * @param query
     * @return
     * @author kunbu
     * @time 2019/8/23 13:19
     **/
    ApiResult queryGoodByCondition(GoodQueryParam query);

}
