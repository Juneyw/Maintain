package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.yantang.juney.maintain.MainActivity;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.LoginBean;
import com.yantang.juney.maintain.biz.impl.LoginBizImpl;
import com.yantang.juney.maintain.biz.interf.ILoginBiz;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.cleanapp.CleanApp;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;
import com.yantang.juney.maintain.view.DrawableLeftCenterButton;

import butterknife.BindView;
import butterknife.OnClick;

// TODO 软键盘遮挡问题
public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener ,TextView.OnEditorActionListener {
    public final String TAG = getClass().getSimpleName();

    @BindView(R.id.login_btn)
    DrawableLeftCenterButton loginBtn;
    @BindView(R.id.login_num)
    EditText loginNum;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.ib_cancel_input_code)
    ImageButton ibCancelInputCode;
    @BindView(R.id.ib_cancel_input_password_code)
    ImageButton ibCancelInputPasswordCode;
    @BindView(R.id.tv_serve_change)
    TextView tvServeChange;

    private Circle mCircleDrawable;

    private ILoginBiz loginBiz = new LoginBizImpl();
    private SharedPreferences sp;

    private String account = "";
    private String password = "";
    private SharedPreferences sp1;
    private boolean isLogin;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar
                .titleBar(R.id.login_top)
                .keyboardEnable(true)
                .init();


        sp = getSharedPreferences(PreferenceKey.SELECT_SERVE, MODE_PRIVATE);
        sp1 = getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);

        isLogin();

        mCircleDrawable = new Circle();
        mCircleDrawable.setColor(Color.WHITE);

        loginNum.setOnFocusChangeListener(this);
        loginPassword.setOnFocusChangeListener(this);

        loginPassword.setOnEditorActionListener(this);

        loginNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                account = s.toString();
                if (account.equals("")) {
                    ibCancelInputCode.setVisibility(View.GONE);
                }


                if (!account.equals("") && !password.equals("")) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
            }
        });

        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                password = s.toString();
                if (!account.equals("") && !password.equals("")) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
            }
        });

    }

    /**
     * 第二次登入
     */
    public void isLogin() {
        isLogin = sp1.getBoolean("isLogin", false);
        Log.e("s====", "isLogin== " + isLogin);
        if (isLogin == true) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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


    @OnClick({R.id.ib_cancel_input_code, R.id.ib_cancel_input_password_code, R.id.login_btn,
            R.id.tv_serve_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                ibCancelInputCode.setVisibility(View.GONE);
                ibCancelInputPasswordCode.setVisibility(View.GONE);
                login();

                break;
            case R.id.ib_cancel_input_code:
                loginNum.setText("");
                ibCancelInputCode.setVisibility(View.GONE);
                break;
            case R.id.ib_cancel_input_password_code:
                loginPassword.setText("");
                ibCancelInputPasswordCode.setVisibility(View.GONE);
                break;
            case R.id.tv_serve_change:
                cleanSpData();

                break;
        }
    }

    private void cleanSpData() {
        sp.edit().clear().commit();
        sp1.edit().clear().commit();
        CleanApp.cleanSharedPreference(this);
        CleanApp.cleanInternalCache(this);
        CleanApp.cleanDatabases(this);
        Intent intent=new Intent(this, ActivateActivity.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        startAnim();
        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.json(TAG, result);
                if (!result.equals("\"\"")) {
                    LoginBean loginBean = new Gson().fromJson(result, LoginBean.class);
                    saveLoginInfo(loginBean);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    //TODO 弹框显示
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }

                stopAnim();
            }

            @Override
            protected void _onError(String message) {
                //TODO 弹框显示
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                stopAnim();
            }
        };

        LoggerUtil.d(TAG, "baseUrl: " + sp.getString(PreferenceKey.SERVE_ADDRESS_NEW, ""));

        loginBiz.login(sp.getString(PreferenceKey.SERVE_ADDRESS_NEW, ""), httpObserver, account, password);
    }

    /**
     * 存储登录信息
     *
     * @param loginBean
     */
    private void saveLoginInfo(LoginBean loginBean) {

        SharedPreferences.Editor editor = sp1.edit();

        editor.putString(PreferenceKey.USERNAME, loginBean.getUserName());
        editor.putString(PreferenceKey.USERID, loginBean.getUserID());
        editor.putString(PreferenceKey.UserALIAS, loginBean.getUserALIAS());
        editor.putString(PreferenceKey.ORGNAME, loginBean.getOrgan_name());
        editor.putString(PreferenceKey.ORGID, loginBean.getOrgan_id());
        editor.putString(PreferenceKey.ADCODE, loginBean.getADCODE());

        editor.putString(PreferenceKey.ROLE_ID, loginBean.getROLE_ID());
        editor.putString(PreferenceKey.ROLE_NAME, loginBean.getROLE_NAME());
        editor.putString(PreferenceKey.ADNAME, loginBean.getADNAME());
        editor.putString(PreferenceKey.parentID, loginBean.getParentID());
        editor.putString(PreferenceKey.parentName, loginBean.getParentName());
        editor.putString(PreferenceKey.OrganizationPID, loginBean.getOrganizationPID());
        editor.putString(PreferenceKey.OrganizationPName, loginBean.getOrganizationPName());

        editor.putString(PreferenceKey.GUID, loginBean.getGuid());
        editor.putString(PreferenceKey.TOKEN, loginBean.getToken());



        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    private void startAnim() {

        mCircleDrawable.setBounds(0, 0, 80, 80);
        loginBtn.setCompoundDrawables(mCircleDrawable, null, null, null);
        mCircleDrawable.start();
    }

    private void stopAnim() {
        mCircleDrawable.setBounds(0, 0, 0, 0);

        loginBtn.setCompoundDrawables(mCircleDrawable, null, null, null);
        mCircleDrawable.stop();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.login_num:
                if (hasFocus) {
                    ibCancelInputCode.setVisibility(View.VISIBLE);
                } else {
                    ibCancelInputCode.setVisibility(View.GONE);
                }
                break;
            case R.id.login_password:
                if (hasFocus) {
                    ibCancelInputPasswordCode.setVisibility(View.VISIBLE);
                } else {
                    ibCancelInputPasswordCode.setVisibility(View.GONE);
                }
                break;
        }
    }




    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==  EditorInfo.IME_ACTION_GO){
            login();
        }

        return false;
    }
}
