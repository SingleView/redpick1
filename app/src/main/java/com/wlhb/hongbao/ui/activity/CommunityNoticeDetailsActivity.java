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
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/3/28/028.
 */

public class CommunityNoticeDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_ggbt)
    TextView tvGgbt;
    @BindView(R.id.tv_ggnr)
    TextView tvGgnr;
    @BindView(R.id.iv_touxiang)
    CircleImageView ivTouxiang;
    @BindView(R.id.ll_sqxx)
    LinearLayout llSqxx;
    private BaseResponse<Messageinfo> body;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_communitynoticedetails, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //社区消息设置标题颜色为蓝色
        setTitleColor(true);
        setTitle("公告详情");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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

    @OnClick(R.id.ll_sqxx)
    public void onViewClicked() {
        if(body.data.type == 1){
            app.writeInt("postid",body.data.noticeId);
            readyGo(InvitationActivity.class);
        }
    }
}
