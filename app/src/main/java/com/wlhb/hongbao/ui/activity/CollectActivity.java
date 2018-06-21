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
import com.wlhb.hongbao.bean.Tradingdetails;
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

public class CollectActivity extends BaseActivity {
    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_wdsc)
    RecyclerView rvWdsc;
    private BaseQuickAdapter<Favorite.DataListBean, BaseViewHolder> mAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_collect, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("收藏");
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
        rvWdsc.setLayoutManager(new LinearLayoutManager(this));
        //获取红包收藏列表
        Call<BaseResponse<Favorite>> calltradingdetails = service.favorite(App.token);
        calltradingdetails.enqueue(new BaseCallback<BaseResponse<Favorite>>(calltradingdetails, this) {
            @Override
            public void onResponse(Response<BaseResponse<Favorite>> response) {
                BaseResponse<Favorite> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()){
                        rlNomore.setVisibility(View.GONE);
                        rvWdsc.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvWdsc.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(body.data.dataList);
                    rvWdsc.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置红包收藏列表适配器
        mAdapter = new BaseQuickAdapter<Favorite.DataListBean, BaseViewHolder>(R.layout.item_shoucang) {

            @Override
            protected void convert(BaseViewHolder helper, final Favorite.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.userImage,ivTouxiang);
                helper.setText(R.id.cb_renshu, item.receiveNum + "");
                helper.setText(R.id.cb_pinglun, item.commentsNum + "");
                helper.setText(R.id.cb_zan, item.favoriteNum + "");
                helper.setText(R.id.tv_neirong, item.content + "");
                helper.setText(R.id.tv_name, item.userName + "");
                BGANinePhotoLayout nplItemMomentPhotos = helper.getView(R.id.npl_item_moment_photos);
                nplItemMomentPhotos.setData((ArrayList<String>) item.listImg);

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击去红包详情
                        app.writeInt("packetId",item.id);
                        readyGo(RedPacketParticularsActivity.class);
                    }
                });
            }
        };
    }
}