package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Everydaycount;
import com.wlhb.hongbao.bean.Everydaytask;
import com.wlhb.hongbao.bean.Favorite;
import com.wlhb.hongbao.bean.Pay;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/29/029.
 */

public class MissionActivity extends BaseActivity {
    @BindView(R.id.tv_ylqhb)
    TextView tvYlqhb;
    @BindView(R.id.tv_hbsx)
    TextView tvHbsx;
    @BindView(R.id.rv_renwu)
    RecyclerView rvRenwu;

    private BaseQuickAdapter<Everydaytask, BaseViewHolder> mAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_mission, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("任务");
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
        rvRenwu.setLayoutManager(new LinearLayoutManager(this));
        //获取红包领取情况
        Call<BaseResponse<Everydaycount>> call = service.everydaycount(App.token);
        call.enqueue(new BaseCallback<BaseResponse<Everydaycount>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Everydaycount>> response) {
                BaseResponse<Everydaycount> body = response.body();
                if (body.isOK()) {
                    //已领取的红包
                    tvYlqhb.setText(body.data.recevieNum+"");
                    //今日红包可领取总量
                    tvHbsx.setText(body.data.totalNum+"");
                } else {
                    showToast(body.message);
                }
            }
        });

        //获取任务列表数据
        Call<BaseResponse<List<Everydaytask>>> calleverydaytask = service.everydaytask(App.token);
        calleverydaytask.enqueue(new BaseCallback<BaseResponse<List<Everydaytask>>>(calleverydaytask, this) {
            @Override
            public void onResponse(Response<BaseResponse<List<Everydaytask>>> response) {
                BaseResponse<List<Everydaytask>> body = response.body();
                if (body.isOK()) {
                    mAdapter.setNewData(body.data);
                    rvRenwu.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置任务列表适配器
        mAdapter = new BaseQuickAdapter<Everydaytask, BaseViewHolder>(R.layout.item_renwu) {

            @Override
            protected void convert(BaseViewHolder helper, final Everydaytask item) {
                ImageView ivTouxiang = helper.getView(R.id.iv_pengyouquan);
                showImageTx(item.image,ivTouxiang);
                helper.setText(R.id.tv_title, item.title + "");
                helper.setText(R.id.tv_cishu, item.remark + "+"+item.quantity+"次红包领取次数");

                helper.getView(R.id.bt_gomission).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //任务类型为1时去发红包,2时去抢红包然后分享红包,3时去分享界面分享APP
                       if (item.type == 1){
                           readyGo(RedPacketInformationActivity.class);
                       }else if(item.type == 2){
                           readyGo(MainActivity.class);
                        }else {
                           readyGo(ShareActivity.class);
                       }
                    }
                });
            }
        };
    }
}
