package com.wlhb.hongbao.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;

import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.cache.UserManage;
import com.wlhb.hongbao.constant.Constant;
import com.wlhb.hongbao.widget.CountDownTimer;


/**
 * Created by Administrator on 2018/3/22/022.
 */

public class StartActivity extends BaseActivity {

    private static final int GO_HOME = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页

    /**
     * 跳转判断
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(StartActivity.this, GuideActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_start, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleBar(false);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (UserManage.getInstance().hasUserInfo(this) && App.token != null)//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
        }
    }
}
