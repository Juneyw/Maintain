package com.yantang.juney.maintain.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.activity.UploadBKPicActivity;
import com.yantang.juney.maintain.bean.BKRecordBean;
import com.yantang.juney.maintain.bean.CommonBean;
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

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private final String TAG = "ExpandListViewAdapter";
    private Context mContext;
    private List<BKRecordBean> mDatas;
    private String baseUrl;
    private String token, guid, userName, orgName, orgId, adCode, uuid;
    private String title;

    private int choiceIndex;

    private List<TypeBean> list;
    private boolean isChange;


    public ExpandListViewAdapter(Context mContext, List<BKRecordBean> mDatas, String baseUrl) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.baseUrl=baseUrl;
        initParams();

    }


    @Override
    public int getGroupCount() {
        return mDatas ==null?0: mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<BKRecordBean.FaultListBean> faultList = mDatas.get(groupPosition).getFaultList();
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


        groupViewHoder.tv_device_type.setText(mDatas.get(groupPosition).getMaintenance_equipment_name());
        groupViewHoder.tv_add_Bk_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChange=false;
                getTypes(groupPosition,0,2);

            }
        });

        groupViewHoder.rlSelectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTypes(groupPosition,0,1);

            }
        });

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

        String type_fault_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_fault_name();
        String type_phenomenon_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_phenomenon_name();
        String type_reason_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_reason_name();
        String type_exclude_name = mDatas.get(groupPosition).getFaultList().get(childPosition).getType_exclude_name();

        if (type_fault_name!=null){
            childenViewHolder.tv_BK_type.setText(type_fault_name);
        }
        if (type_phenomenon_name!=null){
            childenViewHolder.tv_BK_state.setText(type_phenomenon_name);
        }
        if (type_reason_name!=null){
            childenViewHolder.tv_BK_reason.setText(type_reason_name);
        }
        if (type_exclude_name!=null){
            childenViewHolder.tv_BK_exclude.setText(type_exclude_name);
        }

        childenViewHolder.gv_install_pic.setAdapter(new BKPicAdapter(mContext,mDatas.get(groupPosition).getFaultList().get(childPosition).getAttlist()));

        childenViewHolder.gv_install_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener!=null){
                    mListener.OnChildItemClickListener(groupPosition,childPosition,position);
                }
            }
        });



        childenViewHolder.rl_select_BK_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChange=true;
                getTypes(groupPosition,childPosition,2);
            }
        });
        childenViewHolder.ll_select_BK_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTypes(groupPosition,childPosition,3);
            }
        });

        childenViewHolder.ll_select_BK_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTypes(groupPosition,childPosition,4);
            }
        });

        childenViewHolder.ll_select_BK_exclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTypes(groupPosition,childPosition,5);
            }
        });

        childenViewHolder.ib_delete_BK_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas!=null){
                    mDatas.get(groupPosition).getFaultList().remove(childPosition);
                    notifyDataSetChanged();
                }

            }
        });

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        private TextView tv_device_type,tv_add_Bk_type;
        private RelativeLayout rlSelectDevice;
        GroupViewHolder(View itemView) {
            tv_device_type=itemView.findViewById(R.id.tv_device_type);
            tv_add_Bk_type=itemView.findViewById(R.id.tv_add_Bk_type);
            rlSelectDevice=itemView.findViewById(R.id.rl_select_device);
        }
    }

     class ChildenViewHolder {
         private TextView tv_BK_type,tv_BK_state,tv_BK_reason,tv_BK_exclude,tv_BK_pic;
         private RelativeLayout rl_select_BK_type;
         private LinearLayout ll_select_BK_state,ll_select_BK_reason,ll_select_BK_exclude;
         private ImageButton ib_delete_BK_type;
         private GridView gv_install_pic;

        ChildenViewHolder(View itemView) {
            tv_BK_type=itemView.findViewById(R.id.tv_BK_type);
            tv_BK_state=itemView.findViewById(R.id.tv_BK_state);
            tv_BK_reason=itemView.findViewById(R.id.tv_BK_reason);
            tv_BK_exclude=itemView.findViewById(R.id.tv_BK_exclude);
            tv_BK_pic=itemView.findViewById(R.id.tv_BK_pic);
            rl_select_BK_type=itemView.findViewById(R.id.rl_select_BK_type);
            ll_select_BK_state=itemView.findViewById(R.id.ll_select_BK_state);
            ll_select_BK_reason=itemView.findViewById(R.id.ll_select_BK_reason);
            ll_select_BK_exclude=itemView.findViewById(R.id.ll_select_BK_exclude);
            ib_delete_BK_type=itemView.findViewById(R.id.ib_delete_BK_type);
            gv_install_pic=itemView.findViewById(R.id.gv_install_pic);
        }

    }

    public void setDatas(List<BKRecordBean> groupDatas) {
        this.mDatas = groupDatas;

        notifyDataSetChanged();
    }



    private void showChoiseDialog(String[] deviceTypes, int groupPosition,int childPosition, int type){
//        1设备名称  2故障类型 3故障现象 4故障原因 5故障排除【必填】

        switch (type){
            case 1:
                title="设备名称";
                break;
            case 2:
                title="故障类型";
                break;
            case 3:
                title="故障现象";
                break;
            case 4:
                title="故障原因";
                break;
            case 5:
                title="故障排除";
                break;
        }

          AlertDialog  dialog2 = MDialog.createChoiceDialog(deviceTypes, mContext, title, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (type){
                        case 1:
                            if (list!=null){

                                mDatas.get(groupPosition).setMaintenance_equipment_name(list.get(choiceIndex).getNAME());
                                mDatas.get(groupPosition).setMaintenance_equipment_id(list.get(choiceIndex).getID());

                            }
                            break;
                        case 2:
                            if (list!=null){
                                if (!isChange){
                                    BKRecordBean.FaultListBean faultListBean = new BKRecordBean.FaultListBean();
                                    faultListBean.setType_fault_name(list.get(choiceIndex).getNAME());
                                    faultListBean.setType_fault_id(list.get(choiceIndex).getID());
                                    if (mDatas.get(groupPosition).getFaultList()==null){
                                        List<BKRecordBean.FaultListBean> faultList =new ArrayList<>();
                                        mDatas.get(groupPosition).setFaultList(faultList);
                                    }

                                    mDatas.get(groupPosition).getFaultList().add(faultListBean);

                                }else {
                                    mDatas.get(groupPosition).getFaultList().get(childPosition).setType_fault_name(list.get(choiceIndex).getNAME());
                                    mDatas.get(groupPosition).getFaultList().get(childPosition).setType_fault_id(list.get(choiceIndex).getID());
                                }

                                isChange=true;
                            }
                            break;
                        case 3:
                            mDatas.get(groupPosition).getFaultList().get(childPosition).setType_phenomenon_id(list.get(choiceIndex).getID());
                            mDatas.get(groupPosition).getFaultList().get(childPosition).setType_phenomenon_name(list.get(choiceIndex).getNAME());
                            break;
                        case 4:
                            mDatas.get(groupPosition).getFaultList().get(childPosition).setType_reason_id(list.get(choiceIndex).getID());
                            mDatas.get(groupPosition).getFaultList().get(childPosition).setType_reason_name(list.get(choiceIndex).getNAME());
                            break;

                        case 5:
                            mDatas.get(groupPosition).getFaultList().get(childPosition).setType_exclude_id(list.get(choiceIndex).getID());
                            mDatas.get(groupPosition).getFaultList().get(childPosition).setType_exclude_name(list.get(choiceIndex).getNAME());
                            break;
                    }


                    notifyDataSetChanged();

                    choiceIndex=-1;
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choiceIndex=which;

                }
            }).create();


        dialog2.show();



        MDialog.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "add", Toast.LENGTH_SHORT).show();
                final EditText edit = new EditText(mContext);
                AlertDialog dialog1 = MDialog.createEditDialog(edit,"sadsf", mContext, title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = edit.getText().toString();
                        addTypes(s,groupPosition, type);
                        dialog2.dismiss();
                    }
                }).create();
                dialog1.show();


            }
        });
    }

    /**
     * 获取类型
     * @param childPosition
     * @param groupPosition
     * @param type
     */
    private void getTypes(int groupPosition,int childPosition, int type) {
//        1设备名称  2故障类型 3故障现象 4故障原因 5故障排除【必填】
        Map<String, String> map = new HashMap<>();

        map.put("type", type+"");
        map.put("token", token);
        map.put("uuid", uuid);
        map.put("username", userName);

        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.json(TAG,result);
                list = new Gson().fromJson(result, new TypeToken<List<TypeBean>>() {
                }.getType());

                if (list!=null){
                    List<String> deviceTypeList =new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        deviceTypeList.add(list.get(i).getNAME());
                    }

                    String[] deviceTypes = (String[])deviceTypeList.toArray(new String[deviceTypeList.size()]);


                    showChoiseDialog(deviceTypes,groupPosition,childPosition,type);
                }

            }

            @Override
            protected void _onError(String message) {

            }
        };

        HttpMethods.getInstance().queryTypes(baseUrl,httpObserver,map);

    }

    /**
     * 新增类型
     * @param typeName
     * @param groupPosition
     * @param type
     */
    private void addTypes(String typeName, int groupPosition, int type)  {
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("name",typeName);
            jsonObject.put("type",type+"");
            jsonObject.put("remark","");
            jsonObject.put("uuid",uuid);
            jsonObject.put("username",userName);
            jsonObject.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LoggerUtil.d(TAG,jsonObject.toString());

        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.d(TAG,result);
                int code  = Integer.parseInt(result);
                if (code>0){
                    getTypes(groupPosition,0, type);

                }else {
                    switch (code){
                        case 0:
                            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                            break;
                        case -1:
                            Toast.makeText(mContext, "存在同名类型", Toast.LENGTH_SHORT).show();
                            break;

                        case -2:
                            Toast.makeText(mContext, "输入的必填数据为空", Toast.LENGTH_SHORT).show();

                            break;

                    }
                }
            }

            @Override
            protected void _onError(String message) {

            }
        };

        HttpMethods.getInstance().addTypes(baseUrl,httpObserver,jsonObject.toString());
    }

    /**
     * 请求参数
     */
    private void initParams() {
        SharedPreferences sp =mContext.getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);
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
     * 单项点击事件
     */
    private OnChildItemClickListener mListener;
    public interface OnChildItemClickListener{
        void OnChildItemClickListener(int groupPosition, int childPosition,int position);
    }
    public void setOnChildItemClickListener(OnChildItemClickListener mListener){
        this.mListener=mListener;
    }

}