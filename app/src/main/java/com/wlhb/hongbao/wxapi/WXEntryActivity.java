package com.wlhb.hongbao.wxapi;


import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.IViewController;
import com.wlhb.hongbao.bean.Wxregist;
import com.wlhb.hongbao.cache.UserManage;
import com.wlhb.hongbao.http.APIService;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.http.Http;
import com.wlhb.hongbao.pay.wxpay.Constants;
import com.wlhb.hongbao.ui.activity.MainActivity;
import com.wlhb.hongbao.utils.OkHttpUtils;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Response;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler , IViewController {

    /**
     * 微信登录相关
     */
    protected APIService service;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private String registrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.api.handleIntent(getIntent(), this);
        service = Http.getService();
        registrationId = JPushInterface.getRegistrationID(this); //获取极光注册ID
    }


    /***
     * 请求微信的相应码
     * @author YOLANDA
     * @param
     */
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == baseResp.getType()){
                    finish();
                }
                else {
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN: //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) baseResp).code;
                        getAccessToken(code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);//跳转到主页
                        startActivity(intent);
                        break;
                } break;
        }
    }

    /**
     * 微信主动请求我们
     **/
    @Override
    public void onReq(BaseReq baseResp) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void getAccessToken(String code) {
        //获取授权,及返回的相关参数
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(Constants.APP_ID)
                .append("&secret=")
                .append(Constants.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String accessToken = null;
                String expiresIn = null;
                String refreshToken = null;
                String openId = null;
                String scope = null;
                String unionid = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    accessToken = jsonObject.getString("access_token");
                    expiresIn = jsonObject.getString("expires_in");
                    refreshToken = jsonObject.getString("refresh_token");
                    openId = jsonObject.getString("openid");
                    scope = jsonObject.getString("scope");
                    unionid = jsonObject.getString("unionid");
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
                //微信注册
                Call<BaseResponse<Wxregist>> callwxregist = service.wxregist(accessToken, expiresIn, refreshToken, openId, scope, unionid,registrationId);
                callwxregist.enqueue(new BaseCallback<BaseResponse<Wxregist>>(callwxregist, WXEntryActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<Wxregist>> response) {
                        BaseResponse<Wxregist> body = response.body();
                        if (body.isOK()) {
                            App.login(body.data);
                            UserManage.getInstance().saveUserInfo(WXEntryActivity.this, body.data.username, body.data.token);
                            Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);//跳转到主页
                            intent.putExtra("id", body.data.id);
                            intent.putExtra("phonemsg", body.message);
                            startActivity(intent);
                            finish();
                    }
                }
            });
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        OkHttpUtils.get(loginUrl.toString(), resultCallback);
    }


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}