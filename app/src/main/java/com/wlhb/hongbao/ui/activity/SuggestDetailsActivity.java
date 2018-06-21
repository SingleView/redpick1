package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/30/030.
 */

public class SuggestDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_jynr)
    TextView tvJynr;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_suggestdetails, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置标题颜色为蓝色
        setTitleColor(true);
        setTitle("建议详情");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String jygg = app.readString("jygg", "");
        tvJynr.setText(jygg);
    }
}
