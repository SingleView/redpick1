package com.wlhb.hongbao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.adapter.SpinnerListAdapter;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Pay;
import com.wlhb.hongbao.bean.Publish;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.WeiXinPay;
import com.wlhb.hongbao.ui.fragment.HomeFragment;
import com.wlhb.hongbao.ui.view.MySpinner;
import com.wlhb.hongbao.utils.Base64BitmapUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class RedPacketInformationActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate, EasyPermissions.PermissionCallbacks, View.OnTouchListener {
    @BindView(R.id.bt_fhb)
    Button btFhb;
    @BindView(R.id.sp_hbfl)
    RelativeLayout spHbfl;
    @BindView(R.id.sp_fbdx)
    RelativeLayout spFbdx;
    @BindView(R.id.tv_hbfl)
    TextView tvHbfl;
    @BindView(R.id.tv_fbdx)
    TextView tvFbdx;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    @BindView(R.id.et_hbnr)
    EditText etHbnr;
    @BindView(R.id.et_hbgs)
    EditText etHbgs;
    @BindView(R.id.et_hbje)
    EditText etHbje;
    @BindView(R.id.tv_weizi)
    TextView tvWeizi;
    @BindView(R.id.et_ljbt)
    EditText etLjbt;
    @BindView(R.id.et_ljdz)
    EditText etLjdz;
    private String[] lx_list;
    private String[] dx_list;
    private String neirong;
    private Float jine;
    private String shuliang;
    private String dizhi;
    private int dx;
    private int REQUEST_GET_TIME=0;

    private String duixiang;
    private String ljbt;
    private String ljdz;
    private String address;
    private float latitude;
    private float longitude;
    private String province = new String();
    private String city = new String();
    private String district = new String();
    private String scope;


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_redpacketinformation, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("红包信息");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        lx_list = new String[]{"广告", "招聘", "交友", "相亲"};
        dx_list = new String[]{"只对女生", "只对男生", "不限"};
        etHbnr.setOnTouchListener(this);
        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setMaxItemCount(9);
        mPhotosSnpl.setSortable(true);
        mPhotosSnpl.setDelegate(this);
        mPhotosSnpl.isEditable();
        mPhotosSnpl.isPlusEnable();

    }

    @OnClick({R.id.rl_xzwz, R.id.bt_fhb, R.id.sp_hbfl, R.id.sp_fbdx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_xzwz:
                Intent intent=new Intent(this,RedPacketLocationActivity.class);
                //第一个参数是Intent对象，第二个requestCode指定我们的一个启动标志值，因为我们可能有多个按钮，如果都是跳转到同一个Activity对象上，
                //我们需要对其进行标志，才知道是哪个Activity对象跳转过来的。
                startActivityForResult(intent,REQUEST_GET_TIME);
                break;
            case R.id.bt_fhb:

                neirong = etHbnr.getText().toString().trim();
                String Hbje = etHbje.getText().toString().trim();
                if (!Hbje.equals("")){
                    jine = Float.parseFloat(Hbje);
                }
                shuliang = etHbgs.getText().toString().trim();

                dizhi = tvWeizi.getText().toString().trim();
                duixiang = tvFbdx.getText().toString().trim();
                ljbt = etLjbt.getText().toString().trim();
                ljdz = etLjdz.getText().toString().trim();

                if (TextUtils.isEmpty(neirong)) {
                    showToast("请输入内容");
                    return;
                }

                if (TextUtils.isEmpty(shuliang)) {
                    showToast("请输入数量");
                    return;
                }

                if (TextUtils.isEmpty(Hbje)) {
                    showToast("请输入金额");
                    return;
                }

                if (TextUtils.isEmpty(duixiang)) {
                    showToast("请选择对象");
                    return;
                }

                if (TextUtils.isEmpty(dizhi)) {
                    showToast("请选择地址");
                    return;
                }

                ArrayList<File> fileList = getFileList();
                if (fileList != null && !fileList.isEmpty()) {
                    compressImage(fileList);
                } else {
                    showToast("请选择图片");
                    return;
                }

                break;
            case R.id.sp_hbfl:
                MySpinner hbflSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, lx_list);
                hbflSpinner.showAsDropDown(spHbfl, 0, 0);//显示在rl_spinner的下方
                hbflSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        tvHbfl.setText(lx_list[position]);
                    }
                });
                break;
            case R.id.sp_fbdx:
                MySpinner fbdxSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, dx_list);
                fbdxSpinner.showAsDropDown(spFbdx, 0, 0);//显示在rl_spinner的下方
                fbdxSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        tvFbdx.setText(dx_list[position]);
                    }
                });
                break;
        }
    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }


    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, mPhotosSnpl.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);

    }


    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "xiaoyuan");

            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, takePhotoDir, mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            if (false) {
                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedImages(data));
            } else {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
            }
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }

        if(requestCode==REQUEST_GET_TIME&&resultCode==RESULT_OK){
            address = data.getStringExtra("address");
            scope = data.getStringExtra("scope");
            latitude = data.getFloatExtra("latitude",-1f);
            longitude = data.getFloatExtra("longitude",-1f);

            if (!address.equals("")){
                tvWeizi.setText(address.substring(address.indexOf("省") + 1)+"("+scope+")");
                province = address.substring(0,address.indexOf("省") + 1);
                city = address.substring(address.indexOf("省") + 1,address.indexOf("市") + 1);
                district = address.substring(address.indexOf("市") + 1);
            }
        }
    }

    public ArrayList<File> getFileList() {
        ArrayList<String> urls = mPhotosSnpl.getData();



        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            File file = new File(urls.get(i));
            files.add(file);
        }
        return files;
    }

    //压缩图片
    private void compressImage(final ArrayList<File> list) {
        Luban.compress(this, list)
                .putGear(Luban.CUSTOM_GEAR)
                .launch(new OnMultiCompressListener() {
                    @Override
                    public void onStart() {
                        showLoading();
                    }

                    @Override
                    public void onSuccess(List<File> fileList) {
                        Log.d(TAG, "afterCompress" + fileList);
                        if (fileList.size() == list.size()) {
                            try {
                                buildImagePart(fileList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            showToast("图片压缩出错");
                            hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getLocalizedMessage());
                        hideLoading();
                        showToast("图片压缩出错");
                    }
                });

    }

    private void buildImagePart(List<File> fileList) throws Exception {


        ArrayList<String> urls = mPhotosSnpl.getData();
        StringBuilder files = new StringBuilder();
        for (int i = 0; i < urls.size(); i++) {
            String file = urls.get(i);
            String base64File = Base64BitmapUtil.encodeBase64File(file) + ",";
            files.append(base64File) ;

        }
        String bpm = files.toString();
        doSubmit(bpm);
    }


    private void doSubmit(String bpm) {
        int scopeint = scope.equals("两公里内") ? 1 : 0;
        int receivetype = tvFbdx.equals("不限") ? 0 : (tvFbdx.equals("只对女生") ? 1 : 2);
        Call<BaseResponse<Publish>> call = service.publish(
                App.token,
                neirong,
                jine,
                shuliang,
                province,
                city,
                district,
                longitude,
                latitude,
                receivetype,
                ljbt,
                ljdz,
                scopeint,
                bpm,
                1
                );

        call.enqueue(new BaseCallback<BaseResponse<Publish>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Publish>> response) {
                BaseResponse<Publish> body = response.body();
                if (body.isOK()) {

                    sePtpay(body.data.number);

                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void sePtpay(String number) {
        Call<BaseResponse<JSON>> callpay = service.pay(App.token, "wxpay", number);
        callpay.enqueue(new BaseCallback<BaseResponse<JSON>>(callpay, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    JSON data = body.data;
                    Pay mWXPayingEntity = JSON.toJavaObject(data, Pay.class);
                    new WeiXinPay(context, mWXPayingEntity);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.et_hbnr:
                // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
        }
        return false;
    }
}
