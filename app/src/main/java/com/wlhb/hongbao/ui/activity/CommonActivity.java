package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class CommonActivity extends BaseActivity {
    @BindView(R.id.rl_cc)
    RelativeLayout rlCc;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_common, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("通用");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rl_cc)
    public void onViewClicked() {
        readyGo(CommonSaveActivity.class);
        finish();
    }
}
