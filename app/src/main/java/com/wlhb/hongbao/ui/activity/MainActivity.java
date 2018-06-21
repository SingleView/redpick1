package com.wlhb.hongbao.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Countmessage;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.fragment.DiscoverFragment;
import com.wlhb.hongbao.ui.fragment.HomeFragment;
import com.wlhb.hongbao.ui.fragment.IssueFragment;
import com.wlhb.hongbao.ui.fragment.MessageFragment;
import com.wlhb.hongbao.ui.fragment.MineFragment;
import com.wlhb.hongbao.utils.ClickCounter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.main_rg)
    RadioGroup mRadioGroup;
    List<Fragment> mFragments = new ArrayList<>();
    @Nullable
    @BindView(R.id.Iv_home_bg)
    ImageView IvHomeBg;
    public static boolean isDebug = true;
    @BindView(R.id.bt_mainxtshumu)
    Button mainbtXtshumu;
    private ClickCounter counter;
    private Toast toast;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleBar(false);
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
        init();
        //注册环信
        configEase();
        //        //登录环信
        //        if (EMClient.getInstance().isLoggedInBefore()) {
        //            setEaseListener();
        //        } else {
        //
        //        }
        //登录环信
        loginEase();
    }

    private void init() {
        Intent getIntent = getIntent();
        int id = getIntent.getIntExtra("id", -1);
        String phonemsg = getIntent.getStringExtra("phonemsg");
        //获取记录用户id
        //是否绑定手机号
        app.writeInt("id", id);
        app.writeString("phonemsg", phonemsg);
        mFragments.add(new HomeFragment());
        mFragments.add(new DiscoverFragment());
        mFragments.add(new IssueFragment());
        mFragments.add(new MessageFragment());
        mFragments.add(new MineFragment());
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);

        //点击两次返回键退出程序
        counter = new ClickCounter(2, 2000);
        counter.setListener(new ClickCounter.OnCountListener() {

            @Override
            public void onCount(int currentTime) {
                toast = Toast.makeText(context, "再按一次退出登录", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFinish() {
                if (toast != null) {
                    toast.cancel();
                }
                App.exit();
            }
        });

        getCountmessage();
    }

    private void getCountmessage() {
        Call<BaseResponse<Countmessage>> call = service.countmessage(App.token);
        call.enqueue(new BaseCallback<BaseResponse<Countmessage>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Countmessage>> response) {
                BaseResponse<Countmessage> body = response.body();
                if (body.isOK()) {
                    int communityNum = body.data.communityNum;
                    int sysNum = body.data.sysNum;
                    app.writeInt("communityNum",communityNum);
                    app.writeInt("sysNum",sysNum);
                    mainbtXtshumu.setVisibility(communityNum + sysNum == 0?View.GONE:View.VISIBLE);
                    mainbtXtshumu.setText(communityNum + sysNum + "");
                }
            }
        });
    }

    //点击底部Radiobutton切换Fragment
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            group.indexOfChild(group.findViewById(checkedId));
            View checkedRb = group.findViewById(checkedId);
            int index = group.indexOfChild(checkedRb);
            switchFragment(index);
        }
    };

    //转换Fragment
    private void switchFragment(int index) {

        addHideShowFragment(index);
    }

    Fragment mCurrentFragment;

    Set<Fragment> mAddedFragments = new HashSet<>();

    //  show 和 hide 不影响生命周期方法，但不会重合
    private void addHideShowFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        Fragment newFragment = mFragments.get(index);
        if (mAddedFragments.contains(newFragment)) {
            ft.show(newFragment);
        } else {
            ft.add(R.id.main_fragment_container, newFragment);
            mAddedFragments.add(newFragment);
        }
        mCurrentFragment = newFragment;
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (counter != null) {
            counter.countClick();
        }
    }

    //点击中间按钮弹窗发红包
    @Nullable
    @OnClick(R.id.Iv_home_bg)
    public void onfbViewClicked() {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_fabu, null);
        ImageView ivguanb = (ImageView) v.findViewById(R.id.iv_guanb);
        LinearLayout llfbhb = (LinearLayout) v.findViewById(R.id.ll_fbhb);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面

        ivguanb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点×关闭
                dialog.dismiss();

            }
        });
        llfbhb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击去发红包
                readyGo(RedPacketInformationActivity.class);
                dialog.dismiss();//跳转关闭
            }
        });
    }

    private static final String TAG = "App";

    /**
     * 登录环信
     */
    private static void loginEase() {
        EMClient.getInstance().login("asdasd", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "登录聊天服务器成功");
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                setEaseListener();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "登录中...");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "登录聊天服务器失败" + code + ":" + message);
                if (code == EMError.USER_NOT_FOUND) {
                    //用户不存在，注册
                }
            }
        });
    }

    /**
     * 环信连接监听
     */
    private static void setEaseListener() {
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {
                Log.d(TAG, "onConnected()");
            }

            @Override
            public void onDisconnected(int code) {
                if (code == EMError.USER_LOGIN_ANOTHER_DEVICE
                        || code == EMError.USER_REMOVED) {
                    //用户从其他设备登录或账号被删除
                    //                        logout();
                    //                    checkLogin(code);
                }
            }
        });
    }

    /**
     * 环信初始化
     */
    private void configEase() {
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(isDebug);
        EaseUI.getInstance().init(this, options);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCountmessage();
    }
}

