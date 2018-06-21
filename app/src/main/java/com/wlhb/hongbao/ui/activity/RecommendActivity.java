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
import com.wlhb.hongbao.bean.Recommondlists;
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

public class RecommendActivity extends BaseActivity {
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_tjdpy)
    RecyclerView rvTjdpy;
    private BaseQuickAdapter<Recommondlists.DataListBean, BaseViewHolder> mAdapter;
    private int tjdpy;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_recommend, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("推荐的朋友");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        tjdpy = app.readInt("tjdpy", -1);
        init();
    }

    private void init() {
        initAdapter();
        rvTjdpy.setLayoutManager(new LinearLayoutManager(this));
        //获取推荐的朋友列表
        Call<BaseResponse<Recommondlists>> call = service.recommondlists(App.token,tjdpy);
        call.enqueue(new BaseCallback<BaseResponse<Recommondlists>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Recommondlists>> response) {
                BaseResponse<Recommondlists> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()){
                        rlNomore.setVisibility(View.GONE);
                        rvTjdpy.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvTjdpy.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(body.data.dataList);
                    rvTjdpy.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        mAdapter = new BaseQuickAdapter<Recommondlists.DataListBean, BaseViewHolder>(R.layout.item_tuijiandepengyou) {

            @Override
            protected void convert(BaseViewHolder helper, final Recommondlists.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.image,ivTouxiang);
                helper.setText(R.id.tv_qianming, item.username + "");
                helper.setText(R.id.tv_name, item.personalMark + "");

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.writeInt("id",item.id);
                        readyGo(HomepageActivity.class);
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