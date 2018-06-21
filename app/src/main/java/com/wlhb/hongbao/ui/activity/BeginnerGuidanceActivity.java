package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4/004.
 */

public class BeginnerGuidanceActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_beginnerguidance, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("新手引导");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //加载帮助中心传过来的网页
        String showLink = app.readString("showLink", "");
        webView.loadUrl(showLink);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
