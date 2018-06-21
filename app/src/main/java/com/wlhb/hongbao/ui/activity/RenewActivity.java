package com.wlhb.hongbao.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Pay;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.WeiXinPay;
import com.wlhb.hongbao.ui.view.OnPasswordInputFinish;
import com.wlhb.hongbao.ui.view.PasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/8/008.
 */

public class RenewActivity extends BaseActivity {
    @BindView(R.id.tv_geshu)
    TextView tvGeshu;
    @BindView(R.id.bt_xufei)
    Button btXufei;
    @BindView(R.id.et_jine)
    EditText etJine;
    @BindView(R.id.tv_jinee)
    TextView tvJinee;
    private String jines;
    private double jine;
    private AlertDialog dialog;
    private String password;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_renew, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("红包续费");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_xufei)
    public void onxufeiViewClicked() {
        jines = etJine.getText().toString().trim();
        if (TextUtils.isEmpty(jines)) {
            showToast("请输入续费金额");
        }else {
            showDialog();
        }
    }

    //弹出支付方式对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_xufei, null);
        Button bt_qrzf = (Button) v.findViewById(R.id.bt_qrzf);
        TextView tvjine = (TextView) v.findViewById(R.id.tv_jine);
        TextView qianbao = (TextView) v.findViewById(R.id.tv_qianbao);
        final RadioButton rbQianbao =  v.findViewById(R.id.rb_qianbao);
        final RadioButton rbWeixin =  v.findViewById(R.id.rb_weixin);
        //设置钱包可用余额
        qianbao.setText(App.userData.amount+"");

        tvjine.setText(jines);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 1000;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        bt_qrzf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(jines)) {
                    showToast("请输入续费金额");
                }else {
                    jine = Double.parseDouble(jines);
                }

                if (rbQianbao.isChecked()){
                    showDialogzf();

                }else if (rbWeixin.isChecked()){
                    Call<BaseResponse<JSON>> calll = service.recharge(App.token, 3, jine,"wxpay", null,"");
                    calll.enqueue(new BaseCallback<BaseResponse<JSON>>(calll, RenewActivity.this) {
                        @Override
                        public void onResponse(Response<BaseResponse<JSON>> response) {
                            BaseResponse<JSON> body = response.body();
                            if (body.isOK()) {
                                dialog.dismiss();
                                showToast(body.message);
                            }else {
                                showToast(body.message);
                            }
                        }
                    });
                }else {
                    showToast("请选择支付方式");
                }
            }
        });
    }


    //点击钱包余额支付弹出数字键盘
    private void showDialogzf() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_setpassword, null);
        final PasswordView pwdView = v.findViewById(R.id.pwd_view);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 900;
        params.height = 1200;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面

        //当密码输入完时
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                password = pwdView.getStrPassword();
                Call<BaseResponse<JSON>> calll = service.recharge(App.token, 3, jine,"yue", null,password);
                calll.enqueue(new BaseCallback<BaseResponse<JSON>>(calll, RenewActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            dialog.dismiss();
                            App.userData.amount = App.userData.amount - jine;
                            showToast(body.message);
                        }else {
                            showToast(body.message);
                        }
                    }
                });

            }
        });

        /**
         *  可以用自定义控件中暴露出来的cancelImageView方法，重新提供相应
         *  如果写了，会覆盖我们在自定义控件中提供的响应 */
        pwdView.getCancelImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
