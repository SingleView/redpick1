package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Normalreg;
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

//登录界面
public class EnterActivity extends BaseActivity {

    @BindView(R.id.bt_wxlogin)
    RelativeLayout btWxlogin;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_yhxy)
    TextView tvYhxy;
    @BindView(R.id.bt_ptlogin)
    RelativeLayout btPtlogin;
    @BindView(R.id.bt_zhuce)
    RelativeLayout btZhuce;
    @BindView(R.id.ll_ptdl)
    LinearLayout llPtdl;
    private IWXAPI mWxApi;


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_enter, container, false);
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
        Call<BaseResponse<Normalreg>> callcatelists = service.normalreg();
        callcatelists.enqueue(new BaseCallback<BaseResponse<Normalreg>>(callcatelists, this) {
            @Override
            public void onResponse(Response<BaseResponse<Normalreg>> response) {
                BaseResponse<Normalreg> body = response.body();
                if (body.isOK()) {
                    if (body.data.normalReg == 0){
                        llPtdl.setVisibility(View.GONE);
                    }
                } else {
                    showToast(body.message);
                }
            }
        });
        registToWX();

    }

    //注册微信
    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 将该app注册到微信
        mWxApi.registerApp(Constants.APP_ID);
    }


    @OnClick({R.id.bt_wxlogin, R.id.bt_login, R.id.tv_yhxy, R.id.bt_ptlogin, R.id.bt_zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_wxlogin://微信登录
                wxLogin();
                break;
            case R.id.bt_login://手机号登录
                readyGo(NumberloginActivity.class);
                break;
            case R.id.tv_yhxy://用户协议
                readyGo(AgreementActivity.class);
                break;
            case R.id.bt_ptlogin:
                readyGo(LoginActivity.class);
                break;
            case R.id.bt_zhuce:
                readyGo(RegisterActivity.class);
                break;
        }
    }

    //微信登录
    public void wxLogin() {
        if (!mWxApi.isWXAppInstalled()) {
            showToast("您还未安装微信客户端,请先安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_微信登录";
        mWxApi.sendReq(req);
    }

}
