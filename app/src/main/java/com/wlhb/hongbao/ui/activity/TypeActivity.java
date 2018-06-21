package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Category;
import com.wlhb.hongbao.bean.Favorite;
import com.wlhb.hongbao.bean.Packetlists;
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
 * Created by Administrator on 2018/3/29/029.
 */

public class TypeActivity extends BaseActivity {


    @BindView(R.id.rv_fenlei)
    RecyclerView rvFenlei;
    private BaseQuickAdapter<Category, BaseViewHolder> mAdapter;
    private int category;
    private String fjprovince;
    private String fjcity;
    private String fjdistrict;
    private float lat;
    private float lng;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_type, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("类型");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //获取附近的地址和坐标
        fjprovince = app.readString("fjprovince", "");
        fjcity = app.readString("fjcity", "");
        fjdistrict = app.readString("fjdistrict", "");
        lat = app.readFloat("latitude", -2);
        lng = app.readFloat("longitude", -2);
        initAdapter();
        rvFenlei.setLayoutManager(new LinearLayoutManager(this));
        //获取分类
        Call<BaseResponse<List<Category>>> calldynamic = service.category(App.token);
        calldynamic.enqueue(new BaseCallback<BaseResponse<List<Category>>>(calldynamic, this) {
            @Override
            public void onResponse(Response<BaseResponse<List<Category>>> response) {
                BaseResponse<List<Category>> body = response.body();
                if (body.isOK()) {

                    mAdapter.setNewData(body.data);
                    rvFenlei.setAdapter(mAdapter);

                }
            }
        });
    }

    private void initAdapter() {
        //设置红包收藏列表适配器
        mAdapter = new BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_fenlei) {

            @Override
            protected void convert(BaseViewHolder helper, final Category item) {
                helper.setText(R.id.tv_leiming,item.name);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        category = item.id;
                        //点击分类刷新
                        getDynamicData(category);
                    }
                });
            }
        };
    }


    //获取红包分类
    private void getDynamicData(int category) {
        Call<BaseResponse<Packetlists>> calldynamic = service.packetlists(App.token, 4, fjprovince, fjcity, fjdistrict, lng, lat,category);
        calldynamic.enqueue(new BaseCallback<BaseResponse<Packetlists>>(calldynamic, this) {
            @Override
            public void onResponse(Response<BaseResponse<Packetlists>> response) {
                BaseResponse<Packetlists> body = response.body();
                if (body.isOK()) {
                  finish();
                } else {
                    showToast(body.message);
                }
            }
        });
    }

}
