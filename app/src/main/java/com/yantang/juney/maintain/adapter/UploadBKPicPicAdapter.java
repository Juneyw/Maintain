package com.yantang.juney.maintain.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.activity.UploadBKPicActivity;
import com.yantang.juney.maintain.constants.PreferenceKey;

import java.util.List;

public class UploadBKPicPicAdapter extends BaseAdapter {

    private final String address;
    private Context mContext;

    private List<String> imgUrls;

    public UploadBKPicPicAdapter(Context context,List<String> imgUrls) {
        this.mContext=context;
        this.imgUrls=imgUrls;
        SharedPreferences sp = context.getSharedPreferences(PreferenceKey.SELECT_SERVE, Context.MODE_PRIVATE);
        address = sp.getString(PreferenceKey.SERVE_ADDRESS_WEB, "");

    }

    @Override
    public int getCount() {
        return imgUrls==null?1:imgUrls.size()+1;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_upload_pic, parent, false);
        BKPicViewHolder viewHolder=new BKPicViewHolder(view);

        if (position==0){
            viewHolder.ib_cancel.setVisibility(View.GONE);
        }else {
            viewHolder.ib_cancel.setVisibility(View.VISIBLE);

            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(mContext.getResources());
            
            GenericDraweeHierarchy hierarchy = builder
                    .setProgressBarImage(new ProgressBarDrawable())
                    .build();
            viewHolder.simpleDraweeView.setHierarchy(hierarchy);


            viewHolder.simpleDraweeView.setImageURI(address+imgUrls.get(position-1));
        }

        viewHolder.ib_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgUrls.remove(position-1);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void setDatas(List<String> imgUrls) {
        this.imgUrls=imgUrls;
        notifyDataSetChanged();
    }

    class BKPicViewHolder{
        private ImageView ib_cancel;
        private SimpleDraweeView simpleDraweeView;
        public BKPicViewHolder(View itemView) {
            ib_cancel=itemView.findViewById(R.id.ib_cancel_upload_item);
            simpleDraweeView=itemView.findViewById(R.id.sd_upload_install_pic_item);
        }
    }
}
