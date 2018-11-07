package com.yantang.juney.maintain.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.activity.DeceiveBreakdownRecordActivity;
import com.yantang.juney.maintain.activity.PicActivity;
import com.yantang.juney.maintain.bean.BKRecordBean;
import com.yantang.juney.maintain.bean.RecordsBean;
import com.yantang.juney.maintain.constants.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

public class BKRecordPicAdapter extends BaseAdapter {
    private final String address;
    private Context mContext;
    private  List<RecordsBean.RecordListBean.FaultListBean.AttlistBean> mDatas;
    private ArrayList<String> imgUrl1=new ArrayList<>();
    private ArrayList<String> imgUrl2=new ArrayList<>();

    public BKRecordPicAdapter(Context context,  List<RecordsBean.RecordListBean.FaultListBean.AttlistBean> attlist) {
        this.mContext=context;
        this.mDatas=attlist;

        SharedPreferences sp = context.getSharedPreferences(PreferenceKey.SELECT_SERVE, Context.MODE_PRIVATE);
        address = sp.getString(PreferenceKey.SERVE_ADDRESS_WEB, "");

        if (mDatas!=null ) {
            for (int i = 0; i < mDatas.size(); i++) {

                String type = mDatas.get(i).getTYPE();
                if (type.equals("500.0")) {
                    imgUrl1.add(mDatas.get(i).getFILEPATH());
                } else if (type.equals("501.0")){
                    imgUrl2.add(mDatas.get(i).getFILEPATH());
                }

            }
        }



    }



    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_install_pic, parent, false);

        BKPicViewHolder viewHolder=new BKPicViewHolder(view);


        if (position==0){
            viewHolder.tv_pic_type.setText("维修前");
            viewHolder.tv_num.setText("共"+imgUrl1.size()+"张");
            if (imgUrl1.size()>0){
                viewHolder.simpleDraweeView.setImageURI(address+imgUrl1.get(0));
            }


        }else {
            viewHolder.tv_pic_type.setText("维修后");
            viewHolder.tv_num.setText("共"+imgUrl2.size()+"张");
            if (imgUrl2.size()>0){
                viewHolder.simpleDraweeView.setImageURI(address+imgUrl2.get(0));
            }


        }

        viewHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,PicActivity.class);
                if (position==0){
                    if (imgUrl1.size()>0){
                        intent.putStringArrayListExtra("imgUrls",imgUrl1);
                        mContext.startActivity(intent);
                    }

                }else {
                    if (imgUrl2.size()>0){
                        intent.putStringArrayListExtra("imgUrls",imgUrl2);
                        mContext.startActivity(intent);
                    }

                }






            }
        });



        return view;
    }

    class BKPicViewHolder{
        private TextView tv_num,tv_pic_type;
        private SimpleDraweeView simpleDraweeView;
        public BKPicViewHolder(View itemView) {
            tv_num=itemView.findViewById(R.id.tv_pic_num);
            tv_pic_type=itemView.findViewById(R.id.tv_pic_type);

            simpleDraweeView=itemView.findViewById(R.id.sd_BK_pic);
        }
    }
}
