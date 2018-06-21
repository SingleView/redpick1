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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.adapter.SpinnerListAdapter;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.view.MySpinner;
import com.wlhb.hongbao.utils.Base64BitmapUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Created by Administrator on 2018/3/30/030.
 */

public class PostedActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tv_tzlx)
    TextView tvTzlx;
    @BindView(R.id.sp_tzlx)
    RelativeLayout spTzlx;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.et_biaoti)
    EditText etBiaoti;
    @BindView(R.id.et_neirong)
    EditText etNeirong;
    private String[] dx_list;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    private String biaoti;
    private String neirong;
    private String leixing;
    private int communityid;
    private int intleixing;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_posted, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("发帖");
        setTitleColor(true);
        setTitleRightText("          提 交", new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                biaoti = etBiaoti.getText().toString().trim();
                neirong = etNeirong.getText().toString().trim();

                if (TextUtils.isEmpty(biaoti)) {
                    showToast("请输入标题");
                    return;
                }

                if (TextUtils.isEmpty(leixing)) {
                    showToast("请选择类型");
                    return;
                }


                if (TextUtils.isEmpty(neirong)) {
                    showToast("请输入内容");
                    return;
                }


                ArrayList<File> fileList = getFileList();
                if (fileList != null && !fileList.isEmpty()) {
                    compressImage(fileList);
                } else {
                    Call<BaseResponse<JSON>> call = service.postsave(App.token,communityid,biaoti,intleixing,neirong,
                            "",
                            null
                    );
                    call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, PostedActivity.this) {
                        @Override
                        public void onResponse(Response<BaseResponse<JSON>> response) {
                            BaseResponse<JSON> body = response.body();
                            if (body.isOK()) {
                                finish();
                                showToast(body.message);
                            } else {
                                showToast(body.message);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        communityid = app.readInt("communityid", -1);
        dx_list = new String[]{"失物招领", "杂谈怪论", "保洁需求"};

        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setMaxItemCount(9);
        mPhotosSnpl.setSortable(true);
        mPhotosSnpl.setDelegate(this);
        mPhotosSnpl.isEditable();
        mPhotosSnpl.isPlusEnable();
    }

    //点击设置帖子类型
    @OnClick(R.id.sp_tzlx)
    public void onViewClicked() {
        MySpinner pxSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, dx_list);
        pxSpinner.showAsDropDown(spTzlx, 0, 0);//显示在rl_spinner的下方
        pxSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
            @Override
            public void click(int position, View view) {
                tvTzlx.setText(dx_list[position]);
                leixing = tvTzlx.getText().toString().trim();
                    intleixing = (leixing.equals("失物招领")? 1 : (leixing.equals("杂谈怪论")? 2 : 3));

            }
        });
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
        Call<BaseResponse<JSON>> call = service.postsave(App.token,communityid,biaoti,intleixing,neirong,
                "",
                bpm
        );
        call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    finish();
                    showToast(body.message);
                } else {
                    showToast(body.message);

                }
            }
        });
    }
}
