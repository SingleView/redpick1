package com.wlhb.hongbao.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.app.AppManager;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.cache.UserManage;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.view.OnPasswordInputFinish;
import com.wlhb.hongbao.ui.view.PasswordView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/3/003.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.rl_tz)
    RelativeLayout rlTz;
    @BindView(R.id.rl_ty)
    RelativeLayout rlTy;
    @BindView(R.id.rl_hmd)
    RelativeLayout rlHmd;
    @BindView(R.id.rl_gy)
    RelativeLayout rlGy;
    @BindView(R.id.rl_bzzx)
    RelativeLayout rlBzzx;
    @BindView(R.id.rl_yjfk)
    RelativeLayout rlYjfk;
    @BindView(R.id.bt_exit)
    Button btExit;
    private AlertDialog dialog;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_setting, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("设置");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_tz, R.id.rl_ty, R.id.rl_hmd, R.id.rl_szmm, R.id.rl_gy, R.id.rl_bzzx, R.id.rl_yjfk, R.id.bt_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tz:
                readyGo(InformationActivity.class);
                break;
            case R.id.rl_ty:
                readyGo(CommonActivity.class);
                break;
            case R.id.rl_hmd:
                readyGo(BlacklistActivity.class);
                break;
            case R.id.rl_szmm:
                showDialog();
                break;
            case R.id.rl_gy:
                readyGo(AsregardsActivity.class);
                break;
            case R.id.rl_bzzx:
                readyGo(HelpCenterActivity.class);
                break;
            case R.id.rl_yjfk:
                readyGo(FeedbackActivity.class);
                break;
            case R.id.bt_exit:
                readyGo(EnterActivity.class);
                UserManage.getInstance().saveUserInfo(SettingActivity.this, "", "");
                app.logout();
                finish();
                AppManager.finish(MainActivity.class);
                break;
        }
    }

    //点击聊天显示诚意红包弹窗
    private void showDialog() {
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
                String strPassword = pwdView.getStrPassword();
                String repassword = pwdView.getStrPassword();

                Call<BaseResponse<JSON>> call = service.transpassword(App.token, strPassword, repassword);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, SettingActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            dialog.dismiss();
                            showToast(body.message);
                        } else {
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
