package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by Administrator on 2018/4/9/009.
 */

public class DrawingsActivity extends BaseActivity {
    @BindView(R.id.et_txje)
    EditText etTxje;
    @BindView(R.id.bt_tix)
    Button btTix;
    @BindView(R.id.tv_ktxje)
    TextView tvKtxje;
    private String amount;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_drawings, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("提现");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        amount = getIntent().getStringExtra("amount");
        tvKtxje.setText("可提现金额"+amount+"元");
    }

    //点击提现
    @OnClick(R.id.bt_tix)
    public void ontxViewClicked() {
        String jine = etTxje.getText().toString().trim();
        if (TextUtils.isEmpty(jine)){
            showToast("请输入提现金额");
            return;
        }

        Call<BaseResponse<JSON>> call = service.withdrawal(App.token, jine);
        call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    finish();
                    showToast(body.message);
                } else {
                    showToast(body.message);
                }
            }
        });

    }
}
