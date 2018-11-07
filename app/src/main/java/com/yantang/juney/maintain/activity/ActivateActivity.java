package com.yantang.juney.maintain.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.wang.avi.AVLoadingIndicatorView;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.utils.Until;
import com.yantang.juney.maintain.MainActivity;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.ActiviteIPBean;
import com.yantang.juney.maintain.biz.impl.IScanConditionCodeImpl;
import com.yantang.juney.maintain.biz.interf.IScanConditionCodeBiz;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.constants.URLString;
import com.yantang.juney.maintain.utils.GetPhoneParameter;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.BitmapUtils;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.ResponseHttpObserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.yantang.juney.maintain.constants.ConstantCode.SCAN_CODE;
import static com.yantang.juney.maintain.constants.ConstantCode.SCAN_PIC_CODE;

public class ActivateActivity extends BaseActivity {
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.iv_identify_scan)
    ImageView ivIdentifyScan;
    @BindView(R.id.iv_identify_photo)
    ImageView ivIdentifyPhoto;
    @BindView(R.id.btu_input_hand)
    Button btuInputHand;
    @BindView(R.id.avi_activate)
    AVLoadingIndicatorView aviActivate;
    private IScanConditionCodeBiz scanConditionCodeBiz = new IScanConditionCodeImpl();
    private SharedPreferences sp;
    private boolean isSelectServe;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_activate;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar
                .titleBar(R.id.toolbar_activate)
                .statusBarColorInt(Color.BLACK)
                .init();


        applyJurisdiction();
        sp=getSharedPreferences(PreferenceKey.SELECT_SERVE,MODE_PRIVATE);

        isLogin();


    }

    /**
     * 第二次登入
     */
    public void isLogin(){
        isSelectServe=sp.getBoolean("isSelectServe",false);
        Log.e("s====","isSelectServe== "+isSelectServe);
        Log.e("s====","serve_address== "+sp.getString(PreferenceKey.SERVE_ADDRESS, ""));
        if(isSelectServe==true){
            Intent intent = new Intent(ActivateActivity.this, LoginActivity.class);
            startActivity(intent);
            ActivateActivity.this.finish();
        }
    }


    /**
     * 申请权限
     */
    private void applyJurisdiction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int ret = ActivityCompat.checkSelfPermission(ActivateActivity.this, Manifest.permission
                    .CAMERA);
            int ret1 = ActivityCompat.checkSelfPermission(ActivateActivity.this, Manifest.permission
                    .READ_PHONE_STATE);

            if (ret != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivateActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1001);
                return;
            }

            if (ret1 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivateActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1003);
                return;
            }

        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_identify_scan, R.id.iv_identify_photo, R.id.btu_input_hand})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_identify_scan:
                intent.setClass(ActivateActivity.this, CaptureActivity.class);
                startActivityForResult(intent, SCAN_CODE);
                break;
            case R.id.iv_identify_photo:
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent1, "请选择图片"), SCAN_PIC_CODE);
                break;
            case R.id.btu_input_hand:
                intent.setClass(ActivateActivity.this,InputCodeActivity.class);
//                intent.putExtra("flag_permission_phone",flag_permission_phone);
                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCAN_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String scan_result = bundle.getString("result");
                    initData(scan_result);
                }

                break;

            case SCAN_PIC_CODE:
                if (data==null){
                    return;
                }

                Uri uri = data.getData();
                if(uri==null)
                    return;
                if (resultCode == RESULT_OK) {

                    scanPic(uri);
                }

                break;


            default:
                break;
        }
    }

    private void scanPic(Uri uri) {
        String imgUrl = BitmapUtils.getUriAbstractPath(this,uri);

        Bitmap bitmap = Until.getxtsldraw(this, imgUrl);
        Reader reader = new MultiFormatReader();
        Result result = null;
        // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析。写外部可能会有异步的问题。（开始解析时bitmap为空）
        try {
            result = reader.decode(getBinaryBitmap(bitmap));
            String scan_result=result.toString();

            LoggerUtil.i(TAG, "result: "+result.toString());
            initData(scan_result);
        } catch (NotFoundException e) {
            Toast.makeText(this, "notFind", Toast.LENGTH_SHORT).show();
            Log.e("createQRCode", "onActivityResult: notFind");
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * ZXing 提供的方法，转换图片
     * @param bitmap
     * @return
     */
    public BinaryBitmap getBinaryBitmap(Bitmap bitmap){
        // 获取bitmap的宽高，像素矩阵
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);

        // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
        RGBLuminanceSource source = new RGBLuminanceSource(width,height,pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));


        return binaryBitmap;
    }

    private void initData(String scan_result) {
        startAnim();
        Map<String, String> map = new HashMap<>();

        map.put("confStr", scan_result);
        map.put("strType", "2");
        map.put("pn", "0");
        map.put("ms", "");
        map.put("me", "");
        map.put("os", GetPhoneParameter.getPhoneModel());
        map.put("sv", GetPhoneParameter.getPhoneRelease());
        map.put("av", getResources().getString(R.string.version_num));
        map.put("re", "");
        map.put("at", "7");


        LoggerUtil.i(TAG,map.toString());


        ResponseHttpObserver<ResponseBody> httpObserver = new ResponseHttpObserver<ResponseBody>() {
            @Override
            protected void _onNext(String result) {

                    ActiviteIPBean activiteIPBean = new Gson().fromJson(result, ActiviteIPBean.class);

                    if (activiteIPBean.getResult().equals("success")) {
                        stopAnim();
                        ActiviteIPBean.InfoBean info_IP = activiteIPBean.getInfo();

                        saveData(info_IP);

                        Intent intent=new Intent(ActivateActivity.this,LoginActivity.class);
                        intent.putExtra("logo_image_url",info_IP.getL());
                        startActivity(intent);
                        ActivateActivity.this.finish();

                    }

            }

            @Override
            protected void _onError(String message) {

            }
        };


        scanConditionCodeBiz.scanConditionCode(URLString.BASE_URL_ACTIVITE,httpObserver, map);

    }

    private void saveData(ActiviteIPBean.InfoBean info_IP) {
        String[] split = info_IP.getA().split(":");

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PreferenceKey.SERVE_ADDRESS, "http://"+info_IP.getA()+"/");
        editor.putString(PreferenceKey.SERVE_ADDRESS_WEB, "http://"+info_IP.getW()+"/");
        editor.putString(PreferenceKey.SERVE_ADDRESS_TUISONG, "http://"+info_IP.getT()+"/");
        editor.putString(PreferenceKey.SERVE_ADDRESS_NEW, "http://"+info_IP.getX()+"/");

        editor.putString(PreferenceKey.SERVE_NUMBLE, split[0]);

        editor.putString(PreferenceKey.CITY_ID, info_IP.getI());

        editor.putBoolean("isSelectServe", true);
        editor.apply();
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void startAnim() {
        aviActivate.smoothToShow();
    }

    private void stopAnim() {

        aviActivate.smoothToHide();
    }



}
