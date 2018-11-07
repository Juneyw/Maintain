package com.yantang.juney.maintain.biz.interf;

import java.util.Map;

import io.reactivex.Observer;
import okhttp3.ResponseBody;

public interface IScanConditionCodeBiz  {

    /**
     * 扫描特征码获取服务器信息
     * @param map
     * @return
     */
    public void scanConditionCode(String baseUrl,Observer<ResponseBody> subscriber,Map<String, String> map);
}
