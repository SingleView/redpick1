package com.wlhb.hongbao.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Lists;
import com.wlhb.hongbao.bean.Pay;
import com.wlhb.hongbao.bean.UserInfo;
import com.wlhb.hongbao.bean.Views;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.WeiXinPay;
import com.wlhb.hongbao.utils.TimeUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/29/029.
 */

public class HomepageActivity extends BaseActivity {

    @BindView(R.id.popw)
    View popw;
    @BindView(R.id.rv_rizhi)
    RecyclerView rvRizhi;

    private PopupWindow popWindow;
    private TextView jine;
    private BaseQuickAdapter mAdapter;
    private BaseQuickAdapter<Views.DataListBean, BaseViewHolder> fkAdapter;
    private RecyclerView rvFangke;
    private TextView tvFangkerenshu;
    private Dialog dialog;
    private int id;
    private TextView tvName;
    private TextView tvQianming;
    private ImageView ivTx;
    private TextView tvFadejine;
    private TextView tvQiangdejine;
    private ImageView xingbie;
    private double num;
    private int earnestMoney;
    private TextView tvJgz;
    private ImageView ivJgz;


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_homepage, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详细信息");
        //获取用户ID
        id = app.readInt("id", -1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
        init();

    }

    private void init() {

        initAdapter();
        rvRizhi.setLayoutManager(new LinearLayoutManager(this));
        //获取个人信息红包列表信息
        Call<BaseResponse<Lists>> calllists = service.lists(App.token, id);
        calllists.enqueue(new BaseCallback<BaseResponse<Lists>>(calllists, this) {
            @Override
            public void onResponse(Response<BaseResponse<Lists>> response) {
                BaseResponse<Lists> body = response.body();
                if (body.isOK()) {
                    mAdapter.setNewData(body.data.dataList);
                    rvRizhi.setAdapter(mAdapter);
                }
            }
        });
    }

