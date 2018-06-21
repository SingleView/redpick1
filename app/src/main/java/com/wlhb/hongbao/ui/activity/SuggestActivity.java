package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
 * Created by Administrator on 2018/5/30/030.
 */

public class SuggestActivity extends BaseActivity {
    @BindView(R.id.et_jynr)
    EditText etJynr;
    @BindView(R.id.bt_tjgg)
    Button btTjgg;
    private int communityid;
    private String yjnr;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_suggest, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("发布建议");
        //设置标题颜色为蓝色
        setTitleColor(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        communityid = app.readInt("communityid", -1);
    }

    //提交公告
    @OnClick(R.id.bt_tjgg)
    public void onViewClicked() {
        yjnr = etJynr.getText().toString().trim();

        if (TextUtils.isEmpty(yjnr)) {
            showToast(etJynr.getHint().toString().trim());
            return;
        }

        Call<BaseResponse<JSON>> call = service.communitymsg(App.token, yjnr, communityid, 1);
        call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    showToast(body.message);
                    finish();
                } else {
                    showToast(body.message);
                }
            }
        });
    }
}
