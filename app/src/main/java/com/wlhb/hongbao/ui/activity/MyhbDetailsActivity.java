package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Myreceive;
import com.wlhb.hongbao.bean.Myreceivecount;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/4/004.
 */

public class MyhbDetailsActivity extends BaseActivity {
    @BindView(R.id.menu_left)
    ImageView topMenuLeft;
    @BindView(R.id.rv_wqdhb)
    RecyclerView rvWqdhb;
    @BindView(R.id.rv_wfdhb)
    RecyclerView rvWfdhb;
    @BindView(R.id.ll_fhbxq)
    LinearLayout llFhbxq;
    @BindView(R.id.ll_qhbxq)
    LinearLayout llQhbxq;
    @BindView(R.id.rb_wqdhb)
    RadioButton rbWqdhb;
    @BindView(R.id.rb_wfdhb)
    RadioButton rbWfdhb;
    @BindView(R.id.tv_sdzje)
    TextView tvSdzje;
    @BindView(R.id.tv_zjsq)
    TextView tvZjsq;
    private BaseQuickAdapter<Myreceive.DataListBean, BaseViewHolder> jiluAdapter;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_myhbdetails, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //自定义titlebar
        setTitleBar(false);
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
        rvWfdhb.setLayoutManager(new LinearLayoutManager(this));
        rvWqdhb.setLayoutManager(new LinearLayoutManager(this));
        //获取抢到的红包列表
        Call<BaseResponse<Myreceive>> callmyreceive = service.myreceive(App.token);
        callmyreceive.enqueue(new BaseCallback<BaseResponse<Myreceive>>(callmyreceive, this) {
            @Override
            public void onResponse(Response<BaseResponse<Myreceive>> response) {
                BaseResponse<Myreceive> body = response.body();
                if (body.isOK()) {
                    jiluAdapter.setNewData(body.data.dataList);
                    rvWqdhb.setAdapter(jiluAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });

        //获取抢到的红包数据
        Call<BaseResponse<Myreceivecount>> call = service.myreceivecount(App.token);
        call.enqueue(new BaseCallback<BaseResponse<Myreceivecount>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Myreceivecount>> response) {
                BaseResponse<Myreceivecount> body = response.body();
                if (body.isOK()) {
                    //抢到的总金额
                    tvSdzje.setText(body.data.totalMoney+"");
                    //最佳手气
                    tvZjsq.setText(body.data.maxMoney+"");
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置抢到的红包列表适配器
        jiluAdapter = new BaseQuickAdapter<Myreceive.DataListBean, BaseViewHolder>(R.layout.item_hongbaojilu) {

            @Override
            protected void convert(BaseViewHolder helper, final Myreceive.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.image, ivTouxiang);

                helper.setText(R.id.tv_neirong, item.title + "");
                helper.setText(R.id.cb_renshu, item.receiveNum + "");
                helper.setText(R.id.cb_pinglun, item.commentsNum + "");
                helper.setText(R.id.cb_dianzan, item.favoriteNum + "");
                helper.setText(R.id.tv_name, item.userName + "");

                BGANinePhotoLayout nplItemMomentPhotos = helper.getView(R.id.npl_item_moment_photos);
                if (item.listImg != null && !item.listImg.isEmpty()){
                    nplItemMomentPhotos.setData((ArrayList<String>) item.listImg);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击当前条目,进入抢到的红包详情
                        app.writeInt("packetId", item.id);
                        readyGo(RedPacketParticularsActivity.class);
                    }
                });
            }
        };
    }

    @OnClick({R.id.menu_left, R.id.rb_wqdhb, R.id.rb_wfdhb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_left://点击左上箭头返回
                finish();
                break;
            case R.id.rb_wqdhb://显示我领取的红包
                llQhbxq.setVisibility(View.VISIBLE);
                rvWqdhb.setVisibility(View.VISIBLE);
                llFhbxq.setVisibility(View.GONE);
                rvWfdhb.setVisibility(View.GONE);
                break;
            case R.id.rb_wfdhb://显示我发放的红包
                llQhbxq.setVisibility(View.GONE);
                rvWqdhb.setVisibility(View.GONE);
                llFhbxq.setVisibility(View.VISIBLE);
                rvWfdhb.setVisibility(View.VISIBLE);
                break;
        }
    }
}
