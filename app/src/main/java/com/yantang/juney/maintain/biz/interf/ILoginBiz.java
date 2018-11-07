package com.yantang.juney.maintain.biz.interf;


import com.yantang.juney.maintain.bean.CommonBean;

import io.reactivex.Observer;

public interface ILoginBiz {
    /**
     * 登录
     * @param userName
     * @param password
     * @param baseUrl
     * @return
     */
    public void login(String baseUrl, Observer<CommonBean> subscriber, String userName, String password);


}
