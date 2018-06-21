package com.wlhb.hongbao.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.app.AppManager;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Bindmobile;
import com.wlhb.hongbao.bean.Wxregist;
import com.wlhb.hongbao.cache.UserManage;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.widget.CountDownTimer;
import com.wlhb.hongbao.wxapi.WXEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/23/023.
 */

public class NumberloginActivity extends BaseActivity {
    @BindView(R.id.ed_account)
    EditText edAccount;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.bt_vcode)
    Button btVcode;
    @BindView(R.id.bt_register)
    Button btRegister;
    private CountDownTimer mTimer;
    private String mMobile;
    private String mCode;
    private String deviceId;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("登录");
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_numberlogin, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取设备ID
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        deviceId = tm.getDeviceId();
    }


    private void startTimer() {
        mTimer = new CountDownTimer(1000, 60 * 1000);
        mTimer.start(new CountDownTimer.CountDownListener() {
            @Override
            public void onStart(final long countTimeMillis) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = countTimeMillis / 1000 + "秒后重试";
                        btVcode.setText(str);
                        btVcode.setEnabled(false);
                        btVcode.setTextColor(getResources().getColor(R.color.gray));
                        btVcode.setBackground(getResources().getDrawable(R.drawable.framebuttongray_bg));
                    }
                });
            }

            @Override
            public void onTick(final long leftTimeMillis) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = leftTimeMillis / 1000 + "秒后重试";
                        btVcode.setText(str);
                        btVcode.setTextColor(getResources().getColor(R.color.gray));
                        btVcode.setBackground(getResources().getDrawable(R.drawable.framebuttongray_bg));
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = "获取验证码";
                        btVcode.setText(str);
                        btVcode.setEnabled(true);
                        btVcode.setTextColor(getResources().getColor(R.color.red));
                        btVcode.setBackground(getResources().getDrawable(R.drawable.framebutton_bg));
                    }
                });
            }
        });
    }

    @OnClick({R.id.bt_vcode, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_vcode:
                //获取手机号
                mMobile = edAccount.getText().toString().trim();
                if (TextUtils.isEmpty(mMobile)) {
                    showToast("请输入手机号");
                    return;
                }

                //发送短信
                Call<BaseResponse<JSON>> call = service.sendsms(mMobile, 4);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            startTimer();
                            showToast(body.message);
                        } else {
                            showToast(body.message);
                        }
                    }
                });
                break;
            case R.id.bt_register:
                register();
                break;
        }
    }

    //注册
    private void register() {
        mMobile = edAccount.getText().toString().trim();
        mCode = edCode.getText().toString().trim();

        if (TextUtils.isEmpty(mMobile)) {
            showToast(edAccount.getHint().toString().trim());
            return;
        }

        if (TextUtils.isEmpty(mCode)) {
            showToast(edCode.getHint().toString().trim());
            return;
        }

        //调用手机号登录接口
        Call<BaseResponse<Wxregist>> call = service.login(mMobile, mCode, deviceId);
        call.enqueue(new BaseCallback<BaseResponse<Wxregist>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Wxregist>> response) {
                BaseResponse<Wxregist> body = response.body();
                if (body.isOK()) {
                    //登录用户信息
                    App.login(body.data);
                    //第二次免登录
                    UserManage.getInstance().saveUserInfo(NumberloginActivity.this, body.data.username, body.data.token);
                    //记录用户ID跳转到主页
                    Intent intent = new Intent(NumberloginActivity.this, MainActivity.class);
                    intent.putExtra("id", body.data.id);
                    startActivity(intent);
                    AppManager.finish(NumberloginActivity.class, EnterActivity.class);
                } else {
                    showToast(body.message);
                }
            }
        });
    }
}

