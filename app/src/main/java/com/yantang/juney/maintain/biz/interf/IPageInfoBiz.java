package com.yantang.juney.maintain.biz.interf;

import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.PageCommonBean;

import java.util.Map;

import io.reactivex.Observer;

public interface IPageInfoBiz {

    /**
     * 公共方法-获取分页信息
     * @param baseUrl
     * @param subscriber
     * @param map
     */
    public void getPageInfo(String baseUrl, Observer<PageCommonBean> subscriber, Map<String,String> map);
}
