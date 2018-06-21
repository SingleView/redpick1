package com.wlhb.hongbao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseFragment;
import com.wlhb.hongbao.bean.BroadcastData;
import com.wlhb.hongbao.bean.Mycommunitymsg;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.activity.AttentionaActivity;
import com.wlhb.hongbao.ui.activity.CommunityNoticeDetailsActivity;
import com.wlhb.hongbao.ui.activity.FansActivity;
import com.wlhb.hongbao.ui.activity.NoticeDetailsActivity;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/4/004.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.rb_gonggao)
    RadioButton rbGonggao;
    @BindView(R.id.rb_liaotian)
    RadioButton rbLiaotian;
    @BindView(R.id.top_menu_right)
    ImageView topMenuRight;
    @BindView(R.id.rv_gonggao)
    RecyclerView rvGonggao;
    @BindView(R.id.rb_sheqvgonggao)
    RadioButton rbSheqvgonggao;
    @BindView(R.id.rv_sheqvgonggao)
    RecyclerView rvSheqvgonggao;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.bt_xtshumu)
    Button btXtshumu;
    @BindView(R.id.bt_sqshumu)
    Button btSqshumu;
    private PopupWindow popWindow;
    private BaseQuickAdapter<BroadcastData.DataListBean, BaseViewHolder> ggAdapter;
    private BaseQuickAdapter<Mycommunitymsg.DataListBean, BaseViewHolder> sqggAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    private void initView() {
        initAdapter();
        //设置系统与社区未读消息数目
        int communityNum = app.readInt("communityNum", -1);
        int sysNum = app.readInt("sysNum", -1);
        btSqshumu.setVisibility(communityNum == 0 ? View.GONE : View.VISIBLE);
        btXtshumu.setVisibility(sysNum == 0 ? View.GONE : View.VISIBLE);
        btSqshumu.setText(communityNum + "");
        btXtshumu.setText(sysNum + "");
        rvGonggao.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSheqvgonggao.setLayoutManager(new LinearLayoutManager(getActivity()));
        //获取系统公告
        Call<BaseResponse<BroadcastData>> callbroadcastdata = service.broadcastdata(App.token);
        callbroadcastdata.enqueue(new BaseCallback<BaseResponse<BroadcastData>>(callbroadcastdata, this) {
            @Override
            public void onResponse(Response<BaseResponse<BroadcastData>> response) {
                BaseResponse<BroadcastData> body = response.body();
                if (body.isOK()) {
                    ggAdapter.setNewData(body.data.dataList);
                    rvGonggao.setAdapter(ggAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });

        //获取社区公告
        Call<BaseResponse<Mycommunitymsg>> callcommunitymsg = service.mycommunitymsg(App.token);
        callcommunitymsg.enqueue(new BaseCallback<BaseResponse<Mycommunitymsg>>(callcommunitymsg, this) {
            @Override
            public void onResponse(Response<BaseResponse<Mycommunitymsg>> response) {
                BaseResponse<Mycommunitymsg> body = response.body();
                if (body.isOK()) {
                    sqggAdapter.setNewData(body.data.dataList);
                    rvSheqvgonggao.setAdapter(sqggAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置系统公告适配器
        ggAdapter = new BaseQuickAdapter<BroadcastData.DataListBean, BaseViewHolder>(R.layout.item_xiaoxi) {

            @Override
            protected void convert(BaseViewHolder helper, final BroadcastData.DataListBean item) {
                helper.setText(R.id.tv_name, item.title);
                helper.setText(R.id.tv_neirong, item.content);
                helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
                int type = item.type;
                if (type == 5) {
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            app.writeInt("msgid", item.id);
                            app.writeInt("type", 0);
                            readyGo(NoticeDetailsActivity.class);
                        }
                    });
                } else {
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            app.writeInt("msgid", item.id);
                            app.writeInt("type", 0);
                            readyGo(NoticeDetailsActivity.class);
                        }
                    });
                }
            }
        };
        //设置社区公告适配器
        sqggAdapter = new BaseQuickAdapter<Mycommunitymsg.DataListBean, BaseViewHolder>(R.layout.item_xiaoxi) {

            @Override
            protected void convert(BaseViewHolder helper, final Mycommunitymsg.DataListBean item) {
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

    @OnClick({R.id.rb_gonggao, R.id.rb_liaotian, R.id.rb_sheqvgonggao, R.id.top_menu_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_gonggao://点击显示系统公告
                rvGonggao.setVisibility(View.VISIBLE);
                fragment.setVisibility(View.GONE);
                rvSheqvgonggao.setVisibility(View.GONE);
                break;
            case R.id.rb_liaotian://点击显示聊天消息
                rvGonggao.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);
                rvSheqvgonggao.setVisibility(View.GONE);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment, new HuanxinFragment());
                ft.commit();
                break;
            case R.id.rb_sheqvgonggao://点击显示社区公告
                rvGonggao.setVisibility(View.GONE);
                fragment.setVisibility(View.GONE);
                rvSheqvgonggao.setVisibility(View.VISIBLE);
                break;
            case R.id.top_menu_right://点击显示右边弹窗
                downPopwindow();
                break;
        }
    }

    private void downPopwindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_down, null);
        // 这里就给具体大小的数字，要不然位置不好计算
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(width, height);
        int measuredHeight = contentView.getMeasuredHeight();
        int measuredWidth = contentView.getMeasuredWidth();
        popWindow = new PopupWindow(contentView, measuredWidth, measuredHeight);
        popWindow.setOutsideTouchable(true);
        LinearLayout popguanzhu = contentView.findViewById(R.id.pop_guanzhu);
        LinearLayout popfensi = contentView.findViewById(R.id.pop_fensi);
        popguanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去我的关注
                readyGo(AttentionaActivity.class);
                popWindow.dismiss();
            }
        });
        popfensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去我的粉丝
                readyGo(FansActivity.class);
                popWindow.dismiss();
            }
        });
        popWindow.showAsDropDown(topMenuRight, -200, 0);
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initView();
    }
}
