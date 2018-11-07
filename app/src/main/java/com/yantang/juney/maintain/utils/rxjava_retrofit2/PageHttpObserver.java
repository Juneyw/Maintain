package com.yantang.juney.maintain.utils.rxjava_retrofit2;


import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.PageCommonBean;
import com.yantang.juney.maintain.utils.GzipUtils;
import com.yantang.juney.maintain.utils.LoggerUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 由于返回Json格式为{result : "H4sIAAAAAAAEAIuuVipKLS7NKVGyUjJU0lFKrQAynuzofto/8VnHhKdd85VqYwHNEareJAAAAA==",status : "1"}
 * 无法写成 CommonBean<T>
 */
public abstract class PageHttpObserver<T> implements Observer<PageCommonBean>{

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(PageCommonBean value) {
        //TODO 统一处理结果
        int status = value.getStatus();
        if (status==1){
            String result = GzipUtils.uncompressToString(value.getResult());
            LoggerUtil.d("dsdasdas", value.getRecordCount());
            _onNext(result,value.getPageCount());

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



    protected abstract void _onNext(String result,int pageCount);
    protected abstract void _onError(String message);



}
