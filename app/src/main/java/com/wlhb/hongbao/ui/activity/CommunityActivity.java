package com.wlhb.hongbao.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.adapter.SpinnerListAdapter;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Communitylists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.view.MySpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/30/030.
 */

public class CommunityActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_sheqv)
    RecyclerView rvSheqv;
    @BindView(R.id.popw)
    View popw;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_county)
    TextView tvCounty;
    @BindView(R.id.spin_province)
    RelativeLayout spinProvince;
    @BindView(R.id.spin_city)
    RelativeLayout spinCity;
    @BindView(R.id.spin_county)
    RelativeLayout spinCounty;
    @BindView(R.id.tv_chengshi)
    TextView tvChengshi;
    private RelativeLayout provinceSpinner = null;  //省级（省、直辖市）
    private RelativeLayout citySpinner = null;     //地级市
    private RelativeLayout countySpinner = null;    //县级（区、县、县级市）
    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;


    //省级选项值
    private String[] province = new String[]{"广东省"};
    //地级选项值
    private String[] city = new String[]
            {"广州市"};
    //县级选项值
    private String[] county = new String[]
            {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"};
    private PopupWindow popWindow;
    private BaseQuickAdapter<Communitylists.DataListBean, BaseViewHolder> sheqvAdapter;
    private int id;
    private int categoryId;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleColor(true);
        setTitle("社区列表");
        setTitleMenu(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPopwindow();
            }
        });
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_community, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        rvSheqv.setLayoutManager(new LinearLayoutManager(this));
        spinProvince.setOnClickListener(this);
        spinCity.setOnClickListener(this);
        spinCounty.setOnClickListener(this);
        String city = app.readString("city", "");
        categoryId = app.readInt("categoryId", -2);
        tvChengshi.setText(city);
        initAdapter();

        //获取社区列表
        getCommunitylists();
    }

    private void getCommunitylists() {
        Call<BaseResponse<Communitylists>> callcommunitylists = service.communitylists(App.token,categoryId,null);
        callcommunitylists.enqueue(new BaseCallback<BaseResponse<Communitylists>>(callcommunitylists, this) {
            @Override
            public void onResponse(Response<BaseResponse<Communitylists>> response) {
                BaseResponse<Communitylists> body = response.body();
                if (body.isOK()) {

                    sheqvAdapter.setNewData(body.data.dataList);
                    rvSheqv.setAdapter(sheqvAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置社区适配器
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

        //点击社区条目加入社区
        sheqvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Communitylists.DataListBean item = (Communitylists.DataListBean) adapter.getItem(position);
                id = item.id;
                int isJoin = item.isJoin;
                app.writeInt("communityid", id);
                if (isJoin == 0){
                    //未加入社区显示是否加入对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommunityActivity.this);
                    builder.setTitle("是否加入社区" + item.name + "?")
                            .setNegativeButton("取消", null);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            //同意后调用加入社区接口
                            Call<BaseResponse<JSON>> calladd = service.add(App.token, id
                            );
                            calladd.enqueue(new BaseCallback<BaseResponse<JSON>>(calladd, CommunityActivity.this) {
                                @Override
                                public void onResponse(Response<BaseResponse<JSON>> response) {
                                    BaseResponse<JSON> body = response.body();
                                    if (body.isOK()) {
                                        readyGo(CommunitylistActivity.class);
                                        getCommunitylists();
                                    } else {
                                        showToast(body.message);
                                    }
                                }
                            });
                        }
                    });
                    builder.show();
                }else {
                    readyGo(CommunitylistActivity.class);
                }
            }
        });
    }

    //右上角创建社区弹窗
    private void downPopwindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_cjsq, null);
        // 这里就给具体大小的数字，要不然位置不好计算
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(width, height);
        int measuredHeight = contentView.getMeasuredHeight();
        int measuredWidth = contentView.getMeasuredWidth();
        popWindow = new PopupWindow(contentView, measuredWidth, measuredHeight);
        popWindow.setOutsideTouchable(true);
        LinearLayout cjsq = contentView.findViewById(R.id.pop_cjsq);
        cjsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去创建社区
                readyGo(CreatingCommunitiesActivity.class);
                popWindow.dismiss();
            }
        });
        popWindow.showAsDropDown(popw, 770, -70);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spin_province://省级下拉框
                MySpinner pSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, province);
                pSpinner.showAsDropDown(spinProvince, 0, 0);//显示在rl_spinner的下方
                pSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        tvProvince.setText(province[position]);
                    }
                });
                break;
            case R.id.spin_city://市级下拉框
                MySpinner cSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, city);
                cSpinner.showAsDropDown(spinCity, 0, 0);//显示在rl_spinner的下方
                cSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        tvCity.setText(city[position]);
                    }
                });
                break;
            case R.id.spin_county://区级下拉框
                MySpinner coSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, county);
                coSpinner.showAsDropDown(spinCounty, 0, 0);//显示在rl_spinner的下方
                coSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        tvCounty.setText(county[position]);
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //创建社区后返回刷新社区列表
        getCommunitylists();
    }
}