    private View getHeaderView() {
        //设置个人信息头部数据
        View view = getLayoutInflater().inflate(R.layout.header_xiangxixinxi, (ViewGroup) rvRizhi.getParent(), false);
        ivTx = view.findViewById(R.id.iv_tx);
        ImageView ivLt = view.findViewById(R.id.iv_lt);
        ivJgz = view.findViewById(R.id.iv_jgz);
        xingbie = view.findViewById(R.id.iv_xingbie);
        tvName = view.findViewById(R.id.tv_name);
        tvQianming = view.findViewById(R.id.tv_qianming);
        tvFadejine = view.findViewById(R.id.tv_fadejine);
        tvQiangdejine = view.findViewById(R.id.tv_qiangdejine);
        tvFangkerenshu = view.findViewById(R.id.tv_fangkerenshu);
        rvFangke = view.findViewById(R.id.rv_fangke);
        tvJgz = view.findViewById(R.id.tv_jgz);
        RelativeLayout guanzhu = view.findViewById(R.id.rl_guanzhu);
        RelativeLayout liaotian = view.findViewById(R.id.rl_liaotian);
        RelativeLayout fksl = view.findViewById(R.id.rl_fksl);
        RelativeLayout fktx = view.findViewById(R.id.rl_fktx);
        //如果是自己,隐藏聊天和关注,显示访客列表
        if (id == App.id) {
            guanzhu.setVisibility(View.GONE);
            liaotian.setVisibility(View.GONE);
        } else {
            fksl.setVisibility(View.GONE);
            fktx.setVisibility(View.GONE);
        }
        ivLt.setOnClickListener(new MyListener());
        ivJgz.setOnClickListener(new MyListener());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvFangke.setLayoutManager(linearLayoutManager);
        //调用个人信息接口
        Call<BaseResponse<JSON>> callinfo = service.info(App.token, id);
        callinfo.enqueue(new BaseCallback<BaseResponse<JSON>>(callinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    JSON data = body.data;
                    UserInfo userInfo = JSONObject.toJavaObject(data, UserInfo.class);
                    showImageTx(userInfo.image, ivTx);
                    tvName.setText(userInfo.username);
                    //设置关注状态
                    if (userInfo.isLike == 1) {
                        tvJgz.setText("已关注");
                    }
                    //设置性别
                    if (userInfo.gender == 0) {
                        xingbie.setVisibility(View.GONE);
                    } else if (userInfo.gender == 1) {
                        xingbie.setImageResource(R.drawable.nan);
                    } else {
                        xingbie.setImageResource(R.drawable.nv);
                    }
                    tvQianming.setText(userInfo.personalMark);
                    tvFadejine.setText(formatFloatNumber(userInfo.sendMoney));
                    tvQiangdejine.setText(String.valueOf(userInfo.receiveNum));
                    earnestMoney = userInfo.earnestMoney;
                    if (id != App.id && userInfo.isBlack != 1) {
                        //如果是自己的详细信息或者已拉黑用户就不显示拉黑弹窗
                        setTitleMenu(true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                downPopwindow();
                            }
                        });
                    }
                } else {
                    showToast(body.message);
                }
            }
        });
        //调用访客列表接口
        Call<BaseResponse<Views>> call = service.views(App.token);
        call.enqueue(new BaseCallback<BaseResponse<Views>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Views>> response) {
                BaseResponse<Views> body = response.body();
                if (body.isOK()) {
                    tvFangkerenshu.setText(body.data.dataList.size() + "");
                    fkAdapter.setNewData(body.data.dataList);
                    rvFangke.setAdapter(fkAdapter);
                }
            }
        });
        return view;
    }


    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     *
     * @param value
     * @return Sting
     */
    public String formatFloatNumber(double value) {
        if (value != 0.00) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            return df.format(value);
        } else {
            return "0.00";
        }

    }


    private void initAdapter() {
        //设置详细信息红包列表适配器
        mAdapter = new BaseQuickAdapter<Lists.DataListBean, BaseViewHolder>(R.layout.item_xiangxixinxi) {

            @Override
            protected void convert(BaseViewHolder helper, final Lists.DataListBean item) {

                ImageView ivTouxiang = helper.getView(R.id.iv_pt);
                showImage(item.listImg.get(0), ivTouxiang);
                helper.setText(R.id.tv_neirong, item.content);
                helper.setText(R.id.cb_renshu, item.receiveNum + "");
                helper.setText(R.id.cb_pinglun, item.commentsNum + "");
                helper.setText(R.id.cb_zan, item.favoriteNum + "");
                String timeslash = TimeUtils.timeslash(item.tstamp + "");
                helper.setText(R.id.tv_rq, timeslash.substring(timeslash.indexOf("/") + 4, timeslash.indexOf("/") + 6));
                helper.setText(R.id.tv_yue, timeslash.substring(timeslash.indexOf("/") + 1, timeslash.indexOf("/") + 3) + "月");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击条目去红包详情
                        app.writeInt("packetId", item.id);
                        readyGo(RedPacketParticularsActivity.class);
                    }
                });
            }
        };

        //设置访客适配器
        fkAdapter = new BaseQuickAdapter<Views.DataListBean, BaseViewHolder>(R.layout.item_fangke) {

            @Override
            protected void convert(BaseViewHolder helper, final Views.DataListBean item) {

                CircleImageView cvTouxiang = helper.getView(R.id.cv_touxiang);
                showImageTx(item.toUserImage, cvTouxiang);

                helper.getView(R.id.cv_touxiang).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击条目去访客详情
                        app.writeInt("id", item.toUserId);
                        readyGo(HomepageActivity.class);
                    }
                });
            }
        };
        //添加头部布局
        mAdapter.addHeaderView(getHeaderView());
    }

    //黑名单弹窗
    private void downPopwindow() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_hmd, null);
        // 这里就给具体大小的数字，要不然位置不好计算
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(width, height);
        int measuredHeight = contentView.getMeasuredHeight();
        int measuredWidth = contentView.getMeasuredWidth();
        popWindow = new PopupWindow(contentView, measuredWidth, measuredHeight);
        popWindow.setOutsideTouchable(true);
        LinearLayout hmd = contentView.findViewById(R.id.pop_hmd);
        hmd.setOnClickListener(new MyListener());
        popWindow.showAsDropDown(popw, -300, -50);
    }

    //点击聊天显示诚意红包弹窗
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_givered, null);
        jine = (TextView) v.findViewById(R.id.tv_jine);
        num = (double) Math.round(Math.random() * (earnestMoney - 1) + 1) / 100.0;
        jine.setText(String.valueOf(num));
        LinearLayout hjine = (LinearLayout) v.findViewById(R.id.ll_hje);
        Button fhb = (Button) v.findViewById(R.id.bt_fhb);
        hjine.setOnClickListener(new MyListener());
        fhb.setOnClickListener(new MyListener());
        //builer.setView(v);
        // 这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 900;
        params.height = 1200;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_hje://设置诚意金额随机数
                    num = (double) Math.round(Math.random() * (earnestMoney - 1) + 1) / 100.0;
                    jine.setText(String.valueOf(num));
                    break;
                case R.id.bt_fhb://发诚意红包
                    Call<BaseResponse<JSON>> calll = service.recharge(App.token, 5, num, "wxpay", App.id, "");
                    calll.enqueue(new BaseCallback<BaseResponse<JSON>>(calll, HomepageActivity.this) {
                        @Override
                        public void onResponse(Response<BaseResponse<JSON>> response) {
                            BaseResponse<JSON> body = response.body();
                            if (body.isOK()) {
                                JSON data = body.data;
                                Pay mWXPayingEntity = JSON.toJavaObject(data, Pay.class);
                                new WeiXinPay(context, mWXPayingEntity);
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
                    break;
                case R.id.pop_hmd://拉黑
                    Call<BaseResponse<JSON>> callpull = service.pullblack(App.token, id);
                    callpull.enqueue(new BaseCallback<BaseResponse<JSON>>(callpull, HomepageActivity.this) {
                        @Override
                        public void onResponse(Response<BaseResponse<JSON>> response) {
                            BaseResponse<JSON> body = response.body();
                            if (body.isOK()) {
                                showToast(body.message);
                                //拉黑后关闭右上拉黑弹窗
                                setTitleMenu(false, null);
                                popWindow.dismiss();
                            } else {
                                showToast(body.message);
                            }
                        }
                    });
                    break;
                case R.id.iv_lt://聊天弹窗
                    showDialog();
                    break;
                case R.id.iv_jgz://加关注
                    if (tvJgz.getText().toString().equals("关注")) {
                        Call<BaseResponse<JSON>> callmemberlike = service.memberlike(App.token, id);
                        callmemberlike.enqueue(new BaseCallback<BaseResponse<JSON>>(callmemberlike, HomepageActivity.this) {
                            @Override
                            public void onResponse(Response<BaseResponse<JSON>> response) {
                                BaseResponse<JSON> body = response.body();
                                if (body.isOK()) {
                                    tvJgz.setText("已关注");
                                    showToast(body.message);
                                } else {
                                    showToast(body.message);
                                }
                            }
                        });
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomepageActivity.this);
                        builder.setTitle("是否取消关注该用户")
                                .setNegativeButton("否", null);
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<BaseResponse<JSON>> callmemberlike = service.memberlike(App.token, id);
                                callmemberlike.enqueue(new BaseCallback<BaseResponse<JSON>>(callmemberlike, HomepageActivity.this) {
                                    @Override
                                    public void onResponse(Response<BaseResponse<JSON>> response) {
                                        BaseResponse<JSON> body = response.body();
                                        if (body.isOK()) {
                                            tvJgz.setText("关注");
                                            showToast(body.message);
                                        } else {
                                            showToast(body.message);
                                        }
                                    }
                                });
                            }
                        });
                        builder.show();
                    }

                    break;
                default:
                    break;
            }
        }
    }
}
