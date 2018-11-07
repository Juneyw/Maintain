package com.yantang.juney.maintain.constants;



/**
 * Created by Administrator on 2017/4/14.
 */

public class URLString {

    public  static   String BASE_URL = null;
    public  static   String BASE_URL_WEB  = null;
    public  static  String BASE_URL_TUISONG = null;
    public  static  String BASE_URL_NEW = null;

    public  static final String BASE_URL_ACTIVITE="http://app.zhatuche.com:19610/";


    public URLString(String base_url, String base_url_web, String base_url_tuisong,String base_url_new) {
        BASE_URL = base_url;
        BASE_URL_WEB = base_url_web;
        BASE_URL_TUISONG = base_url_tuisong;
        BASE_URL_NEW = base_url_new;
    }
}
