package com.wlhb.hongbao.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Wallet;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class WalletActivity extends BaseActivity {


    @BindView(R.id.iv_xyz)
    ImageView ivXyz;
    @BindView(R.id.bt_tx)
    Button btTx;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.tv_xingyunzhi)
    TextView tvXingyunzhi;
    private String amount;
    private String phonemsg;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_wallet, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("钱包");
        setTitleRightText("          明 细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(TransactionRecordActivity.class);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        phonemsg = app.readString("phonemsg", "");
        Call<BaseResponse<Wallet>> callwallet = service.wallet(App.token);
        callwallet.enqueue(new BaseCallback<BaseResponse<Wallet>>(callwallet, this) {
            @Override
            public void onResponse(Response<BaseResponse<Wallet>> response) {
                BaseResponse<Wallet> body = response.body();
                if (body.isOK()) {
                    amount = body.data.amount + "";
                    tvYue.setText(amount);
                    tvXingyunzhi.setText(body.data.luckyValue + "");
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    @OnClick({R.id.iv_xyz, R.id.bt_tx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_xyz:
                readyGo(LuckyPointActivity.class);
                break;
            case R.id.bt_tx:
                if (phonemsg.equals("手机号暂未绑定，请使用微信登录并绑定手机号")){
                    TextView textView = new TextView(WalletActivity.this);
                    textView.setText("提现需要绑定手机号,是否立即前往绑定手机号?");
                    textView.setPadding(30,30,0,0);
                    textView.setTextSize(16);
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
                    builder.setTitle("绑定手机号").setView(textView)
                            .setNegativeButton("暂不绑定", null);
                    builder.setPositiveButton("立即绑定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(WalletActivity.this, BindingActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();

                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("amount", amount);
                    readyGo(DrawingsActivity.class,bundle);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
