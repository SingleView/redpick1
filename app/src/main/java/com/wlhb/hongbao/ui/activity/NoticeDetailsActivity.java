package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Messageinfo;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/3/28/028.
 */

public class NoticeDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_ggnr)
    TextView tvGgnr;
    @BindView(R.id.tv_ggbt)
    TextView tvGgbt;
    @BindView(R.id.ll_ggxq)
    LinearLayout llGgxq;
    private BaseResponse<Messageinfo> body;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_noticedetails, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("公告详情");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        initData();
    }

    private void initData() {
        int msgid = app.readInt("msgid", -1);
        int type = app.readInt("type", -1);
        Call<BaseResponse<Messageinfo>> call = service.messageinfo(App.token, type, msgid);
        call.enqueue(new BaseCallback<BaseResponse<Messageinfo>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Messageinfo>> response) {
                body = response.body();
                if (body.isOK()) {
                    tvGgbt.setText(body.data.title);
                    tvGgnr.setText(body.data.content);
                }
            }
        });
    }


    @OnClick(R.id.ll_ggxq)
    public void onViewClicked() {
        if(body.data.type == 1){
            app.writeInt("packetId",body.data.noticeId);
            readyGo(RedPacketParticularsActivity.class);
        }
    }
}
