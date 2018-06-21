package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Recommondcount;
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

public class FriendActivity extends BaseActivity {
    @BindView(R.id.iv_help)
    ImageView ivHelp;
    @BindView(R.id.rl_pywr)
    RelativeLayout rlPywr;
    @BindView(R.id.rl_pysr)
    RelativeLayout rlPysr;
    @BindView(R.id.rl_yqpy)
    RelativeLayout rlYqpy;
    @BindView(R.id.tv_zsy)
    TextView tvZsy;
    @BindView(R.id.tv_tjdpy)
    TextView tvTjdpy;
    @BindView(R.id.tv_tjdpysy)
    TextView tvTjdpysy;
    @BindView(R.id.tv_pydpy)
    TextView tvPydpy;
    @BindView(R.id.tv_pydpysy)
    TextView tvPydpysy;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_friend, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("推荐的朋友");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取推荐的朋友收益情况
        Call<BaseResponse<Recommondcount>> calltradingdetails = service.recommondcount(App.token);
        calltradingdetails.enqueue(new BaseCallback<BaseResponse<Recommondcount>>(calltradingdetails, this) {
            @Override
            public void onResponse(Response<BaseResponse<Recommondcount>> response) {
                BaseResponse<Recommondcount> body = response.body();
                if (body.isOK()) {
                    tvZsy.setText(body.data.total+"");
                    tvTjdpy.setText("朋友"+body.data.owner.total+"人(收益"+body.data.owner.rate+")");
                    tvTjdpysy.setText(body.data.owner.money+"元");
                    tvPydpy.setText("朋友的朋友"+body.data.friend.total+"人(收益"+body.data.friend.rate+")");
                    tvPydpysy.setText(body.data.friend.money+"元");
                }else {
                    showToast(body.message);
                }
            }
        });
    }

    @OnClick({R.id.iv_help, R.id.rl_pywr, R.id.rl_pysr, R.id.rl_yqpy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_help://点击去帮助
                readyGo(HelpActivity.class);
                break;
            case R.id.rl_pywr://点击去推荐的朋友
                app.writeInt("tjdpy",0);
                readyGo(RecommendActivity.class);
                break;
            case R.id.rl_pysr://点击去朋友推荐的朋友
                app.writeInt("tjdpy",1);
                readyGo(RecommendActivity.class);
                break;
            case R.id.rl_yqpy:
                break;
        }
    }
}
