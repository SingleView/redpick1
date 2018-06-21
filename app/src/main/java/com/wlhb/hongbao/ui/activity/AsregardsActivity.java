package com.wlhb.hongbao.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.About;
import com.wlhb.hongbao.bean.BroadcastData;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/9/009.
 */

public class AsregardsActivity extends BaseActivity {

    @BindView(R.id.webView1)
    WebView mWebview;




    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_asregards, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("关于网领");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        //加载关于网领HTML信息
        Call<BaseResponse<String>> call = service.about();
        call.enqueue(new BaseCallback<BaseResponse<String>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<String>> response) {
                BaseResponse<String> body = response.body();
                if (body.isOK()) {
                    String data = body.data;
                    mWebview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
                } else {

                }
            }
        });
    }

    //设置不用系统浏览器打开,直接显示在当前Webview
    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}

