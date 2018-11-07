package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.adapter.SimpleRecordAdapter;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.SimpleRecordBean;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DeceiveBreakdownSimpleRecordActivity extends BaseActivity implements SimpleRecordAdapter.OnItemClickListener {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.ib_back_BK_record_simple)
    ImageButton ibBackBKRecordSimple;
    @BindView(R.id.tv_car_name_BK_record_simple)
    TextView tvCarNumBKRecordSimple;
    @BindView(R.id.recv_BK_record_simple)
    RecyclerView recvBKRecordSimple;
    private String carNum, carId;
    private String token, guid, userName, orgName, orgId, adCode, uuid, userId;
    private List<SimpleRecordBean> simpleRecordList;
    private SimpleRecordAdapter mAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_deceive_breakdown_simple_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar.titleBar(R.id.toolbar_BK_record_simple).init();
        initAdapter();

    }

    private void initAdapter() {
        mAdapter = new SimpleRecordAdapter(this, simpleRecordList);
        recvBKRecordSimple.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recvBKRecordSimple.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        initParams();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        carNum = bundle.getString("carNum");
        carId = bundle.getString("carId");

        tvCarNumBKRecordSimple.setText(carNum);

        queryRecord();
    }

    private void queryRecord() {
//        vehicleid=39610&pagesize=10&startpage=1&token=ceshi&uuid=ceshi&username=ceshi

        Map<String, String> map = new HashMap<>();
        map.put("vehicleid", carId);
        map.put("token", token);
        map.put("uuid", uuid);
        map.put("username", userName);
        map.put("startpage", "1");
        map.put("pagesize", "1000");

        LoggerUtil.d(TAG, "map: " + map.toString());

        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.d(TAG, "result: " + result);

                simpleRecordList = new Gson().fromJson(result, new TypeToken<List<SimpleRecordBean>>() {
                }.getType());

                mAdapter.setDatas(simpleRecordList);

            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(DeceiveBreakdownSimpleRecordActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };

        HttpMethods.getInstance().querySimpleRecords(baseUrl, httpObserver, map);

    }

    /**
     * 请求参数
     */
    private void initParams() {
        SharedPreferences sp = getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);
        token = sp.getString(PreferenceKey.TOKEN, "");
        guid = sp.getString(PreferenceKey.GUID, "");
        userName = sp.getString(PreferenceKey.USERNAME, "");
        userId = sp.getString(PreferenceKey.USERID, "");
        orgName = sp.getString(PreferenceKey.ORGNAME, "");
        orgId = sp.getString(PreferenceKey.ORGID, "");
        adCode = sp.getString(PreferenceKey.ADCODE, "");


        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        uuid = guid + "_" + df.format(day);
    }


    @Override
    public void OnItemClickListener(int position) {
        Intent intent =new Intent(this,DeceiveBreakdownRecordActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("BKId",simpleRecordList.get(position).getID());
        bundle.putString("carName",carNum);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }




    @OnClick(R.id.ib_back_BK_record_simple)
    public void onViewClicked() {
        finish();
    }


}
