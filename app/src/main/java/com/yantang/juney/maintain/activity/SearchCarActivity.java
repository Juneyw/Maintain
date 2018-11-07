package com.yantang.juney.maintain.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.adapter.CarListAdapter;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.CarListInfoBean;
import com.yantang.juney.maintain.bean.PageCommonBean;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.PageHttpObserver;
import com.yantang.juney.maintain.view.CustomLoadMoreView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

//TODO 点击单项颜色变化
public class SearchCarActivity extends BaseActivity implements TextWatcher ,TextView.OnEditorActionListener {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.et_search_car)
    EditText etSearchCar;
    @BindView(R.id.tv_cancel_search_car)
    TextView tvCancelSearchCar;
    @BindView(R.id.ib_cancel_search_car)
    ImageButton ibCancelSearchCar;
    @BindView(R.id.recv_search)
    RecyclerView recvSearch;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private String searchInfo = "";
    private String token, guid, userName, orgName, orgId, adCode, uuid;
    private List<CarListInfoBean> carList = new ArrayList<>();
    private CarListAdapter mAdapter;
    private boolean isRefresh;


    private int TOTAL_PAGE;
    private int mNextRequestPage;
    /** 每页数量*/
    private static final int PAGE_SIZE = 15;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_sear_car;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar
                .fitsSystemWindows(true)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .init();


        etSearchCar.addTextChangedListener(this);
        etSearchCar.setOnEditorActionListener(this);

        initAdapter();
        initSwipeLayout();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = getIntent();
                intent.putExtra("carNum",carList.get(position).getLICENSEPLATENUMBER());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void initSwipeLayout() {
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setRefreshing(true);
        refresh();
        swipeLayout.setOnRefreshListener(() -> {
            refresh();
        });
    }

    private void refresh() {

        carList.clear();

        isRefresh=true;

        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载

        getCarListInfo(mNextRequestPage, PAGE_SIZE);

    }


    private void initAdapter() {
        mAdapter = new CarListAdapter(R.layout.item_car_list, carList);
        recvSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recvSearch.setAdapter(mAdapter);

        mAdapter.setLoadMoreView(new CustomLoadMoreView());


        mAdapter.setOnLoadMoreListener(() -> {

            recvSearch.postDelayed(() -> {
                isRefresh = false;

                if (mNextRequestPage < TOTAL_PAGE) {
                    mNextRequestPage++;
                    getCarListInfo(mNextRequestPage, PAGE_SIZE);
                } else {
                    //数据全部加载完毕
                    mAdapter.loadMoreEnd();
                }


            }, 500);
        });


    }

    @Override
    protected void initData() {

    }

    /**
     * 请求参数
     */
    private void initParams() {
        SharedPreferences sp = getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);
        token = sp.getString(PreferenceKey.TOKEN, "");
        guid = sp.getString(PreferenceKey.GUID, "");
        userName = sp.getString(PreferenceKey.USERNAME, "");
        orgName = sp.getString(PreferenceKey.ORGNAME, "");
        orgId = sp.getString(PreferenceKey.ORGID, "");
        adCode = sp.getString(PreferenceKey.ADCODE, "");

        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        uuid = guid + "_" + df.format(day);
    }

    /**
     * 查询车辆列表
     *
     * @param startPage
     * @param pageSize
     */
    private void getCarListInfo(int startPage, int pageSize) {
//        token:E7923D9BCADED4BF852F99ED3EE287DEA79F76A09D2F32DE69E1054B05B3D579EF8CACAAF67B59EA
//        uuid:46c6fb6ff7894743bdb457a70062f414
//        username:xjzzqxh
//        licenseplateNumber:
//        organName:新疆
//        eqiupmentName:
//        equipmentId:
//        sim:
//        adcode:650100
//        pagesize:10
//        startpage:0
//        orgid:2282

        initParams();

        Map<String, String> map = new HashMap<>();

        map.put("adcode", adCode);
        map.put("uuid", uuid);
        map.put("username", userName);
        map.put("token", token);
        map.put("username", userName);
        map.put("orgid", orgId);
        map.put("startpage", startPage + "");
        map.put("pagesize", pageSize + "");
        map.put("licenseplateNumber", searchInfo);

        LoggerUtil.d(TAG, map.toString());

        PageHttpObserver<PageCommonBean> pageHttpObserver = new PageHttpObserver<PageCommonBean>() {
            @Override
            protected void _onNext(String result, int pageCount) {
                LoggerUtil.json(TAG, result);
                LoggerUtil.d(TAG, pageCount);
                carList = new Gson().fromJson(result, new TypeToken<List<CarListInfoBean>>() {
                }.getType());
                TOTAL_PAGE = pageCount;
                setData(isRefresh, carList);
                mAdapter.setEnableLoadMore(true);
                swipeLayout.setRefreshing(false);

            }

            @Override
            protected void _onError(String message) {

            }
        };

        HttpMethods.getInstance().getCarListInfo(baseUrl, pageHttpObserver, map);

    }

    private void setData(boolean isRefresh, List data) {
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        mAdapter.loadMoreComplete();

        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }

    }

    @OnClick({R.id.ib_cancel_search_car, R.id.tv_cancel_search_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_cancel_search_car:
                etSearchCar.setText("");
                break;
            case R.id.tv_cancel_search_car:
                finish();
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        searchInfo = s.toString();
        if (searchInfo.equals("")) {
            ibCancelSearchCar.setVisibility(View.GONE);
        } else {
            ibCancelSearchCar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==EditorInfo.IME_ACTION_SEARCH){
            refresh();
        }
        return false;
    }
}
