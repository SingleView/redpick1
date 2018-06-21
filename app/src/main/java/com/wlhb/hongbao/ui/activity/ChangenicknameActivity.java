package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

public class ChangenicknameActivity extends BaseActivity {
    @BindView(R.id.tv_nicheng)
    EditText edNicheng;
    private String mNicheng;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_changenickname, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("修改昵称");
        setTitleRightText("          提 交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNicheng = edNicheng.getText().toString().trim();
                if (TextUtils.isEmpty(mNicheng)) {
                    showToast(edNicheng.getHint().toString().trim());
                    return;
                }

                finish();

                //编辑昵称
                Call<BaseResponse<JSON>> call = service.edit(App.token, mNicheng, null, null, null, null);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, ChangenicknameActivity.this) {
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
}