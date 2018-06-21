package com.wlhb.hongbao.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseFragment;
import com.wlhb.hongbao.bean.UserData;
import com.wlhb.hongbao.bean.UserInfo;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.activity.CollectActivity;
import com.wlhb.hongbao.ui.activity.DynamicActivity;
import com.wlhb.hongbao.ui.activity.FriendActivity;
import com.wlhb.hongbao.ui.activity.HistoryActivity;
import com.wlhb.hongbao.ui.activity.HomepageActivity;
import com.wlhb.hongbao.ui.activity.MyCommunity;
import com.wlhb.hongbao.ui.activity.PersonalActivity;
import com.wlhb.hongbao.ui.activity.QRcodeActivity;
import com.wlhb.hongbao.ui.activity.SettingActivity;
import com.wlhb.hongbao.ui.activity.ShareActivity;
import com.wlhb.hongbao.ui.activity.VipActivity;
import com.wlhb.hongbao.ui.activity.WalletActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/26/026.
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.lv_picture)
    ImageView lvPicture;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.rl_gerenzhuye)
    RelativeLayout rlGerenzhuye;

    @BindView(R.id.rl_erweima)
    RelativeLayout rlErweima;

    @BindView(R.id.rl_qianbao)
    RelativeLayout rlQianbao;

    @BindView(R.id.rl_lishi)
    RelativeLayout rlLishi;

    @BindView(R.id.rl_wodeshoucang)
    RelativeLayout rlWodeshoucang;

    @BindView(R.id.rl_dongtai)
    RelativeLayout rlDongtai;

    @BindView(R.id.rl_viptaocan)
    RelativeLayout rlViptaocan;

    @BindView(R.id.rl_fenxiang)
    RelativeLayout rlFenxiang;

    @BindView(R.id.rl_pengyou)
    RelativeLayout rlPengyou;
    @BindView(R.id.iv_touxiangbg)
    ImageView ivTouxiangbg;
    @BindView(R.id.tv_yqm)
    TextView tvYqm;

    private Bitmap blur;
    private String tximage;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, inflate);

        init();
        return inflate;
    }

    private void init() {

        Call<BaseResponse<JSON>> callinfo = service.info(App.token, App.id);
        callinfo.enqueue(new BaseCallback<BaseResponse<JSON>>(callinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<JSON>> response) {
                BaseResponse<JSON> body = response.body();
                if (body.isOK()) {
                    JSON data = body.data;
                    UserInfo userInfo = JSONObject.toJavaObject(data, UserInfo.class);
                    tvName.setText(userInfo.username);
                    tximage = userInfo.image;
                    showImageTx(tximage, lvPicture);
                    //设置头像背景模糊
                    Glide.with(MineFragment.this).load(tximage).error(R.drawable.logo).bitmapTransform(new BlurTransformation(context,23,1)).into(ivTouxiangbg);
                    tvYqm.setText("邀请码:" + userInfo.inviteCode);
                    app.writeString("qrcode",userInfo.qrcode);
                }
            }
        });
    }


    @OnClick({R.id.lv_picture, R.id.lv_shezhi, R.id.rl_gerenzhuye, R.id.rl_erweima, R.id.rl_qianbao, R.id.rl_lishi, R.id.rl_wodeshoucang, R.id.rl_dongtai, R.id.rl_viptaocan, R.id.rl_fenxiang, R.id.rl_pengyou,R.id.rl_wodesheqv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_picture:
                readyGo(PersonalActivity.class);
                break;
            case R.id.lv_shezhi:
                readyGo(SettingActivity.class);
                break;
            case R.id.rl_gerenzhuye:
                app.writeInt("id",App.id);
                readyGo(HomepageActivity.class);
                break;
            case R.id.rl_erweima:
                readyGo(QRcodeActivity.class);
                break;
            case R.id.rl_qianbao:
                readyGo(WalletActivity.class);
                break;
            case R.id.rl_lishi:
                readyGo(HistoryActivity.class);
                break;
            case R.id.rl_wodeshoucang:
                readyGo(CollectActivity.class);
                break;
            case R.id.rl_dongtai:
                readyGo(DynamicActivity.class);
                break;
            case R.id.rl_viptaocan:
                readyGo(VipActivity.class);
                break;
            case R.id.rl_fenxiang:
                readyGo(ShareActivity.class);
                break;
            case R.id.rl_pengyou:
                readyGo(FriendActivity.class);
                break;
            case R.id.rl_wodesheqv:
                readyGo(MyCommunity.class);
                break;

        }
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
