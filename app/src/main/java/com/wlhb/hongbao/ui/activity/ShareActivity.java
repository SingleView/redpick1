package com.wlhb.hongbao.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.pay.wxpay.Constants;
import com.wlhb.hongbao.utils.Util;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class ShareActivity extends BaseActivity {
    @BindView(R.id.rl_yqhy)
    RelativeLayout rlYqhy;
    @BindView(R.id.rl_fxpyq)
    RelativeLayout rlFxpyq;
    @BindView(R.id.rl_scewm)
    RelativeLayout rlScewm;
    private IWXAPI api;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_share, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("分享");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
    }

    @OnClick({R.id.rl_yqhy, R.id.rl_fxpyq, R.id.rl_scewm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_yqhy:
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
                break;
            case R.id.rl_fxpyq:
                WXWebpageObject wxWebpageObject2 = new WXWebpageObject();
                wxWebpageObject2.webpageUrl = "http://www.blbuy.com.cn/hongbao/share/shareapp?inviteCode=1004621519";
                WXMediaMessage wxMediaMessage2 = new WXMediaMessage(wxWebpageObject2);
                wxMediaMessage2.title = "小圆";
                wxMediaMessage2.description = "网领红包";
                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                wxMediaMessage2.thumbData = Util.bmpToByteArray(bitmap2,true);

                SendMessageToWX.Req req2 = new SendMessageToWX.Req();
                req2.transaction = buildTransaction("text");
                req2.message = wxMediaMessage2;
                req2.scene = SendMessageToWX.Req.WXSceneTimeline ;//朋友圈
                api.sendReq(req2);
                break;
            case R.id.rl_scewm:
                readyGo(QRcodeActivity.class);
                break;
        }
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
