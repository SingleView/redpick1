package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Communitylists;
import com.wlhb.hongbao.bean.Communitymsglists;
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

public class OpinionParticularsActivity extends BaseActivity {
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_yjxq)
    RecyclerView rvYjxq;
    private BaseQuickAdapter<Communitymsglists.DataListBean, BaseViewHolder> sqggAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_opinionparticulars, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("建议列表");
        setTitleColor(true);

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
        //获取查看详情的社区ID
        int communityid = app.readInt("communityid", -1);
        //获取建议详情列表
        Call<BaseResponse<Communitymsglists>> call = service.communitymsglists(App.token,1,communityid);
        call.enqueue(new BaseCallback<BaseResponse<Communitymsglists>>(call,this) {
            @Override
            public void onResponse(Response<BaseResponse<Communitymsglists>> response) {
                BaseResponse<Communitymsglists> body = response.body();
                if (body.isOK()){
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()){
                        rlNomore.setVisibility(View.GONE);
                        rvYjxq.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvYjxq.setVisibility(View.GONE);
                    }
                    sqggAdapter.setNewData(body.data.dataList);
                    rvYjxq.setAdapter(sqggAdapter);

                }
            }
        });
    }

    private void initAdapter() {
        //设置建议详情适配器
        sqggAdapter = new BaseQuickAdapter<Communitymsglists.DataListBean, BaseViewHolder>(R.layout.item_xiaoxi) {

            @Override
            protected void convert(BaseViewHolder helper, final Communitymsglists.DataListBean item) {
                helper.setText(R.id.tv_name, item.name);
                helper.setText(R.id.tv_neirong, item.content);
                helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击去详情
                        app.writeString("jygg", item.content);
                        readyGo(SuggestDetailsActivity.class);
                    }
                });
            }
        };
    }
}
