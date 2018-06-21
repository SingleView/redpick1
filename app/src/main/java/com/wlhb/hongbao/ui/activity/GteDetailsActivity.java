package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Receivelists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/4/004.
 */

public class GteDetailsActivity extends BaseActivity {
    @BindView(R.id.rv_lqjl)
    RecyclerView rvLqjl;
    @BindView(R.id.tv_wdlqjl)
    TextView tvWdlqjl;
    @BindView(R.id.tv_lqgs)
    TextView tvLqgs;
    @BindView(R.id.iv_tx)
    CircleImageView ivTx;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_hbzt)
    TextView tvHbzt;
    private BaseQuickAdapter<Receivelists.DataListBean, BaseViewHolder> mAdapter;
    private int packetId;
    private int userId;
    private int quantity;
    private int status;
    private String userName;
    private String image;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_gtedetails, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("红包");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        packetId = app.readInt("packetId", -1);
        userId = app.readInt("id", -1);
        quantity = app.readInt("quantity", -1);
        status = app.readInt("status", -1);
        userName = app.readString("userName", "");
        image = app.readString("image", "");
        rvLqjl.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        initAdapter();
        //获取红包发放状态数据
        Call<BaseResponse<Receivelists>> call = service.receivelists(App.token, packetId);
        call.enqueue(new BaseCallback<BaseResponse<Receivelists>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Receivelists>> response) {
                BaseResponse<Receivelists> body = response.body();
                if (body.isOK()) {
                    mAdapter.setNewData(body.data.dataList);
                    rvLqjl.setAdapter(mAdapter);
                    tvLqgs.setText("已领取" + body.data.dataList.size() + "/" + quantity + "个");
                    tvName.setText(userName + "的红包");
                    showImageTx(image,ivTx);
                    if (status == 0) {
                        tvHbzt.setText("派发中");
                    } else {
                        tvHbzt.setText("已派完");
                    }
                }else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置红包领取列表适配器
        mAdapter = new BaseQuickAdapter<Receivelists.DataListBean, BaseViewHolder>(R.layout.item_lingqvjilu) {

            @Override
            protected void convert(BaseViewHolder helper, final Receivelists.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.toUserImage, ivTouxiang);
                helper.setText(R.id.tv_jine, item.money + "元");
                helper.setText(R.id.tv_name, item.toUserName + "");
                helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp+""));
                helper.getView(R.id.iv_touxiang).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        app.writeInt("id", item.toUserId);
                        readyGo(HomepageActivity.class);
                    }
                });
            }
        };
    }


    @OnClick({R.id.iv_tx, R.id.tv_wdlqjl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tx://点击头像去个人详情
                app.writeInt("id", userId);
                readyGo(HomepageActivity.class);
                break;
            case R.id.tv_wdlqjl://点击去我的红包记录
                readyGo(MyhbDetailsActivity.class);
                break;
        }
    }
}
