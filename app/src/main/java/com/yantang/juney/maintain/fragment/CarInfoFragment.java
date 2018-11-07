package com.yantang.juney.maintain.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.activity.DeceiveBreakdownSimpleRecordActivity;
import com.yantang.juney.maintain.activity.MaintainActivity;
import com.yantang.juney.maintain.bean.CarListInfoBean;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.PageCommonBean;
import com.yantang.juney.maintain.bean.TypeBean;
import com.yantang.juney.maintain.constants.PreferenceKey;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.PageHttpObserver;
import com.yantang.juney.maintain.view.DrawableLeftCenterButton;
import com.yantang.juney.maintain.view.MDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * TODO 封装Fragment父类
 */
public class CarInfoFragment extends Fragment {

    private static final String ARG_PARAM1 = "carNum";

    private final String TAG = "CarInfoFragment";
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.tv_SIM)
    TextView tvSIM;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_car_type)
    TextView tvCarType;
    @BindView(R.id.btu_maintain)
    DrawableLeftCenterButton btuMaintain;
    @BindView(R.id.btu_record)
    DrawableLeftCenterButton btuRecord;
    Unbinder unbinder;

    private String carNum,carId;
    private String token, guid, userName, orgName, orgId, adCode, uuid;

    private OnFragmentInteractionListener mListener;

    private String baseUrl;
    private  Context mActivity ;
    private List<String> deviceTypeList =new ArrayList();
    private List<TypeBean> types =new ArrayList<>();
    private Intent intent = null;

    public CarInfoFragment() {
        // Required empty public constructor
    }


    public static CarInfoFragment newInstance(String carNum) {
        CarInfoFragment fragment = new CarInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, carNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carNum = getArguments().getString(ARG_PARAM1);
            LoggerUtil.d(TAG, carNum);
        }
        mActivity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseUrl = mActivity.getSharedPreferences(PreferenceKey.SELECT_SERVE, MODE_PRIVATE).getString(PreferenceKey.SERVE_ADDRESS_NEW, "");
        getCarListInfo();
    }

    @OnClick({R.id.btu_maintain, R.id.btu_record})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("carNum",carNum);
        bundle.putString("carId",carId);

        switch (view.getId()) {
            case R.id.btu_maintain:
//                getTypes();
                intent.setClass(mActivity,MaintainActivity.class);
                intent.putExtras(bundle);
                break;
            case R.id.btu_record:
                intent.setClass(mActivity,DeceiveBreakdownSimpleRecordActivity.class);
                intent.putExtras(bundle);
                break;

        }
        startActivity(intent);
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


                List<TypeBean> list = new Gson().fromJson(result, new TypeToken<List<TypeBean>>() {
                }.getType());

                types.addAll(list);

                for (int i = 0; i < types.size(); i++) {
                    deviceTypeList.add(types.get(i).getNAME());
                }

                String[] deviceTypes = (String[])deviceTypeList.toArray(new String[deviceTypeList.size()]);


                showChoiseDialog(deviceTypes);
            }

            @Override
            protected void _onError(String message) {

            }
        };

        HttpMethods.getInstance().queryTypes(baseUrl,httpObserver,map);

    }


    private void showChoiseDialog(String[] deviceTypes){

        AlertDialog dialog = MDialog.createChoiceDialog(deviceTypes, mActivity, "选择故障设备", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (intent !=null){
                    startActivity(intent);
                }

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                intent =new Intent();
                intent.setClass(mActivity,MaintainActivity.class);
                Bundle bundle=new Bundle();
                if (types.size()>0){
                    bundle.putString("typeId",types.get(which).getID());
                    bundle.putString("carNum",carNum);
                    bundle.putString("carId",carId);

                }

                intent.putExtras(bundle);
            }
        }).create();

        dialog.show();

        MDialog.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "add", Toast.LENGTH_SHORT).show();
                final EditText edit = new EditText(mActivity);
                AlertDialog dialog1 = MDialog.createEditDialog(edit,"sadsf", mActivity, "设备名称", new DialogInterface.OnClickListener() {
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
                LoggerUtil.d(TAG,result);
            }

            @Override
            protected void _onError(String message) {

            }
        };

        HttpMethods.getInstance().addTypes(baseUrl,httpObserver,jsonObject.toString());
    }

    /**
     * 查询车辆列表
     */
    private void getCarListInfo() {
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
        map.put("startpage", 0 + "");
        map.put("pagesize", "1000");
        map.put("licenseplateNumber", carNum);

        LoggerUtil.d(TAG, map.toString());

        PageHttpObserver<PageCommonBean> pageHttpObserver = new PageHttpObserver<PageCommonBean>() {
            @Override
            protected void _onNext(String result, int pageCount) {
                LoggerUtil.json(TAG, result);
                LoggerUtil.d(TAG, pageCount);
                List<CarListInfoBean> carList = new Gson().fromJson(result, new TypeToken<List<CarListInfoBean>>() {
                }.getType());
                CarListInfoBean carListInfoBean = carList.get(0);

                initViewData(carListInfoBean);

            }

            @Override
            protected void _onError(String message) {

            }
        };

        HttpMethods.getInstance().getCarListInfo(baseUrl, pageHttpObserver, map);

    }

    private void initViewData(CarListInfoBean carListInfoBean) {
        LoggerUtil.d(TAG, carListInfoBean.getID());
        carId=carListInfoBean.getID();
        tvCarNum.setText(carListInfoBean.getLICENSEPLATENUMBER());
        tvCarType.setText(carListInfoBean.getVEHICLETYPENAME());
        tvCompany.setText(carListInfoBean.getORGANATION_NAME());
        tvSIM.setText(carListInfoBean.getEQUIP_SIM());
        tvDeviceName.setText(carListInfoBean.getEQUIP_NAME());
    }

    /**
     * 请求参数
     */
    private void initParams() {
        SharedPreferences sp = mActivity.getSharedPreferences(PreferenceKey.LOGIN_USER, MODE_PRIVATE);
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
