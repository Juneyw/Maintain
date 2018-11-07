package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.yantang.juney.maintain.MainActivity;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.base.BaseActivity;

//TODO 图片全屏
public class WelcomeActivity extends BaseActivity {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar
                .fitsSystemWindows(true)
                .transparentStatusBar()
                .init();
        startActivity(new Intent(WelcomeActivity.this,ActivateActivity.class));
        finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
