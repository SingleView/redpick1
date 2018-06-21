package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/15/015.
 */

public class ComplainActivity extends BaseActivity {

    @BindView(R.id.et_hbts)
    EditText etHbts;
    private String hbts;
    private int packetId;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_complain, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("红包投诉");
        setTitleRightText("          提 交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hbts = etHbts.getText().toString().trim();
                packetId = app.readInt("packetId", -1);
                if (TextUtils.isEmpty(hbts)) {
                    showToast(etHbts.getHint().toString().trim());
                    return;
                }

                //发布红包投诉
                Call<BaseResponse<JSON>> call = service.complain(App.token, hbts,packetId);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, ComplainActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            finish();

                        } else {
                            showToast(body.message);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
