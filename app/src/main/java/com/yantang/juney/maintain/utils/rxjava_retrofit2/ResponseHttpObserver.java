package com.yantang.juney.maintain.utils.rxjava_retrofit2;


import com.google.gson.Gson;
import com.yantang.juney.maintain.bean.ActiviteIPBean;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.utils.GzipUtils;
import com.yantang.juney.maintain.utils.LoggerUtil;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 由于返回Json格式为{result : "H4sIAAAAAAAEAIuuVipKLS7NKVGyUjJU0lFKrQAynuzofto/8VnHhKdd85VqYwHNEareJAAAAA==",status : "1"}
 * 无法写成 CommonBean<T>

 */
public abstract class ResponseHttpObserver<T> implements Observer<ResponseBody>{

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResponseBody value) {

        if (value==null){
            _onError("请求异常");
        }

        String string = null;
        try {
            string = value.string();
            String backlogJsonStrTmp = string.replace("\\", "");
            string = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);

            _onNext(string);

        } catch (IOException e) {
            e.printStackTrace();
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
