package com.yantang.juney.maintain.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.yantang.juney.maintain.R;
import com.yantang.juney.maintain.adapter.UploadBKPicPicAdapter;
import com.yantang.juney.maintain.base.BaseActivity;
import com.yantang.juney.maintain.bean.CommonBean;
import com.yantang.juney.maintain.bean.ImageUrlBean;
import com.yantang.juney.maintain.utils.BitmapUtils;
import com.yantang.juney.maintain.utils.GzipUtils;
import com.yantang.juney.maintain.utils.LoggerUtil;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpMethods;
import com.yantang.juney.maintain.utils.rxjava_retrofit2.HttpObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yantang.juney.maintain.constants.ConstantCode.CAPTURE_CODE;
import static com.yantang.juney.maintain.constants.ConstantCode.PICK_CODE;
import static com.yantang.juney.maintain.utils.BitmapUtils.checkDirPath;
import static com.yantang.juney.maintain.utils.BitmapUtils.getImgStr;

public class UploadBKPicActivity extends BaseActivity {
    private final String TAG=getClass().getSimpleName();

    @BindView(R.id.ib_back_upload_BK_pic)
    ImageButton ibBackUploadBKPic;
    @BindView(R.id.but_upload_BK_pic)
    Button butUploadBKPic;
    @BindView(R.id.gv_upload_BK_pic_before)
    GridView gvUploadBKPicBefore;
    @BindView(R.id.gv_upload_BK_pic_after)
    GridView gvUploadBKPicAfter;
    private File tempFile;
    private String type;

    private ArrayList<ImageUrlBean> imgDatas = new ArrayList<>();

    private List<String> imgUrls1=new ArrayList<>();
    private List<String> imgUrls2=new ArrayList<>();
    private UploadBKPicPicAdapter uploadBKPicPicAdapter,uploadBKPicPicAdapter1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_upload_bkpic;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mImmersionBar.titleBar(R.id.toolbar_upload_BK_pic).init();
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/clipHeaderLikeQQ/image/"),
                    System.currentTimeMillis() + ".jpg");
        }


    }



    @Override
    protected void initData() {
        Intent intent=getIntent();
        ArrayList<ImageUrlBean> list = intent.getParcelableArrayListExtra("imgDatas");

        imgDatas.addAll(list);

        for (int i = 0; i < imgDatas.size(); i++) {
            if (imgDatas.get(i).getType().equals("500")){
                imgUrls1.add(imgDatas.get(i).getResult());
            }else if (imgDatas.get(i).getType().equals("501")){
                imgUrls2.add(imgDatas.get(i).getResult());
            }
        }

        initAdapter();

    }

    private void initAdapter() {
        uploadBKPicPicAdapter = new UploadBKPicPicAdapter(this, imgUrls1);

        gvUploadBKPicBefore.setAdapter(uploadBKPicPicAdapter);

        uploadBKPicPicAdapter1 = new UploadBKPicPicAdapter(this, imgUrls2);
        gvUploadBKPicAfter.setAdapter(uploadBKPicPicAdapter1);

        gvUploadBKPicBefore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    type="500";
                    showChooseDialog();
                }

            }
        });

        gvUploadBKPicAfter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    type="501";
                    showChooseDialog();
                }

            }
        });
    }


    private void showChooseDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setItems(new String[]{"拍照", "从相册选择图片"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                            startActivityForResult(intent, CAPTURE_CODE);

                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "请选择图片"), PICK_CODE);

                        }
                    }
                }).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CAPTURE_CODE:
                String imgUrl = tempFile.getPath();
                Log.e("RESULT_OK", "imgUrl: " + imgUrl);
                uploadPic(imgUrl);

                break;
            case PICK_CODE:
                if (data==null)
                    return;

                Uri uri = data.getData();
                if (uri == null)
                    return;
                if (resultCode == RESULT_OK) {
                    imgUrl = BitmapUtils.getUriAbstractPath(this, uri);
                    Log.e("RESULT_OK", "imgUrl: " + imgUrl);
                    uploadPic(imgUrl);
                }
                break;


            default:
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param imgUrl
     */
    private void uploadPic(String imgUrl) {

//        compressImage(imgUrl, imgUrl, 30);
        final LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("StrBase64", getImgStr(imgUrl));
        map.put("type", type);

        String jsonStr = new Gson().toJson(map);

        Log.e(TAG, "jsonStr: " + jsonStr);


        HttpObserver<CommonBean> httpObserver = new HttpObserver<CommonBean>() {
            @Override
            protected void _onNext(String result) {
                LoggerUtil.d(TAG,"result: "+result);
                ImageUrlBean imgUrlBean = new Gson().fromJson(result, ImageUrlBean.class);
                String imgPath = imgUrlBean.getResult();
                LoggerUtil.d(TAG,"imgPath: "+imgPath);
                if (imgPath.equals("")) {
                    Toast.makeText(UploadBKPicActivity.this, "无法识人脸，请重新上传", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadBKPicActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    imgDatas.add(imgUrlBean);
                    imgUrls1.clear();
                    imgUrls2.clear();
                    for (int i = 0; i < imgDatas.size(); i++) {
                        if (imgDatas.get(i).getType().equals("500")){
                            imgUrls1.add(imgDatas.get(i).getResult());
                        }else if (imgDatas.get(i).getType().equals("501")){
                            imgUrls2.add(imgDatas.get(i).getResult());
                        }
                    }
                    uploadBKPicPicAdapter.setDatas(imgUrls1);

                    uploadBKPicPicAdapter1.setDatas(imgUrls2);

                }

            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(UploadBKPicActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        };

        HttpMethods.getInstance().uploadImage(baseUrl,httpObserver,jsonStr);

    }


    @OnClick({R.id.ib_back_upload_BK_pic, R.id.but_upload_BK_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back_upload_BK_pic:
                finish();
                break;
            case R.id.but_upload_BK_pic:
                Intent intent = getIntent();
                intent.putParcelableArrayListExtra("imgDatas",  imgDatas);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("tempFile", tempFile);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
