package com.yantang.juney.maintain.utils.rxjava_retrofit2;

import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.PageCommonBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IService {

    @GET("CityConfigService/QuerySerCon")
    Observable<ResponseBody> scanConditionCode(@QueryMap Map<String,String> map);

//    "BusinessUser/UserLogin?username=%s&password=%s&logintype=2"
    @GET("BusinessUser/UserLogin?logintype=2")
    Observable<CommonBean> login(@Query("username") String username, @Query("password")String password);



    @GET("BusinessQiYeDangAnData/GetVehicle")
    Observable<PageCommonBean> getCarListInfo(@QueryMap Map<String,String> map);


//    BusinessQiYeDangAnData/GetTypeDictionary?type=1&parent_id=&token=ceshi&uuid=ceshi&username=ceshi
    @GET("BusinessQiYeDangAnData/GetTypeDictionary")
    Observable<CommonBean> queryTypes(@QueryMap Map<String,String> map);

//    http://101.37.24.52:19551/BusinessQiYeDangAnData/SaveTypeDictionary
    @Headers({"Content-Type: application/text","Accept: application/text"})
    @POST("BusinessQiYeDangAnData/SaveTypeDictionary")
    Observable<CommonBean> addTypes(@Body RequestBody route);


//    http://101.37.24.52:19551/BusinessQiYeDangAnData/SaveMaintenanceRecord
    @Headers({"Content-Type: application/text","Accept: application/text"})
    @POST("BusinessQiYeDangAnData/SaveMaintenanceRecord")
    Observable<CommonBean> submitBKData(@Body RequestBody route);

    /**
     *  上传照片
     */
    @Headers({"Content-Type: application/text","Accept: application/text"})//需要添加头
    @POST("BusinessWebGPS/UpLoadPicture")
    Observable<CommonBean> uploadImage(@Body RequestBody route);//传入的参数为RequestBody


//    BusinessQiYeDangAnData/GetMaintenanceInfo?vehicleid=39610&pagesize=10&startpage=1&token=ceshi&uuid=ceshi&username=ceshi
    @GET("BusinessQiYeDangAnData/GetMaintenanceInfo")
    Observable<CommonBean> querySimpleRecords(@QueryMap Map<String,String> map);

//    BusinessQiYeDangAnData/GetMaintenanceInfoByID?mid=5&token=ceshi&uuid=ceshi&username=ceshi
    @GET("BusinessQiYeDangAnData/GetMaintenanceInfoByID")
    Observable<CommonBean> queryRecords(@QueryMap Map<String,String> map);
}
