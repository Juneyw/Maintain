package com.yantang.juney.maintain.biz.impl;

import android.util.Log;

import com.yantang.juney.maintain.biz.interf.IScanConditionCodeBiz;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.IService;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IScanConditionCodeImpl implements IScanConditionCodeBiz {


    @Override
    public void scanConditionCode(String baseUrl,Observer<ResponseBody> subscriber, Map<String, String> map) {
        HttpMethods.getInstance().scanConditionCode(baseUrl,subscriber, map);
    }
}
