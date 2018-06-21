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
import com.wlhb.hongbao.bean.Communitymsglists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/30/030.
 */

public class CommunityAnnouncementActivity extends BaseActivity {
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_sqgg)
    RecyclerView rvSqgg;
    private int communityid;
    private BaseQuickAdapter<Communitymsglists.DataListBean, BaseViewHolder> sqggAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_communityannouncement, container, false);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("社区公告");
        setTitleColor(true);
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
        communityid = app.readInt("communityid", -1);
        rvSqgg.setLayoutManager(new LinearLayoutManager(this));
        Call<BaseResponse<Communitymsglists>> callcommunitymsg = service.communitymsglists(App.token, 2, communityid);
        callcommunitymsg.enqueue(new BaseCallback<BaseResponse<Communitymsglists>>(callcommunitymsg, this) {
            @Override
            public void onResponse(Response<BaseResponse<Communitymsglists>> response) {
                BaseResponse<Communitymsglists> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()) {
                        rlNomore.setVisibility(View.GONE);
                        rvSqgg.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvSqgg.setVisibility(View.GONE);
                    }
                    sqggAdapter.setNewData(body.data.dataList);
                    rvSqgg.setAdapter(sqggAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }


    private void initAdapter() {
        sqggAdapter = new BaseQuickAdapter<Communitymsglists.DataListBean, BaseViewHolder>(R.layout.item_xiaoxi) {

            @Override
            protected void convert(BaseViewHolder helper, final Communitymsglists.DataListBean item) {
                helper.setText(R.id.tv_name, item.name);
                helper.setText(R.id.tv_neirong, item.content);
                helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        app.writeInt("msgid", item.id);
                        app.writeInt("type", 1);
                        readyGo(CommunityNoticeDetailsActivity.class);
                    }
                });
            }
        };
    }
}
