package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;
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
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.ResponseHttpObserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class InputCodeActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.ib_back_input_code)
    ImageButton ibBackInputCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btu_input_hand)
    Button btuInputHand;
    @BindView(R.id.avi_activate)
    AVLoadingIndicatorView aviActivate;

    private IScanConditionCodeBiz scanConditionCodeBiz = new IScanConditionCodeImpl();
    private SharedPreferences sp;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_input_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar
                .titleBar(R.id.toolbar_input_code)
                .statusBarColorInt(Color.BLACK)
                .init();
        sp=getSharedPreferences(PreferenceKey.SELECT_SERVE,MODE_PRIVATE);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ib_back_input_code, R.id.btu_input_hand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back_input_code:
                finish();
                break;
            case R.id.btu_input_hand:
                initData(etCode.getText().toString());
                break;
        }
    }

    private void initData(String scan_code) {
        startAnim();
        Map<String, String> map = new HashMap<>();

        map.put("confStr", scan_code);
        map.put("strType", "2");
        map.put("pn", "0");
        map.put("ms", "");
        map.put("me", "");
        map.put("os", GetPhoneParameter.getPhoneModel());
        map.put("sv", GetPhoneParameter.getPhoneRelease());
        map.put("av", getResources().getString(R.string.version_num));
        map.put("re", "");
        map.put("at", "7");


        LoggerUtil.i(TAG, map.toString());


        ResponseHttpObserver<ResponseBody> httpObserver = new ResponseHttpObserver<ResponseBody>() {
            @Override
            protected void _onNext(String result) {
                    LoggerUtil.e(TAG,result);
                    ActiviteIPBean activiteIPBean = new Gson().fromJson(result, ActiviteIPBean.class);

                    if (activiteIPBean.getResult().equals("success")) {
                        stopAnim();
                        ActiviteIPBean.InfoBean info_IP = activiteIPBean.getInfo();

                        saveData(info_IP);

                        Intent intent = new Intent(InputCodeActivity.this, LoginActivity.class);
                        intent.putExtra("logo_image_url", info_IP.getL());
                        startActivity(intent);
                        InputCodeActivity.this.finish();

                    }

            }

            @Override
            protected void _onError(String message) {

            }
        };

        scanConditionCodeBiz.scanConditionCode(URLString.BASE_URL_ACTIVITE, httpObserver, map);

    }

    private void saveData(ActiviteIPBean.InfoBean info_IP) {
        String[] split = info_IP.getA().split(":");

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PreferenceKey.SERVE_ADDRESS, "http://" + info_IP.getA() + "/");
        editor.putString(PreferenceKey.SERVE_ADDRESS_WEB, "http://" + info_IP.getW() + "/");
        editor.putString(PreferenceKey.SERVE_ADDRESS_TUISONG, "http://" + info_IP.getT() + "/");
        editor.putString(PreferenceKey.SERVE_ADDRESS_NEW, "http://" + info_IP.getX() + "/");

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
