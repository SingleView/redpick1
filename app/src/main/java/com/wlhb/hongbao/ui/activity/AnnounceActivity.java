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
 * Created by Administrator on 2018/4/9/009.
 */

public class AnnounceActivity extends BaseActivity {
    @BindView(R.id.et_ggnr)
    EditText etGgnr;
    @BindView(R.id.bt_tjgg)
    Button btTjgg;
    private String ggnr;
    private int communityid;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_announce, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("发布公告");
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
        ggnr = etGgnr.getText().toString().trim();

        if (TextUtils.isEmpty(ggnr)) {
            showToast(etGgnr.getHint().toString().trim());
            return;
        }

        Call<BaseResponse<JSON>>call = service.communitymsg(App.token,ggnr,communityid,2 );
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
