package com.kunbu.spring.bucks.service;

import com.github.pagehelper.PageInfo;
import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.param.GoodQueryParam;
import com.kunbu.spring.bucks.common.vo.GoodInfoVO;

/**
 * @author: KunBu
 * @time: 2019/8/16 15:19
 * @description:
 */
public interface GoodManageService {

    /**
     * 查询商品
     * @param query
     * @author kunbu
     * @time 2019/8/16 15:29
     * @return
     **/
    ServiceResult<PageInfo<GoodInfoVO>> queryGoodByCondition(GoodQueryParam query);

}
