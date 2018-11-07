package com.yantang.juney.maintain.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by wangqing on 2017/8/22.
 */

public class GetPhoneParameter {

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Context context){
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi ;
    }

    public static String getPhoneModel(){
        String model = Build.MODEL;
        return model;
    }


    public static String getPhoneRelease(){
        String release = Build.VERSION.RELEASE;
        return release;
    }

    //    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        TextView textView = (TextView) findViewById(R.id.text);
//        textView.setText("手机型号: " + android.os.Build.MODEL + ",\nSDK版本:"
//                + android.os.Build.VERSION.SDK + ",\n系统版本:"
//                + android.os.Build.VERSION.RELEASE);
//    }

}
