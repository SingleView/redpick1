package com.wlhb.hongbao.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Upload;
import com.wlhb.hongbao.bean.UserInfo;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.Constants;
import com.wlhb.hongbao.utils.Base64BitmapUtil;
import com.wlhb.hongbao.utils.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/29/029.
 */

public class PersonalActivity extends BaseActivity {
    @BindView(R.id.rl_touxiang)
    RelativeLayout rlTouxiang;
    @BindView(R.id.rl_nicheng)
    RelativeLayout rlNicheng;
    @BindView(R.id.rl_qianming)
    RelativeLayout rlQianming;
    @BindView(R.id.rl_erweima)
    RelativeLayout rlErweima;
    @BindView(R.id.rl_xingbie)
    RelativeLayout rlXingbie;
    @BindView(R.id.rl_diqv)
    RelativeLayout rlDiqv;
    @BindView(R.id.rl_shoujihao)
    RelativeLayout rlShoujihao;
    @BindView(R.id.rl_fenxiang)
    RelativeLayout rlFenxiang;
    @BindView(R.id.iv_txx)
    CircleImageView ivTxx;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_qianming)
    TextView tvQianming;
    @BindView(R.id.tv_xingbie)
    TextView tvXingbie;
    @BindView(R.id.tv_dizhi)
    TextView tvDizhi;
    @BindView(R.id.tv_shoujihao)
    TextView tvShoujihao;


    private Dialog dialog;
    private static final int REQUEST_IMAGE_CAPTURE = 100;  //request Code for camera
    private static final int REQUEST_IMAGE_SELECT = 200;   //request Code for album
    public static final int MEDIA_TYPE_IMAGE = 1;  //choose camera image type

    private Uri fileUri;  //用于存储相机拍摄的照片
    private Bitmap bmp;
    private TextView tv_camera;
    private TextView tv_gallery;
    private String toBase64;
    private TextView tv_haoyou;
    private TextView tv_pyq;
    private IWXAPI api;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_personal, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("个人信息");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
    }

    private void init() {
        //获取个人信息
        Call<BaseResponse<JSON>> callinfo = service.info(App.token, App.id);
        callinfo.enqueue(new BaseCallback<BaseResponse<JSON>>(callinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    JSON data = body.data;
                    UserInfo userInfo = JSONObject.toJavaObject(data, UserInfo.class);
                    showImageTx(userInfo.image, ivTxx);
                    tvName.setText(userInfo.nickname);
                    tvQianming.setText(userInfo.personalMark);
                    tvXingbie.setText(userInfo.gender == 0 ? "保密" : (userInfo.gender == 1 ? "男" : "女"));
                    tvDizhi.setText(userInfo.address);
                    if(TextUtils.isEmpty(userInfo.mobile)){
                        tvShoujihao.setText("未绑定");
                    }else {
                        tvShoujihao.setText(userInfo.mobile);
                    }
                }
            }
        });
    }

    @OnClick({R.id.rl_touxiang, R.id.rl_nicheng, R.id.rl_qianming, R.id.rl_erweima, R.id.rl_xingbie, R.id.rl_diqv, R.id.rl_shoujihao, R.id.rl_fenxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_touxiang://点击设置头像
                showdialog();
                break;
            case R.id.rl_nicheng://设置昵称
                readyGo(ChangenicknameActivity.class);
                break;
            case R.id.rl_qianming://设置签名
                readyGo(SignatureActivity.class);
                break;
            case R.id.rl_erweima://二维码
                readyGo(QRcodeActivity.class);
                break;
            case R.id.rl_xingbie://设置性别
                readyGo(ChangeGenderActivity.class);
                break;
            case R.id.rl_diqv://设置地址
                readyGo(SetAdressActivity.class);
                break;
            case R.id.rl_shoujihao://绑定手机号
                readyGo(BindingActivity.class);
                break;
            case R.id.rl_fenxiang://分享
                showdialogs();
                break;
        }
    }

    //分享对话框
    private void showdialogs() {
        View localView = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_share, null);
        tv_haoyou = (TextView) localView.findViewById(R.id.tv_camera);
        tv_pyq = (TextView) localView.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_haoyou.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                WXWebpageObject wxWebpageObject = new WXWebpageObject();
                wxWebpageObject.webpageUrl = "http://www.blbuy.com.cn/hongbao/share/shareapp?inviteCode=1004621519";
                WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
                wxMediaMessage.title = "小圆";
                wxMediaMessage.description = "网领红包";
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                wxMediaMessage.thumbData = Util.bmpToByteArray(bitmap,true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("text");
                req.message = wxMediaMessage;
                req.scene = SendMessageToWX.Req.WXSceneSession;//好友
                api.sendReq(req);
            }
        });

        tv_pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                WXWebpageObject wxWebpageObject = new WXWebpageObject();
                wxWebpageObject.webpageUrl = "http://www.blbuy.com.cn/hongbao/share/shareapp?inviteCode=1004621519";
                WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
                wxMediaMessage.title = "小圆";
                wxMediaMessage.description = "网领红包";
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                wxMediaMessage.thumbData = Util.bmpToByteArray(bitmap,true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("text");
                req.message = wxMediaMessage;
                req.scene = SendMessageToWX.Req.WXSceneTimeline ;//朋友圈
                api.sendReq(req);
            }
        });
    }

    //拍照&相册对话框
    public void showdialog() {
        View localView = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_picture, null);
        tv_camera = (TextView) localView.findViewById(R.id.tv_camera);
        tv_gallery = (TextView) localView.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 拍照
                camera();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                gallery();
            }
        });
    }

    //获取当前时间
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //调用相册
    private void gallery() {
        initPrediction();
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //从相册选取一张图片
        startActivityForResult(i, REQUEST_IMAGE_SELECT);
    }

    //调用相机
    private void camera() {
        initPrediction();
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); //得到存储地址的Uri
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //此action表示进行拍照
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  //指定图片的输出地址
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_IMAGE_CAPTURE || requestCode == REQUEST_IMAGE_SELECT) && resultCode == RESULT_OK) {
            String imgPath;

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                imgPath = fileUri.getPath(); //取得拍照存储的地址
            } else { //解析得到所选相册图片的地址
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
            }

            bmp = BitmapFactory.decodeFile(imgPath); //将所选图片提取为bitmap格式

            toBase64 = Base64BitmapUtil.bitmapToBase64(bmp);
            app.writeString("toBase64", toBase64);

            //获取从相册和相机返回的图片并设置上传
            Call<BaseResponse<Upload>> call = service.upload(App.token, toBase64);
            call.enqueue(new BaseCallback<BaseResponse<Upload>>(call, this) {
                @Override
                public void onResponse(Response<BaseResponse<Upload>> response) {
                    BaseResponse<Upload> body = response.body();
                    if (body.isOK()) {
                        app.writeString("touxiang", body.data.image);
                        showImageTx(body.data.image, ivTxx);
                    } else {
                        showToast(body.message);
                    }
                }
            });


            ivTxx.setImageBitmap(bmp); //show image
        }
        tv_camera.setEnabled(true);
        tv_gallery.setEnabled(true);
    }

    private void initPrediction() {
        tv_camera.setEnabled(false);
        tv_gallery.setEnabled(false);
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // 首先检测外部SDCard是否存在并且可读可写
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Caffe-Android-Demo");
        // 如果存储路径不存在，则创建
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    //返回时更新数据
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}

