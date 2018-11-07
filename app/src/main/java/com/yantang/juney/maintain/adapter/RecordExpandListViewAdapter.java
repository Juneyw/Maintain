package com.yantang.juney.maintain.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.bean.BKRecordBean;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.RecordsBean;
import com.yantang.juney.maintain.bean.TypeBean;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;
import com.yantang.juney.maintain.view.MDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wangqing on 2017/12/28.
 */

public class RecordExpandListViewAdapter extends BaseExpandableListAdapter {
    private final String TAG = "ExpandListViewAdapter";
    private Context mContext;
    private List<RecordsBean.RecordListBean> mDatas;


    public RecordExpandListViewAdapter(Context mContext, List<RecordsBean.RecordListBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;

    }


    @Override
    public int getGroupCount() {
        return mDatas ==null?0: mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<RecordsBean.RecordListBean.FaultListBean> faultList = mDatas.get(groupPosition).getFaultList();
        return faultList==null?0:faultList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LoggerUtil.d(TAG,isExpanded);
        GroupViewHolder groupViewHoder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.ltem_group_device_type, parent, false);
            groupViewHoder=new GroupViewHolder(convertView);
            convertView.setTag(groupViewHoder);
        }else {
            groupViewHoder= (GroupViewHolder) convertView.getTag();
        }
        groupViewHoder.tv_add_Bk_type.setVisibility(View.GONE);
        groupViewHoder.iv_device_type.setVisibility(View.GONE);

        groupViewHoder.tv_device_type.setText(mDatas.get(groupPosition).getMaintenance_equipment_name());

        return convertView;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public View getChildView( int groupPosition,  int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LoggerUtil.e(TAG,"groupPosition: "+groupPosition);
        ChildenViewHolder childenViewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_bk_type, parent, false);
            childenViewHolder=new ChildenViewHolder(convertView);
            convertView.setTag(childenViewHolder);
        }else {
            childenViewHolder = (ChildenViewHolder) convertView.getTag();
        }

        childenViewHolder.iv_BK_type.setVisibility(View.GONE);
        childenViewHolder.iv_BK_state.setVisibility(View.GONE);
        childenViewHolder.iv_BK_reason.setVisibility(View.GONE);
        childenViewHolder.iv_BK_exclude.setVisibility(View.GONE);
        childenViewHolder.tv_BK_pic.setVisibility(View.GONE);
        childenViewHolder.ib_delete_BK_type.setVisibility(View.GONE);

        String type_fault_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_fault_name();
        String type_phenomenon_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_phenomenon_name();
        String type_reason_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_reason_name();
        String type_exclude_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_exclude_name();


        childenViewHolder.tv_BK_type.setText(type_fault_name);
        childenViewHolder.tv_BK_state.setText(type_phenomenon_name);

        childenViewHolder.tv_BK_reason.setText(type_reason_name);

        childenViewHolder.tv_BK_exclude.setText(type_exclude_name);

        childenViewHolder.gv_install_pic.setAdapter(new BKRecordPicAdapter(mContext,mDatas.get(groupPosition).getFaultList().get(childPosition).getAttlist()));


        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        private TextView tv_device_type,tv_add_Bk_type;
        private ImageView iv_device_type;
        GroupViewHolder(View itemView) {
            tv_device_type=itemView.findViewById(R.id.tv_device_type);
            tv_add_Bk_type=itemView.findViewById(R.id.tv_add_Bk_type);
            iv_device_type=itemView.findViewById(R.id.iv_device_type);
        }
    }

     class ChildenViewHolder {
         private TextView tv_BK_type,tv_BK_state,tv_BK_reason,tv_BK_exclude,tv_BK_pic;
         private ImageView iv_BK_type,iv_BK_state,iv_BK_reason,iv_BK_exclude;
         private ImageButton ib_delete_BK_type;
         private GridView gv_install_pic;

        ChildenViewHolder(View itemView) {
            tv_BK_type=itemView.findViewById(R.id.tv_BK_type);
            tv_BK_state=itemView.findViewById(R.id.tv_BK_state);
            tv_BK_reason=itemView.findViewById(R.id.tv_BK_reason);
            tv_BK_exclude=itemView.findViewById(R.id.tv_BK_exclude);
            tv_BK_pic=itemView.findViewById(R.id.tv_BK_pic);
            iv_BK_type=itemView.findViewById(R.id.iv_BK_type);
            iv_BK_state=itemView.findViewById(R.id.iv_BK_state);
            iv_BK_reason=itemView.findViewById(R.id.iv_BK_reason);
            iv_BK_exclude=itemView.findViewById(R.id.iv_BK_exclude);
            ib_delete_BK_type=itemView.findViewById(R.id.ib_delete_BK_type);
            gv_install_pic=itemView.findViewById(R.id.gv_install_pic);
        }

    }

    public void setDatas(List<RecordsBean.RecordListBean> groupDatas) {
        this.mDatas = groupDatas;

        notifyDataSetChanged();
    }



}