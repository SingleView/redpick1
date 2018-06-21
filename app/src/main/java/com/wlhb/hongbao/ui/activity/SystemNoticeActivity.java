package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.BroadcastData;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/29/029.
 */

public class SystemNoticeActivity extends BaseActivity {
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_xtgg)
    RecyclerView rvXtgg;
    private BaseQuickAdapter<BroadcastData.DataListBean, BaseViewHolder> ggAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_systemnotice, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("系统公告");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initAdapter();
        rvXtgg.setLayoutManager(new LinearLayoutManager(this));
        Call<BaseResponse<BroadcastData>> callbroadcastdata = service.broadcastdata(App.token);
        callbroadcastdata.enqueue(new BaseCallback<BaseResponse<BroadcastData>>(callbroadcastdata, this) {
            @Override
            public void onResponse(Response<BaseResponse<BroadcastData>> response) {
                BaseResponse<BroadcastData> body = response.body();
                if (body.isOK()) {
                    if (body.isOK()) {
                        if (body.data.dataList != null && !body.data.dataList.isEmpty()) {
                            rlNomore.setVisibility(View.GONE);
                            rvXtgg.setVisibility(View.VISIBLE);
                        } else {
                            rlNomore.setVisibility(View.VISIBLE);
                            rvXtgg.setVisibility(View.GONE);
                        }
                        ggAdapter.setNewData(body.data.dataList);
                        rvXtgg.setAdapter(ggAdapter);
                    } else {
                        showToast(body.message);
                    }
                }
            }
        });
    }


    private void initAdapter() {
        ggAdapter = new BaseQuickAdapter<BroadcastData.DataListBean, BaseViewHolder>(R.layout.item_xiaoxi) {

            @Override
            protected void convert(BaseViewHolder helper, final BroadcastData.DataListBean item) {
                helper.setText(R.id.tv_name, item.title);
                helper.setText(R.id.tv_neirong, item.content);
                helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        app.writeString("xtgg",item.content);
                        readyGo(NoticeDetailsActivity.class);
                    }
                });
            }
        };
    }
}
