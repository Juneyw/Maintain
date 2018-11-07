package com.yantang.juney.maintain.biz.impl;

import com.yantang.juney.maintain.bean.PageCommonBean;
import com.yantang.juney.maintain.biz.interf.IPageInfoBiz;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;

import java.util.Map;

import io.reactivex.Observer;

public class PageInfoImpl implements IPageInfoBiz {

    @Override
    public void getPageInfo(String baseUrl, Observer<PageCommonBean> subscriber, Map<String, String> map) {
        HttpMethods.getInstance().getCarListInfo(baseUrl,subscriber, map);
    }
}
