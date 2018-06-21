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
import com.wlhb.hongbao.bean.Favorite;
import com.wlhb.hongbao.bean.Help;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/23/023.
 */

public class HelpCenterActivity extends BaseActivity {


    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_help)
    RecyclerView rvHelp;
    private BaseQuickAdapter<Help, BaseViewHolder> mAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_helpcenter, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("帮助中心");
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
        rvHelp.setLayoutManager(new LinearLayoutManager(this));
        //获取帮助中心条目数据
        Call<BaseResponse<List<Help>>> call = service.help();
        call.enqueue(new BaseCallback<BaseResponse<List<Help>>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<List<Help>>> response) {
                BaseResponse<List<Help>> body = response.body();
                if (body.isOK()) {
                    if (body.data != null && !body.data.isEmpty()) {
                        rlNomore.setVisibility(View.GONE);
                    } else {
                        rvHelp.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(body.data);
                    rvHelp.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置帮助中心条目数据适配器
        mAdapter = new BaseQuickAdapter<Help, BaseViewHolder>(R.layout.item_help) {

            @Override
            protected void convert(BaseViewHolder helper, final Help item) {
                helper.setText(R.id.tv_helpone, item.title + "");
                helper.setText(R.id.tv_helponecontent, item.remark + "");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击去帮助详情网页
                        app.writeString("showLink",item.showLink);
                        readyGo(BeginnerGuidanceActivity.class);
                    }
                });
            }
        };
    }
}

