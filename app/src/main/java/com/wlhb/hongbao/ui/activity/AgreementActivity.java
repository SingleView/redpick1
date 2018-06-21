package com.wlhb.hongbao.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;

/**
 * Created by Administrator on 2018/3/28/028.
 */

public class AgreementActivity extends BaseActivity {
    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_agreement, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("注册协议");
    }


}
