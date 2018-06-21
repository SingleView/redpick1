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
 * Created by Administrator on 2018/4/8/008.
 */

public class SignatureActivity extends BaseActivity {
    @BindView(R.id.ed_qianming)
    EditText edQianming;
    private String mQianming;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_signature, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("个性签名");
        setTitleRightText("          提 交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQianming = edQianming.getText().toString().trim();
                if (TextUtils.isEmpty(mQianming)) {
                    showToast(edQianming.getHint().toString().trim());
                    return;
                }

                Call<BaseResponse<JSON>> call = service.edit(App.token,null, mQianming,null,null,null);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, SignatureActivity.this) {
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
