package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/13/013.
 */

public class MyCommunity extends BaseActivity {
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_wdsq)
    RecyclerView rvWdsq;
    private BaseQuickAdapter<Communitylists.DataListBean, BaseViewHolder> sheqvAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_mycommunity, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("我的社区");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        rvWdsq.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        //获取我的社区列表数据
        Call<BaseResponse<Communitylists>> callcommunitylists = service.communitylists(App.token,null,null);
        callcommunitylists.enqueue(new BaseCallback<BaseResponse<Communitylists>>(callcommunitylists, this) {
            @Override
            public void onResponse(Response<BaseResponse<Communitylists>> response) {
                BaseResponse<Communitylists> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()){
                        rlNomore.setVisibility(View.GONE);
                        rvWdsq.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvWdsq.setVisibility(View.GONE);
                    }
                    sheqvAdapter.setNewData(body.data.dataList);
                    rvWdsq.setAdapter(sheqvAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置我的社区列表适配器
        sheqvAdapter = new BaseQuickAdapter<Communitylists.DataListBean, BaseViewHolder>(R.layout.item_sheqv) {

            @Override
            protected void convert(final BaseViewHolder helper, Communitylists.DataListBean item) {
                ImageView tubiao = helper.getView(R.id.iv_sqtouxiang);
                showImage(item.image, tubiao);
                helper.setText(R.id.tv_sqname, item.name + "");
                helper.setText(R.id.tv_sqneirong, item.content + "");
                helper.setText(R.id.cb_sqrs, " "+item.memberNum + "");
                helper.setText(R.id.cb_sqnan, " "+item.manNum + "");
                helper.setText(R.id.cb_sqnv, " "+item.womenNUm + "");
                helper.setText(R.id.cb_sqtz, " "+item.contentNum + "");
            }
        };
        sheqvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击社区条目,进入当前条目的社区
                Communitylists.DataListBean item = (Communitylists.DataListBean) adapter.getItem(position);
                app.writeInt("communityid",item.id);
                readyGo(CommunitylistActivity.class);
            }
        });
    }
}
