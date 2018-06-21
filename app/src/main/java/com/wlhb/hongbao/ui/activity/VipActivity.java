package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Pay;
import com.wlhb.hongbao.bean.Viplist;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.WeiXinPay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class VipActivity extends BaseActivity {
    @BindView(R.id.rl_yue)
    RelativeLayout rlYue;
    @BindView(R.id.rl_nian)
    RelativeLayout rlNian;
    @BindView(R.id.bt_agree)
    Button btAgree;
    @BindView(R.id.cb_yue)
    CheckBox cbYue;
    @BindView(R.id.cb_nian)
    CheckBox cbNian;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.tv_nian)
    TextView tvNian;
    private double money = 0;
    private Viplist viplistyue;
    private Viplist viplistnian;
    private int id;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_vip, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("开通VIP");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //获取VIP列表信息
        Call<BaseResponse<List<Viplist>>> call = service.viplist(App.token);
        call.enqueue(new BaseCallback<BaseResponse<List<Viplist>>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<List<Viplist>>> response) {
                BaseResponse<List<Viplist>> body = response.body();
                if (body.isOK()) {
                    if (body.data != null && !body.data.isEmpty()) {
                        viplistyue = body.data.get(0);
                        viplistnian = body.data.get(1);
                    }
                    tvYue.setText(viplistyue.title);
                    tvNian.setText(viplistnian.title);
                }
            }
        });
    }

    @OnClick({R.id.rl_yue, R.id.rl_nian, R.id.bt_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_yue:
                if (cbYue.isChecked()) {
                    cbYue.setChecked(false);
                } else {
                    cbYue.setChecked(true);
                    cbNian.setChecked(false);
                }
                break;
            case R.id.rl_nian:
                if (cbNian.isChecked()) {
                    cbNian.setChecked(false);
                } else {
                    cbNian.setChecked(true);
                    cbYue.setChecked(false);
                }
                break;
            case R.id.bt_agree:
                if (cbYue.isChecked()) {
                    money = viplistyue.money;
                    id = viplistyue.id;
                } else if (cbNian.isChecked()) {
                    money = viplistnian.money;
                    id = viplistnian.id;
                }
                if (money == 0) {
                    showToast("请选择充值金额");
                    return;
                }
                Call<BaseResponse<JSON>> calll = service.recharge(App.token, 4, money, "wxpay", id, "");
                calll.enqueue(new BaseCallback<BaseResponse<JSON>>(calll, this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            JSON data = body.data;
                            Pay mWXPayingEntity = JSON.toJavaObject(data, Pay.class);
                            new WeiXinPay(context, mWXPayingEntity);
                        } else {
                            showToast(body.message);
                        }
                    }
                });
                break;
        }
    }
}
