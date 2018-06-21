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
import com.wlhb.hongbao.bean.Dynamic;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class DynamicActivity extends BaseActivity {
    @BindView(R.id.rv_hydt)
    RecyclerView rvHydt;
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_dynamic, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("动态");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        rvHydt.setLayoutManager(new LinearLayoutManager(this));
        //获取动态列表
        final Call<BaseResponse<Dynamic>> call = service.dynamic(App.token);
        call.enqueue(new BaseCallback<BaseResponse<Dynamic>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Dynamic>> response) {
                BaseResponse<Dynamic> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()) {
                        rlNomore.setVisibility(View.GONE);
                        rvHydt.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvHydt.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(body.data.dataList);
                    rvHydt.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    //设置红包动态适配器
    BaseQuickAdapter mAdapter = new BaseQuickAdapter<Dynamic.DataListBean, BaseViewHolder>(R.layout.item_haoyoudongtai) {

        @Override
        protected void convert(BaseViewHolder helper, final Dynamic.DataListBean item) {
            helper.setText(R.id.tv_name, item.fromUserName);
            CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
            showImageTx(item.fromUserImage, ivTouxiang);
            helper.setText(R.id.tv_neirong, item.content);
            helper.setText(R.id.tv_shijian, "" + TimeUtils.timeslash(item.tstamp + ""));
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击进入红包详情
                    app.writeInt("packetId", item.packetId);
                    readyGo(RedPacketParticularsActivity.class);
                }
            });
        }
    };
}
