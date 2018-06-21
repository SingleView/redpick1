package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Funnyman;
import com.wlhb.hongbao.bean.Views;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.flingswipe.CardAdapter;
import com.wlhb.hongbao.ui.flingswipe.CardMode;
import com.wlhb.hongbao.ui.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/4/11/011.
 */

public class FunnymanActivity extends BaseActivity {


    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.right)
    ImageView right;
    private ArrayList<CardMode> al;
    private CardAdapter adapter;
    private int i;
    private SwipeFlingAdapterView flingContainer;
    private List<String> list = new ArrayList<>();
    private ArrayList<String> stringArrayList = new ArrayList<String>();
    private String[] imageUrls = new String[]{"http://www.blbuy.com.cn:80/uploads/member/201805/29/6d6d8325-2902-49ae-9a26-1df1ca0aa9fd.png",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/G0U7VYeOiaeBzlWLicTiafbmuQckBSp6yYrhtuZQ42ICTiaBvcI9MPjczjvOSkHtEia3V0GUE1ME4Vwd40NibUe9Rm8Q/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/q13pI6KWje1xlQUWib9tJoUoibmsnR8gnjJUQOrsn7sa8R1nTp4udO31kxHRq7G4Z39BNjycoZvxbGeFRiaoocxCg/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/9YRh0gX8z9xZTdn5A2oHibibmGfibIGeMrcvB22gmvYfLkD9uRtiauSpWia6w1fLT0HTVLXbRQ7ohVccZdMvImoCJ5A/132"};

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_funnyman, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("有趣的人");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initdata();
        init();
    }

    private void initdata() {
        //获取有趣的人图片
        Call<BaseResponse<Funnyman>> callfunnyman = service.funnyman(App.token);
        callfunnyman.enqueue(new BaseCallback<BaseResponse<Funnyman>>(callfunnyman, this) {
            @Override
            public void onResponse(Response<BaseResponse<Funnyman>> response) {
                BaseResponse<Funnyman> body = response.body();
                if (body.isOK()) {
                    for (int j = 0; j < body.data.dataList.size(); j++) {
                        stringArrayList.add(body.data.dataList.get(j).image);
                    }
                    imageUrls = stringArrayList.toArray(new String[stringArrayList.size()]);

                }
            }
        });
    }

    @OnClick({R.id.left, R.id.back, R.id.right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left://点击向左翻页
                left();
                break;
            case R.id.back://点击撤回(未完成)

                break;
            case R.id.right://点击向右翻页
                right();
                break;
        }
    }

    public void init() {
        //滑动图片布局适配器
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        al = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            list.add(imageUrls[i]);
        }

        //设置图片名字
        for (int j = 0; j < list.size(); j++) {
            al.add(new CardMode("sq", list.get(j)));
        }

        //设置图片适配器
        adapter = new CardAdapter(FunnymanActivity.this, al);
        flingContainer.setAdapter(adapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                al.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //当图片向右滑动调用喜欢接口
                Call<BaseResponse<JSON>> callfunnyman = service.funnymanlike(App.token,1);
                callfunnyman.enqueue(new BaseCallback<BaseResponse<JSON>>(callfunnyman, FunnymanActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                        }else {
                            showToast(body.message);
                        }
                    }
                });
            }

            @Override
            //当图片划完时循环
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                al.add(new CardMode("已经点赞完毕", "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1527587972&di=b8eda93748efe00ba8ed3ac87ddbc315&src=http://img.zcool.cn/community/01460b57e4a6fa0000012e7ed75e83.png"));
                adapter.notifyDataSetChanged();
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = flingContainer.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }


    public void right() {
        flingContainer.getTopCardListener().selectRight();
    }

    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }


}
