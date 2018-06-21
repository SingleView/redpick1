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

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.app.AppManager;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Wxregist;
import com.wlhb.hongbao.cache.UserManage;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/3/22/022.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    private String username;
    private String password;
    private IWXAPI mWxApi;
    private String deviceId;


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("登录");
        setTitleBack(false);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        registToWX();

    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 将该app注册到微信
        mWxApi.registerApp(Constants.APP_ID);

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


    @OnClick({R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void login() {
        username = etId.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            showToast("请输入账号");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }

        final Call<BaseResponse<Wxregist>> call = service.login(username, "21980",deviceId);
        call.enqueue(new BaseCallback<BaseResponse<Wxregist>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Wxregist>> response) {
                BaseResponse<Wxregist> body = response.body();
                if (body.isOK()) {
                    //登录用户信息
                    App.login(body.data);
                    //第二次免登录
                    UserManage.getInstance().saveUserInfo(LoginActivity.this, body.data.username, body.data.token);
                    //记录用户ID跳转到主页
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("id", body.data.id);
                    startActivity(intent);
                    AppManager.finish(LoginActivity.class, EnterActivity.class);
                } else {
                    showToast(body.message);
                }
            }
        });
    }
}
