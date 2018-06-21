package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Favorite;
import com.wlhb.hongbao.bean.Myblack;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class BlacklistActivity extends BaseActivity {
    @BindView(R.id.rv_hmd)
    RecyclerView rvHmd;
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    private BaseQuickAdapter<Myblack.DataListBean, BaseViewHolder> mAdapter;
    private Button huifu;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_blacklist, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("黑名单");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initAdapter();
        //获取黑名单列表
        rvHmd.setLayoutManager(new LinearLayoutManager(this));
        Call<BaseResponse<Myblack>> calltradingdetails = service.myblack(App.token);
        calltradingdetails.enqueue(new BaseCallback<BaseResponse<Myblack>>(calltradingdetails, this) {
            @Override
            public void onResponse(Response<BaseResponse<Myblack>> response) {
                BaseResponse<Myblack> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()) {
                        rlNomore.setVisibility(View.GONE);
                        rvHmd.setVisibility(View.VISIBLE);
                    } else {
                        rvHmd.setVisibility(View.GONE);
                        rlNomore.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setNewData(body.data.dataList);
                    rvHmd.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });

    }

    private void initAdapter() {
        //设置黑名单适配器
        mAdapter = new BaseQuickAdapter<Myblack.DataListBean, BaseViewHolder>(R.layout.item_heimingdan) {

            @Override
            protected void convert(BaseViewHolder helper, final Myblack.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.image, ivTouxiang);
                helper.setText(R.id.tv_neirong, item.personalMark + "");
                helper.setText(R.id.tv_name, item.username + "");
                //点击恢复按钮恢复黑名单
                helper.getView(R.id.bt_huifu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<BaseResponse<JSON>> callremove = service.removeblack(App.token, item.id);
                        callremove.enqueue(new BaseCallback<BaseResponse<JSON>>(callremove, BlacklistActivity.this) {
                            @Override
                            public void onResponse(Response<BaseResponse<JSON>> response) {
                                BaseResponse<JSON> body = response.body();
                                if (body.isOK()) {
                                    init();
                                } else {
                                    showToast(body.message);
                                }
                            }
                        });
                    }
                });

            }
        };
    }
}

