package com.yantang.juney.maintain.utils.rxjava_retrofit2;


import com.google.gson.Gson;
import com.yantang.juney.maintain.bean.ActiviteIPBean;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.utils.GzipUtils;
import com.yantang.juney.maintain.utils.LoggerUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 由于返回Json格式为{result : "H4sIAAAAAAAEAIuuVipKLS7NKVGyUjJU0lFKrQAynuzofto/8VnHhKdd85VqYwHNEareJAAAAA==",status : "1"}
 * 无法写成 CommonBean<T>

 */
public abstract class HttpObserver <T> implements Observer<CommonBean>{

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CommonBean value) {
        LoggerUtil.e("sdfasd",value.getStatus());
        //TODO 统一处理结果
        int status = value.getStatus();
        if (status==1){
            String result = GzipUtils.uncompressToString(value.getResult());
            _onNext(result);
        }else {
            _onError("请求异常");
        }

    }

    @Override
    public void onError(Throwable e) {
        //TODO 封装错误信息

        _onError(e.getMessage());

    }

    @Override
    public void onComplete() {

    }



    protected abstract void _onNext(String result);
    protected abstract void _onError(String message);



}
