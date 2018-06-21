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

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.et_yjfk)
    EditText etYjfk;
    private String yjfk;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_feedback, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("意见反馈");
        setTitleRightText("          提 交", new View.OnClickListener() {
            @Override
            //点击提交意见
            public void onClick(View v) {
                //意见反馈内容
                yjfk = etYjfk.getText().toString().trim();
                if (TextUtils.isEmpty(yjfk)) {
                    showToast(etYjfk.getHint().toString().trim());
                    return;
                }

                //调用提交意见接口
                Call<BaseResponse<JSON>> call = service.feedback(App.token,yjfk);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, FeedbackActivity.this) {
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
