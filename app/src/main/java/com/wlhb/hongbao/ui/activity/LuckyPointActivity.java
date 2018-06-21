package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;

/**
 * Created by Administrator on 2018/4/9/009.
 */

//幸运值介绍
public class LuckyPointActivity extends BaseActivity {
    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_luckypoint, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("幸运值");
    }
}
