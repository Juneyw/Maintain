package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.fragment.AddBreakdownRecordFragment;
import com.yantang.juney.maintain.fragment.BreakdownRecordFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 备用
 * 新增维修记录页面
 */
public class StandbyActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.tv_car_num_standby)
    TextView tvCarNumMaintain;
    @BindView(R.id.rg_standby)
    RadioGroup rgMaintain;
    @BindView(R.id.ib_back_standby)
    ImageButton ibBackMaintain;
    @BindView(R.id.but_submit_standby)
    Button butSubmitMaintain;
    private String carNum, carId;
    private Fragment currentFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_standby;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar.titleBar(R.id.toolbar_standby).init();
        switchFragment(0);
        rgMaintain.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        carNum = bundle.getString("carNum");
        carId = bundle.getString("carId");
        tvCarNumMaintain.setText(carNum);

    }

    @OnClick({R.id.ib_back_standby, R.id.but_submit_standby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back_standby:
                finish();
                break;
            case R.id.but_submit_standby:

                break;
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = group.findViewById(checkedId);
        rb.setChecked(true);
        switch (checkedId) {
            case R.id.rb_add_breakdown:
                switchFragment(0);
                break;
            case R.id.rb_breakdown_record:
                switchFragment(1);
                break;

        }
    }

    private void switchFragment(int pos) {

        Fragment targetFragment = createFragment(pos);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.container_standby, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction.hide(currentFragment).show(targetFragment);

        }
        currentFragment = targetFragment;

        transaction.commit();
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = null;

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new AddBreakdownRecordFragment();
                    Log.i("fragment", "fragment1");
                    break;
                case 1:
                    fragment = new BreakdownRecordFragment();
                    Log.i("fragment", "fragment2");
                    break;


            }

        }
        return fragment;
    }





}
