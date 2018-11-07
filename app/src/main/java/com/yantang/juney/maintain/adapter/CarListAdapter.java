package com.yantang.juney.maintain.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.bean.CarListInfoBean;

import java.util.List;

public class CarListAdapter extends BaseQuickAdapter<CarListInfoBean, BaseViewHolder> {

    public CarListAdapter(int layoutResId, @Nullable List<CarListInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarListInfoBean item) {
       helper.setText(R.id.tv_car_num_item,item.getLICENSEPLATENUMBER());
    }
}
