package com.kunbu.spring.bucks.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: bucks
 * @description: 商品信息展示模型
 * @author: kunbu
 * @create: 2019-08-16 15:58
 **/
@Data
public class GoodInfoVO implements Serializable {

    private String goodId;
    private String goodName;
    private String description;
    private String categoryId;
    private String categoryName;
    //前台类目
    private String categoryShow;
    private String stateInfo;

}
