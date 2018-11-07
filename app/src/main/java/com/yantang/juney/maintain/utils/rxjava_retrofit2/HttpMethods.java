package com.yantang.juney.maintain.utils.rxjava_retrofit2;

import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.PageCommonBean;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethods  {
    private Retrofit retrofit;
    private IService mService;

    //构造方法私有
    private HttpMethods() {

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){

        return SingletonHolder.INSTANCE;
    }

    /**
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void scanConditionCode(String baseUrl,Observer<ResponseBody> subscriber, Map<String, String> map){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);


        mService.scanConditionCode(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }




    public void login(String baseUrl,Observer<CommonBean> subscriber, String userName, String password ){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);


        mService.login(userName,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void getCarListInfo(String baseUrl, Observer<PageCommonBean> subscriber, Map<String, String> map){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);


        mService.getCarListInfo(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void queryTypes(String baseUrl, Observer<CommonBean> subscriber, Map<String, String> map){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);


        mService.queryTypes(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void addTypes(String baseUrl, Observer<CommonBean> subscriber, String jsonStr){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);

        RequestBody body = RequestBody.create(MediaType.parse("application/text; charset=utf-8"), jsonStr);

        mService.addTypes(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void submitBKInfo(String baseUrl, Observer<CommonBean> subscriber, String jsonStr){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);

        RequestBody body = RequestBody.create(MediaType.parse("application/text; charset=utf-8"), jsonStr);

        mService.submitBKData(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void uploadImage(String baseUrl, Observer<CommonBean> subscriber, String jsonStr){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);

        RequestBody body = RequestBody.create(MediaType.parse("application/text; charset=utf-8"), jsonStr);

        mService.uploadImage(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void querySimpleRecords(String baseUrl, Observer<CommonBean> subscriber, Map<String, String> map){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);


        mService.querySimpleRecords(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void queryRecords(String baseUrl, Observer<CommonBean> subscriber, Map<String, String> map){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 添加生成bean的工厂
                .addConverterFactory(GsonConverterFactory.create())
                //添加支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mService = retrofit.create(IService.class);


        mService.queryRecords(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * {
     *     "body":
     *          {
     *             "name":"ben",
     *             "age":"28",
     *             "gender":"man"
     *          },
     *         "code":"0",
     *         "status":"0"
     * }
     *  固定格式时使用
     */
//    private class HttpResultFunc<T> implements Function<CommonBean<T>, ObservableSource<T>> {
//
//        @Override
//        public ObservableSource<T> apply(CommonBean<T> tCommonBean) throws Exception {
//
//            return Observable.just (tCommonBean.getData());
//        }
//    }


}
