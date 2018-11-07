package com.yantang.juney.maintain.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.adapter.ExpandListViewAdapter;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.BKRecordBean;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.ImageUrlBean;
import com.yantang.juney.maintain.bean.TypeBean;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;
import com.yantang.juney.maintain.view.MDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yantang.juney.maintain.constants.ConstantCode.UPLOAD_PIC_CODE;

public class MaintainActivity extends BaseActivity implements ExpandListViewAdapter.OnChildItemClickListener {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.ib_back_maintain)
    ImageButton ibBackMaintain;
    @BindView(R.id.tv_car_num_maintain)
    TextView tvCarNumMaintain;
    @BindView(R.id.but_submit_maintain)
    Button butSubmitMaintain;
    @BindView(R.id.lv_maintain)
    ExpandableListView lvMaintain;

    private String carNum, carId;
    private String token, guid, userName, orgName, orgId, adCode, uuid,userId;
    private List<TypeBean> types;

    private ExpandListViewAdapter mAdapter;
    private int choiceIndex;

    private List<BKRecordBean> mDatas=new ArrayList<>();

    private int groupPosition,childPosition;
    private AlertDialog dialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main_tain;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar.titleBar(R.id.toolbar_maintain).init();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new ExpandListViewAdapter(this,mDatas,baseUrl);
        lvMaintain.setAdapter(mAdapter);

        mAdapter.setOnChildItemClickListener(this);



    }

    @Override
    protected void initData() {
        initParams();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        carNum = bundle.getString("carNum");
        carId = bundle.getString("carId");

        tvCarNumMaintain.setText(carNum);
    }

    /**
     * 请求参数
     */
    private void initParams() {
        SharedPreferences sp =getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);
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


    @OnClick({R.id.ib_back_maintain, R.id.but_submit_maintain,R.id.btu_add_device_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btu_add_device_type:

                getTypes();


                break;
            case R.id.ib_back_maintain:
                finish();
                break;
            case R.id.but_submit_maintain:

                submitData();
                break;

        }
    }

    /**
     * 提交故障记录数据
     */
    private void submitData() {

        String submitDatas = getSubmitDatas();

        LoggerUtil.d(TAG,"submitDatas："+submitDatas);

        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.d(TAG,"result："+result);

                if (!result.equals("")){

                    if (Integer.parseInt(result)>0){
                        Toast.makeText(MaintainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(MaintainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(MaintainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };

        HttpMethods.getInstance().submitBKInfo(baseUrl,httpObserver,submitDatas);


    }

    private String getSubmitDatas() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("uuid", uuid);
            jsonObject.put("username", userName);
            jsonObject.put("token", token);
            jsonObject.put("vehicle_Id", carId);

            jsonObject.put("licenseplatenumber", carNum);
            jsonObject.put("nowuserid", userId);

            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();


            jsonObject.put("maintenanceuser",jsonArray1 );

            for (int i = 0; i < mDatas.size(); i++) {
                JSONObject jsonObject1 = new JSONObject();

                jsonObject1.put("maintenance_equipment_id", mDatas.get(i).getMaintenance_equipment_id());
                jsonObject1.put("maintenance_equipment_name", mDatas.get(i).getMaintenance_equipment_name());

                JSONArray jsonArray2 = new JSONArray();
                for (int j = 0; j < mDatas.get(i).getFaultList().size(); j++) {
                    JSONObject jsonObject2 = new JSONObject();
                    BKRecordBean.FaultListBean faultListBean = mDatas.get(i).getFaultList().get(j);
//
                    jsonObject2.put("type_fault_id",faultListBean.getType_fault_id());
                    jsonObject2.put("type_fault_name",faultListBean.getType_fault_name());
                    jsonObject2.put("type_phenomenon_id",faultListBean.getType_phenomenon_id());
                    jsonObject2.put("type_phenomenon_name",faultListBean.getType_phenomenon_name());
                    jsonObject2.put("type_reason_id",faultListBean.getType_reason_id());
                    jsonObject2.put("type_reason_name",faultListBean.getType_reason_name());
                    jsonObject2.put("type_exclude_id",faultListBean.getType_exclude_id());
                    jsonObject2.put("type_exclude_name",faultListBean.getType_exclude_name());

                    List<BKRecordBean.FaultListBean.AttlistBean> attlist = mDatas.get(i).getFaultList().get(j).getAttlist();

                    if (attlist!=null){
                        JSONArray jsonArray3=new JSONArray();
                        for (int k = 0; k < mDatas.get(i).getFaultList().get(j).getAttlist().size(); k++) {
                            JSONObject jsonObject3=new JSONObject();
                            jsonObject3.put("path",attlist.get(k).getPath());
                            jsonObject3.put("type",attlist.get(k).getType());
                            jsonArray3.put(jsonObject3);

                        }

                        jsonObject2.put("attlist",jsonArray3);

                    }

                    jsonArray2.put(jsonObject2);


                }

                jsonObject1.put("faultList",jsonArray2);

                jsonArray.put(jsonObject1);

            }

            jsonObject.put("maintenancerecord",jsonArray);

            LoggerUtil.json(TAG,"jsonObject: "+jsonObject.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
    private void showChoiceDialog(String[] deviceTypes){

            dialog = MDialog.createChoiceDialog(deviceTypes, this, "选择故障设备", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (types!=null){
                        BKRecordBean bkRecordBean = new BKRecordBean();
                        bkRecordBean.setMaintenance_equipment_id(types.get(choiceIndex).getID());
                        bkRecordBean.setMaintenance_equipment_name(types.get(choiceIndex).getNAME());
                        mDatas.add(bkRecordBean);
                    }

                    mAdapter.setDatas(mDatas);

                    for (int i = 0; i < mDatas.size(); i++) {
                        lvMaintain.expandGroup(i);

                    }


                    butSubmitMaintain.setVisibility(View.VISIBLE);

                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choiceIndex=which;


                }
            }).create();

            dialog.show();




        MDialog.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MaintainActivity.this, "add", Toast.LENGTH_SHORT).show();
                final EditText edit = new EditText(MaintainActivity.this);
                AlertDialog dialog1 = MDialog.createEditDialog(edit,"sadsf", MaintainActivity.this, "设备名称", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String s = edit.getText().toString();
                        addTypes(s);

                    }
                }).create();
                dialog1.show();


            }
        });
    }


    /**
     * 获取类型
     */
    private void getTypes() {
//        1设备名称  2故障类型 3故障现象 4故障原因 5故障排除【必填】
        Map<String, String> map = new HashMap<>();

        map.put("type", "1");
        map.put("token", token);
        map.put("uuid", uuid);
        map.put("username", userName);

        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.d(TAG,result);

                types = new Gson().fromJson(result, new TypeToken<List<TypeBean>>() {}.getType());

                if (types!=null){
                    List<String> deviceTypeList =new ArrayList<>();
                    for (int i = 0; i < types.size(); i++) {
                        deviceTypeList.add(types.get(i).getNAME());
                    }

                    String[] deviceTypes = (String[])deviceTypeList.toArray(new String[deviceTypeList.size()]);
                    showChoiceDialog(deviceTypes);

                }



            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(MaintainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };

        HttpMethods.getInstance().queryTypes(baseUrl,httpObserver,map);

    }

    /**
     * 新增类型
     * @param typeName
     */
    private void addTypes(String typeName)  {
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("name",typeName);
            jsonObject.put("type","1");
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
//                >0 保存 成功 0  保存失败 -1 失败，存在同名类型 -2 失败，输入的必填数据为空
                LoggerUtil.d(TAG,"add: "+result);
                int code  = Integer.parseInt(result);
                if (code>0){
                    getTypes();
                }else {
                    switch (code){
                        case 0:
                            Toast.makeText(MaintainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                            break;
                        case -1:
                            Toast.makeText(MaintainActivity.this, "存在同名类型", Toast.LENGTH_SHORT).show();
                            break;

                        case -2:
                            Toast.makeText(MaintainActivity.this, "输入的必填数据为空", Toast.LENGTH_SHORT).show();

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


    @Override
    public void OnChildItemClickListener(int groupPosition, int childPosition,int position) {
        this.groupPosition=groupPosition;
        this.childPosition=childPosition;
        Intent intent =new Intent(MaintainActivity.this,UploadBKPicActivity.class);
        List<BKRecordBean.FaultListBean.AttlistBean> attlist = mDatas.get(groupPosition).getFaultList().get(childPosition).getAttlist();
        ArrayList<ImageUrlBean> imageUrlBeanList=new ArrayList<>();

        if (attlist!=null){
            for (int i = 0; i < attlist.size(); i++) {
                ImageUrlBean imageUrlBean = new ImageUrlBean(attlist.get(i).getPath(),attlist.get(i).getType());
                imageUrlBeanList.add(imageUrlBean);
            }
        }
        intent.putParcelableArrayListExtra("imgDatas",imageUrlBeanList);
        startActivityForResult(intent,UPLOAD_PIC_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case UPLOAD_PIC_CODE:
                if (resultCode==RESULT_OK){
                    if (data==null)
                        return;
                    ArrayList<ImageUrlBean> imgDatas = data.getParcelableArrayListExtra("imgDatas");

                    if (mDatas.get(groupPosition).getFaultList().get(childPosition).getAttlist()==null){
                        List<BKRecordBean.FaultListBean.AttlistBean> attlistBeanList=new ArrayList<>();
                        mDatas.get(groupPosition).getFaultList().get(childPosition).setAttlist(attlistBeanList);
                    }else {
                        mDatas.get(groupPosition).getFaultList().get(childPosition).getAttlist().clear();
                    }

                    List<BKRecordBean.FaultListBean.AttlistBean> attlist = mDatas.get(groupPosition).getFaultList().get(childPosition).getAttlist();
                    for (int i = 0; i < imgDatas.size(); i++) {
                        BKRecordBean.FaultListBean.AttlistBean attlistBean = new BKRecordBean.FaultListBean.AttlistBean();
                        attlistBean.setPath(imgDatas.get(i).getResult());
                        attlistBean.setType(imgDatas.get(i).getType());
                        attlist.add(attlistBean);
                    }
                    LoggerUtil.d(TAG,"imgDatas: "+imgDatas.size());

                    mAdapter.setDatas(mDatas);

                }
                break;
        }

    }
}
