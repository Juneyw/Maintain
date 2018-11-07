package com.yantang.juney.maintain.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by wangqing on 2017/6/12.
 */

public class IApp extends Application{
    private static IApp sInstance;
    private static final String TAG = "JIGUANG-Example";


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Fresco.initialize(this);
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public static IApp getInstance() {
        return sInstance;
    }



}
