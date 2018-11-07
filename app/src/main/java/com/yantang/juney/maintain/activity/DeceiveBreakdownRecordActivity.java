package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.adapter.ExpandListViewAdapter;
import com.yantang.juney.maintain.adapter.RecordExpandListViewAdapter;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.RecordsBean;
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

/**
 * 故障记录页面
 */
public class DeceiveBreakdownRecordActivity extends BaseActivity  {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.ib_back_BK_record)
    ImageButton ibBackBKRecord;
    @BindView(R.id.tv_car_num_BK_record)
    TextView tvCarNumBKRecord;
    @BindView(R.id.lv_BK_record)
    ExpandableListView lvBKRecord;

    private int groupPosition, childPosition;

    private RecordExpandListViewAdapter mAdapter;

    private List<RecordsBean.RecordListBean> mDatas;

    private String carName, BKId;
    private String token, guid, userName, orgName, orgId, adCode, uuid, userId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_deceive_breakdown_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar.titleBar(R.id.toolbar_BK_record).init();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new RecordExpandListViewAdapter(this, mDatas);
        lvBKRecord.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        initParams();
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        BKId = bundle.getString("BKId");
        carName = bundle.getString("carName");

        tvCarNumBKRecord.setText(carName);

        queryRecord();

    }

    private void queryRecord() {
        /// <param name="mid">维修记录id</param>
        /// <param name="token">token信息</param>
        /// <param name="uuid">uuid(guid+时间，客户端传递)</param>
        /// <param name="username">用户名（蜜罐）</param>
        Map<String, String> map = new HashMap<>();
        map.put("mid", BKId);
        map.put("token", token);
        map.put("uuid", uuid);
        map.put("username", userName);

        LoggerUtil.d(TAG, "map: " + map.toString());

        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.d(TAG, "result: " + result);
                RecordsBean recordsBean = new Gson().fromJson(result, RecordsBean.class);

                mDatas = recordsBean.getRecordList();

                mAdapter.setDatas(mDatas);
            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(DeceiveBreakdownRecordActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };

        HttpMethods.getInstance().queryRecords(baseUrl, httpObserver, map);

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
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }


    @OnClick(R.id.ib_back_BK_record)
    public void onViewClicked() {
        finish();
    }

}

