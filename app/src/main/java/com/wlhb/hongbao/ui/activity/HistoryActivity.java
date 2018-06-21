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
import com.wlhb.hongbao.bean.History;
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

public class HistoryActivity extends BaseActivity {


    @BindView(R.id.rl_nomore)
    RelativeLayout rlNomore;
    @BindView(R.id.rv_lsjl)
    RecyclerView rvLsjl;
    private BaseQuickAdapter<History.DataListBean, BaseViewHolder> mAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_history, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("历史信息");
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
        rvLsjl.setLayoutManager(new LinearLayoutManager(this));
        //获取红包领取历史列表
        Call<BaseResponse<History>> call = service.history(App.token);
        call.enqueue(new BaseCallback<BaseResponse<History>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<History>> response) {
                BaseResponse<History> body = response.body();
                if (body.isOK()) {
                    if (body.data.dataList != null && !body.data.dataList.isEmpty()){
                        rlNomore.setVisibility(View.GONE);
                        rvLsjl.setVisibility(View.VISIBLE);
                    } else {
                        rlNomore.setVisibility(View.VISIBLE);
                        rvLsjl.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(body.data.dataList);
                    rvLsjl.setAdapter(mAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });

    }

    private void initAdapter() {
        //设置红包领取历史列表适配器
        mAdapter = new BaseQuickAdapter<History.DataListBean, BaseViewHolder>(R.layout.item_lishijilu) {

            @Override
            protected void convert(BaseViewHolder helper, final History.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.userImage,ivTouxiang);
                helper.setText(R.id.cb_renshu, "  "+item.receiveNum + "");
                helper.setText(R.id.cb_pinglun, "  "+item.commentsNum + "");
                helper.setText(R.id.cb_zan, "  "+item.favoriteNum + "");
                helper.setText(R.id.tv_neirong, item.content + "");
                helper.setText(R.id.tv_name, item.userName + "");

                BGANinePhotoLayout nplItemMomentPhotos = helper.getView(R.id.npl_item_moment_photos);
                nplItemMomentPhotos.setData((ArrayList<String>) item.listImg);

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //点击去历史红包详情
                    public void onClick(View v) {
                        app.writeInt("packetId",item.id);
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

