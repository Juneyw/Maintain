package com.yantang.juney.maintain.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PicActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_vp_index)
    TextView tvVpIndex;
    private ArrayList<String> mImgUrls = new ArrayList<>();

    @BindView(R.id.vp_show_pic)
    ViewPager vpShowPic;
    private String address;
    private final String TAG = getClass().getSimpleName();
    private int mIndex;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pic;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar.init();
        SharedPreferences sp = getSharedPreferences(PreferenceKey.SELECT_SERVE, Context.MODE_PRIVATE);
        address = sp.getString(PreferenceKey.SERVE_ADDRESS_WEB, "");

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mImgUrls = intent.getStringArrayListExtra("imgUrls");
        LoggerUtil.d(TAG, mImgUrls.size());
        vpShowPic.setAdapter(new PicPagerAdapter());
        tvVpIndex.setText(mIndex + 1 + "/" + mImgUrls.size());
        vpShowPic.addOnPageChangeListener(this);

    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvVpIndex.setText(position + 1 + "/" + mImgUrls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    class PicPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImgUrls == null ? 0 : mImgUrls.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {//必须实现
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
            View view = LayoutInflater.from(PicActivity.this).inflate(R.layout.item_show_pic, null);
            SimpleDraweeView simpleDraweeView = view.findViewById(R.id.sd_show_pic);

            simpleDraweeView.setImageURI(address + mImgUrls.get(position));
            container.addView(view);
            view.requestLayout();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁

            View view = (View) object;
            SimpleDraweeView simpleDraweeView = view.findViewById(R.id.sd_show_pic);
//           simpleDraweeView.setController(null);
            container.removeView(view);

        }
    }
}
