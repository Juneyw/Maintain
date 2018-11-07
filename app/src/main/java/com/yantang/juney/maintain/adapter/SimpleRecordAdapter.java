package com.yantang.juney.maintain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.bean.SimpleRecordBean;

import java.util.List;

public class SimpleRecordAdapter extends RecyclerView.Adapter<SimpleRecordAdapter.SimpleRecodViewHolder> {
    private Context mContext;
    private List<SimpleRecordBean> mDatas;


    public SimpleRecordAdapter(Context mContext, List<SimpleRecordBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public SimpleRecodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple_record, parent, false);

        return new SimpleRecodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleRecodViewHolder holder, int position) {
        holder.tv_time_simple_record.setText(mDatas.get(position).getEDITDATETIME());
        holder.tv_bk_type_simple_record.setText(mDatas.get(position).getEQUIPMENT_NAMES());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    public void setDatas(List<SimpleRecordBean> simpleRecordList) {
        this.mDatas=simpleRecordList;
        notifyDataSetChanged();

    }

    class SimpleRecodViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_device_type_simple_record,tv_time_simple_record,tv_bk_type_simple_record;
        public SimpleRecodViewHolder(View itemView) {
            super(itemView);

            tv_device_type_simple_record=itemView.findViewById(R.id.tv_device_type_simple_record);

            tv_time_simple_record=itemView.findViewById(R.id.tv_time_simple_record);
            tv_bk_type_simple_record=itemView.findViewById(R.id.tv_bk_type_simple_record);


        }
    }

    /**
     * 单项点击事件
     */
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void OnItemClickListener(int position);
    }
    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener=mListener;
    }

}
