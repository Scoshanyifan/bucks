package com.kunbu.spring.bucks.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kunbu.spring.bucks.common.ServiceResult;
import com.kunbu.spring.bucks.common.entity.mysql.GoodEntity;
import com.kunbu.spring.bucks.common.param.mysql.GoodQueryParam;
import com.kunbu.spring.bucks.common.vo.GoodInfoVO;
import com.kunbu.spring.bucks.constant.biz.GoodStateEnum;
import com.kunbu.spring.bucks.dao.mysql.GoodMapper;
import com.kunbu.spring.bucks.service.GoodService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: bucks
 * @description: 商品管理
 * @author: kunbu
 * @create: 2019-08-16 15:20
 **/
@Service
public class GoodServiceImpl implements GoodService {

    private static final Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public ServiceResult<PageInfo<GoodInfoVO>> queryGoodByCondition(GoodQueryParam query) {

        PageInfo<GoodEntity> goodPageInfo = PageHelper
                .startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(() -> goodMapper.queryByCondition(query));

        List<GoodEntity> goodList = goodPageInfo.getList();
        logger.info(">>> good query total:{}", goodPageInfo.getTotal());

        PageInfo<GoodInfoVO> voPageInfo = new PageInfo<>();
        voPageInfo.setPageNum(query.getPageNum());
        voPageInfo.setPageSize(query.getPageSize());
        if (CollectionUtils.isNotEmpty(goodList)) {
            List<GoodInfoVO> vos = Lists.newArrayListWithCapacity(goodList.size());
            goodList.stream().forEach(x -> {
                GoodInfoVO vo = new GoodInfoVO();
                vo.setGoodId(x.getId());
                vo.setGoodName(x.getGoodName());
                vo.setDescription(x.getDescription());
                vo.setStateInfo(GoodStateEnum.getStateMsg(x.getState()));
                vo.setCategoryId(x.getCategoryId());
                vos.add(vo);
            });
            voPageInfo.setTotal(goodPageInfo.getTotal());
            voPageInfo.setPages(goodPageInfo.getPages());
            voPageInfo.setList(vos);
        } else {
            voPageInfo.setList(Collections.EMPTY_LIST);
        }
        return ServiceResult.SUCCESS(voPageInfo);
    }
}
