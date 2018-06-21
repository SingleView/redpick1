package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class InformationActivity extends BaseActivity {
    @BindView(R.id.cb_kq)
    CheckBox cbKq;
    @BindView(R.id.rl_kq)
    RelativeLayout rlKq;
    @BindView(R.id.cb_mdr)
    CheckBox cbMdr;
    @BindView(R.id.rl_mdr)
    RelativeLayout rlMdr;
    @BindView(R.id.cb_gb)
    CheckBox cbGb;
    @BindView(R.id.rl_gb)
    RelativeLayout rlGb;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_information, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("通知");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.rl_kq, R.id.rl_mdr,R.id.rl_gb})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.rl_kq://开启通知
                if (cbKq.isChecked()) {
                    cbKq.setChecked(false);
                } else {
                    cbKq.setChecked(true);
                    cbMdr.setChecked(false);
                    cbGb.setChecked(false);
                    setPushType(1);
                }
                break;

            case R.id.rl_mdr://夜间免打扰
                if (cbMdr.isChecked()) {
                    cbMdr.setChecked(false);
                } else {
                    cbMdr.setChecked(true);
                    cbKq.setChecked(false);
                    cbGb.setChecked(false);
                    setPushType(2);
                }
                break;

            case R.id.rl_gb://关闭通知
                if (cbGb.isChecked()) {
                    cbGb.setChecked(false);
                } else {
                    cbGb.setChecked(true);
                    cbMdr.setChecked(false);
                    cbKq.setChecked(false);
                    setPushType(0);
                }
                break;
        }
    }

    //设置通知状态
    private void setPushType(int i) {
        Call<BaseResponse<JSON>> call = service.edit(App.token, null,null,null,null,i);
        call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, InformationActivity.this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {

                } else {
                    showToast(body.message);
                }
            }
        });
    }


}
