package com.yantang.juney.maintain;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.yantang.juney.maintain.activity.LoginActivity;
import com.yantang.juney.maintain.activity.SearchCarActivity;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.fragment.CarInfoFragment;
import com.yantang.juney.maintain.utils.cleanapp.CleanApp;
import com.yantang.juney.maintain.utils.exitapp.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yantang.juney.maintain.constants.ConstantCode.Search_CODE;

//TODO 侧边栏和状态栏重叠
public class MainActivity extends BaseActivity implements CarInfoFragment.OnFragmentInteractionListener {

    @BindView(R.id.container_main)
    FrameLayout containerMain;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.rl_search_click)
    RelativeLayout rlSearchClick;

    private SharedPreferences sp, sp1;
    private ActionBarDrawerToggle mDrawerToggle;
    private long exitTime;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar
                .titleBar(R.id.toolbar_main)
                .init();
        AppManager.getAppManager().addActivity(this);

        initDrawerLayout();

    }

    /**
     * DrawerLayout
     */
    private void initDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarMain, R.string.drawer_open, R.string.drawer_close);

        //获取开关同时让开关和DrawerLayout关联在一起
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(mDrawerToggle);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.menu_change_password:
//                        intent.setClass(MainActivity.this, ChangePasswordActivity.class);
//                        startActivity(intent);
                        break;

                    case R.id.menu_change_account:
                        sp.edit().clear().commit();
                        CleanApp.cleanSharedPreference(MainActivity.this);
                        CleanApp.cleanInternalCache(MainActivity.this);
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });

        initLeftData();
    }

    private void initLeftData() {
        sp = getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);
        sp1 = getSharedPreferences(PreferenceKey.SELECT_SERVE, MODE_PRIVATE);
        Menu menu = navigationView.getMenu();

        getMenuView(menu, R.id.menu_account).setText(sp.getString(PreferenceKey.USERNAME, ""));

        getMenuView(menu, R.id.menu_user).setText(sp.getString(PreferenceKey.ROLE_NAME, ""));

        getMenuView(menu, R.id.menu_organization).setText(sp.getString(PreferenceKey.OrganizationPName, ""));

        getMenuView(menu, R.id.menu_server).setText(sp1.getString(PreferenceKey.SERVE_NUMBLE, ""));

        getMenuView(menu, R.id.menu_version).setText(R.string.version_num);

        View headerView = navigationView.getHeaderView(0);
        TextView tv_company_name = headerView.findViewById(R.id.company_name);
        tv_company_name.setText(sp.getString(PreferenceKey.parentName, ""));

        TextView tv_user_city = headerView.findViewById(R.id.user_city);
        tv_user_city.setText(sp.getString(PreferenceKey.ADNAME, ""));

    }

    private TextView getMenuView(Menu menu, int id) {

        View actionView = MenuItemCompat.getActionView(menu.findItem(id));
        TextView tv_num = (TextView) actionView.findViewById(R.id.tv_num);
        return tv_num;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.rl_search_click)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, SearchCarActivity.class);
        startActivityForResult(intent, Search_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Search_CODE:
                if (resultCode == RESULT_OK) {
                    if (data == null)
                        return;

                    String carNum = data.getStringExtra("carNum");

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    final CarInfoFragment fragment = new CarInfoFragment();

                    transaction.add(R.id.container_main, CarInfoFragment.newInstance(carNum));
                    transaction.commit();

                }
                break;
            default:
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit(this);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
