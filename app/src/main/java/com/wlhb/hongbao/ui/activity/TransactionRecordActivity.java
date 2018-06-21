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
import com.wlhb.hongbao.bean.Tradingdetails;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/9/009.
 */

public class TransactionRecordActivity extends BaseActivity {
    @BindView(R.id.rv_jyjl)
    RecyclerView rvJyjl;
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    private BaseQuickAdapter<Tradingdetails.DataListBean, BaseViewHolder> mAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_transactionrecord, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("交易记录");
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
        rvJyjl.setLayoutManager(new LinearLayoutManager(this));
        Call<BaseResponse<Tradingdetails>> calltradingdetails = service.tradingdetails(App.token);
        calltradingdetails.enqueue(new BaseCallback<BaseResponse<Tradingdetails>>(calltradingdetails, this) {
            @Override
            public void onResponse(Response<BaseResponse<Tradingdetails>> response) {
                BaseResponse<Tradingdetails> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()){
                        rlNomore.setVisibility(View.GONE);
                        rvJyjl.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvJyjl.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(body.data.dataList);
                    rvJyjl.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<Tradingdetails.DataListBean, BaseViewHolder>(R.layout.item_jiaoyijilu) {

            @Override
            protected void convert(BaseViewHolder helper, final Tradingdetails.DataListBean item) {
                helper.setText(R.id.tv_name, item.title);
                helper.setText(R.id.tv_shijian,  TimeUtils.timeslash(item.tstamp+""));
                if (item.title.equals("发红包")){
                    helper.setText(R.id.tv_jine, "-"+item.money + "元");
                }else {
                    helper.setText(R.id.tv_jine, item.money + "元");
                }

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.writeInt("packetId", item.packetId);
                        readyGo(RedPacketParticularsActivity.class);
                    }
                });
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
