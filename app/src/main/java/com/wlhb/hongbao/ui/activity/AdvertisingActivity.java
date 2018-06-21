package com.wlhb.hongbao.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.PacketInfo;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.widget.CountDownTimer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public class AdvertisingActivity extends BaseActivity implements BGANinePhotoLayout.Delegate {
    @BindView(R.id.tv_shijian)
    TextView tvShijian;
    @BindView(R.id.tv_biaoti)
    TextView tvBiaoti;
    @BindView(R.id.tv_neirong)
    TextView tvNeirong;
    @BindView(R.id.npl_item_moment_photos)
    BGANinePhotoLayout nplItemMomentPhotos;
    private CountDownTimer mTimer;
    private static final int PRC_PHOTO_PREVIEW = 1;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_advertising, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("广告详情");
        setTitleBack(false);
        //禁止广告页侧滑关闭
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        startTimer();
        init();
    }

    //初始化
    private void init() {
        int packetId = app.readInt("packetId", -1);
        nplItemMomentPhotos.setDelegate(AdvertisingActivity.this);
        Call<BaseResponse<PacketInfo>> callpacketinfo = service.packetinfo(App.token, packetId);
        callpacketinfo.enqueue(new BaseCallback<BaseResponse<PacketInfo>>(callpacketinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<PacketInfo>> response) {
                BaseResponse<PacketInfo> body = response.body();
                if (body.isOK()) {
                    tvBiaoti.setText(body.data.title);
                    tvNeirong.setText(body.data.content);
                    nplItemMomentPhotos.setData((ArrayList<String>) body.data.listImg);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    //禁止广告页返回
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    //设置广告时间
    private void startTimer() {
        mTimer = new CountDownTimer(1000, 3 * 1000);
        mTimer.start(new CountDownTimer.CountDownListener() {
            @Override
            public void onStart(final long countTimeMillis) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = countTimeMillis / 1000 + "";
                        tvShijian.setText(str);
                        tvShijian.setEnabled(false);
                    }
                });
            }

            @Override
            public void onTick(final long leftTimeMillis) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = leftTimeMillis / 1000 + "";
                        tvShijian.setText(str);
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //去红包详情
                        readyGo(RedPacketParticularsActivity.class);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        nplItemMomentPhotos = ninePhotoLayout;
        photoPreviewWrapper();
    }

    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(PRC_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (nplItemMomentPhotos == null) {
            return;
        }
        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (nplItemMomentPhotos.getItemCount() == 1) {
                // 预览单张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(this, downloadDir, nplItemMomentPhotos.getCurrentClickItem()));
            } else if (nplItemMomentPhotos.getItemCount() > 1) {
                // 预览多张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(this, downloadDir, nplItemMomentPhotos.getData(), nplItemMomentPhotos.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
        }
    }
}
