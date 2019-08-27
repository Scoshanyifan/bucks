package com.kunbu.spring.bucks.common.param;

import java.io.Serializable;
import java.util.List;

/**
 * @program: bucks
 * @description: 商品查询参数
 * @author: kunbu
 * @create: 2019-08-16 15:26
 **/
public class GoodQueryParam extends BaseDbQueryParam implements Serializable {

    private String goodNameLike;
    private String categoryId;
    private List<String> goodIdList;

    public String getGoodNameLike() {
        return goodNameLike;
    }

    public void setGoodNameLike(String goodNameLike) {
        this.goodNameLike = goodNameLike;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getGoodIdList() {
        return goodIdList;
    }

    public void setGoodIdList(List<String> goodIdList) {
        this.goodIdList = goodIdList;
    }

    @Override
    public String toString() {
        return "GoodQueryParam{" +
                "goodNameLike='" + goodNameLike + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", goodIdList=" + goodIdList +
                "} " + super.toString();
    }
}
