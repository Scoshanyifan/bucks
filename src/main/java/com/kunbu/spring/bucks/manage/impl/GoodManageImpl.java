package com.kunbu.spring.bucks.manage.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kunbu.spring.bucks.common.ApiResult;
import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.param.mysql.GoodQueryParam;
import com.kunbu.spring.bucks.common.vo.GoodInfoVO;
import com.kunbu.spring.bucks.service.CategoryBackService;
import com.kunbu.spring.bucks.service.GoodService;
import com.kunbu.spring.bucks.manage.GoodManage;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: bucks
 * @description: 商品业务层
 * @author: kunbu
 * @create: 2019-08-23 11:30
 **/
@Service
public class GoodManageImpl implements GoodManage {

    private static final Logger logger = LoggerFactory.getLogger(GoodManageImpl.class);

    @Autowired
    private GoodService goodService;

    @Autowired
    private CategoryBackService categoryBackService;

    @Override
    public ApiResult queryGoodByCondition(GoodQueryParam query) {

        ServiceResult<PageInfo<GoodInfoVO>> goodResult = goodService.queryGoodByCondition(query);
        if (!goodResult.ok()) {
            return ApiResult.error(goodResult.getServiceError());
        }

        PageInfo<GoodInfoVO> goodPage = goodResult.getData();
        List<GoodInfoVO> goodPageList = goodPage.getList();
        if (CollectionUtils.isNotEmpty(goodPageList)) {
            Map<String, String> cateId2NameMap = Maps.newHashMap();
            ServiceResult<Map<String, String>> categoryMapResult = categoryBackService.getCategoryMap(true);
            if (categoryMapResult.ok()) {
                cateId2NameMap = categoryMapResult.getData();
            } else {
                logger.error(">>> 获取类目名称失败, msg:{}", categoryMapResult.getServiceError().getServiceErrorMsg());
            }
            //Variable used in lambda expression should be final or effectively final
            Map<String, String> finalCateId2NameMap = cateId2NameMap;

            goodPageList.stream().forEach(x -> x.setCategoryName(finalCateId2NameMap.get(x.getCategoryId())));
        }

        ApiResult.ApiPage apiPage = ApiResult.ApiPage.success(
                goodPage.getPageNum(),
                goodPage.getPageSize(),
                goodPage.getPages(),
                goodPage.getTotal(),
                goodPageList);
        return ApiResult.success(apiPage);
    }
}
