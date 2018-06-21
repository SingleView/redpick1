package com.wlhb.hongbao.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.widget.CountDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/3/22/022.
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.ed_account)
    EditText edAccount;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.bt_vcode)
    Button btVcode;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    @BindView(R.id.ed_confirmpassword)
    EditText edConfirmpassword;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.bt_register)
    Button btRegister;
    private CountDownTimer mTimer;
    private String mMobile;
    private String mCode;
    private String password;
    private String rePassword;
    private int agreement;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("注册");
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_register, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvAgreement.getPaint().setAntiAlias(true);//抗锯齿
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

    @OnClick({R.id.bt_vcode, R.id.tv_agreement, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_vcode:
                mMobile = edAccount.getText().toString().trim();
                if (TextUtils.isEmpty(mMobile)) {
                    showToast("请输入手机号");
                    return;
                }

                Call<BaseResponse<JSON>> call = service.sendsms(mMobile, 1);
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
            case R.id.tv_agreement:
                readyGo(AgreementActivity.class);
                break;
            case R.id.bt_register:
                register();
                break;
        }
    }

    private void register() {
        mMobile = edAccount.getText().toString().trim();
        mCode = edCode.getText().toString().trim();
        password = edPwd.getText().toString().trim();
        rePassword = edConfirmpassword.getText().toString().trim();


        if (TextUtils.isEmpty(mMobile)) {
            showToast(edAccount.getHint().toString().trim());
            return;
        }

        if (TextUtils.isEmpty(mCode)) {
            showToast(edCode.getHint().toString().trim());
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast(edPwd.getHint().toString().trim());
            return;
        }

        if (TextUtils.isEmpty(rePassword)) {
            showToast(edConfirmpassword.getHint().toString().trim());
            return;
        }

        if (!password.equals(rePassword)) {
            showToast("两次密码不一致");
            return;
        }

        if (!cbCheck.isChecked()) {
            showToast("请同意注册协议");
            return;
        }else {
            agreement = 1;
        }

        Call<BaseResponse<JSON>> call = service.register(mMobile, password, rePassword, mCode,agreement);
        call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    readyGo(LoginActivity.class);
                    finish();

                } else {
                    showToast(body.message);
                }
            }
        });
    }
}
