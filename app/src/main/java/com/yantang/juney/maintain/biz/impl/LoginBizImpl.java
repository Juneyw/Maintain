package com.yantang.juney.maintain.biz.impl;

import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.biz.interf.ILoginBiz;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.IService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginBizImpl implements ILoginBiz {



    @Override
    public void login(String baseUrl, Observer<CommonBean> subscriber,String userName, String password) {
        HttpMethods.getInstance().login(baseUrl,subscriber,userName,password);

    }
}
