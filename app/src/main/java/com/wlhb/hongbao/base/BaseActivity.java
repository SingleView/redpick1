package com.wlhb.hongbao.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.litesuits.common.assist.Toastor;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.app.AppManager;
import com.wlhb.hongbao.http.APIService;
import com.wlhb.hongbao.http.Http;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by Administrator on 2018/3/22/022.
 */

public abstract class BaseActivity extends SwipeBackActivity implements ITitleController, IViewController {


    private ImageView mBackButton;
    private TextView mTitleText;
    private ImageView mMenuButton;
    private FrameLayout mContent;
    private TextView mRightText;
    private RelativeLayout mTitleMenu;
    private RelativeLayout mTitleBar;
    protected Context context;
    protected Activity activity;
    protected Bundle savedInstanceState;
    public String TAG;
    protected App app;
    protected Context appContext;
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;
    protected APIService service;
    private KProgressHUD hud;
    private Toastor mToastor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_base);
        SDKInitializer.initialize(getApplicationContext());
        TAG = getClass().getSimpleName();
        AppManager.add(this);
        initContext();
        initBaseLayout();
        initStatusTint();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        View view = loadView(getLayoutInflater(), mContent);
        if (view != null) {
            mContent.addView(view);
        }
        service = Http.getService();
        initView(savedInstanceState);
        ButterKnife.bind(this);

    }

    /**
     * 初始化布局,自定义title
     */
    private void initBaseLayout() {
        mBackButton = (ImageView) findViewById(R.id.top_menu_left_back);
        mTitleText = (TextView) findViewById(R.id.top_menu_title);
        mMenuButton = (ImageView) findViewById(R.id.top_menu_right);
        mContent = (FrameLayout) findViewById(R.id.activity_base_content);
        mRightText = (TextView) findViewById(R.id.right_menu_title);
        mTitleBar = (RelativeLayout) findViewById(R.id.layout_topmenu);
        mTitleMenu = (RelativeLayout) findViewById(R.id.activity_base_title);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * init all views and add events
     */
    protected abstract void initView(Bundle savedInstanceState);


    /**
     * 初始化Context
     */
    private void initContext() {
        app = App.getInstance();
        appContext = App.getContext();
        context = this;
        activity = this;
    }

    @Override
    public void setTitleColor(Boolean b) {
        if (b == true && mTitleMenu != null) {
            mTitleMenu.setBackgroundResource(R.color.blue);
        }
    }

    @Override
    public void setTitle(String title) {
        mTitleText.setText(title);
    }

    @Override
    public void setTitleRightText(String right, View.OnClickListener listener) {
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText(right);
        if (listener != null) {
            mRightText.setClickable(true);
            mRightText.setOnClickListener(listener);
        } else {
            mRightText.setClickable(false);
        }
    }

    @Override
    public void setTitleBack(boolean isShow) {
        if (isShow == true) {
            mBackButton.setVisibility(View.VISIBLE);
        } else {
            mBackButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitleBar(boolean isShow) {
        if (isShow == true) {
            mTitleBar.setVisibility(View.VISIBLE);
        } else {
            mTitleBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitleMenu(boolean isShow, View.OnClickListener listener) {
        if (isShow == true) {
            mMenuButton.setVisibility(View.VISIBLE);
            if (listener != null) {
                mMenuButton.setClickable(true);
                mMenuButton.setOnClickListener(listener);
            }
        } else {
            mMenuButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
    }

    /**
     * 不带参数跳转
     *
     * @param claz
     */
    protected void readyGo(Class<? extends Activity> claz) {
        readyGo(claz, null);
    }


    /**
     * 带参数跳转
     *
     * @param claz
     * @param data
     */
    protected void readyGo(Class<? extends Activity> claz, Bundle data) {
        Intent intent = new Intent(this, claz);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }


    /**
     * 沉浸式状态栏
     */
    private void initStatusTint() {
        if (isStatusBarTint()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SystemBarTintManager tintManager = new SystemBarTintManager(
                        this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintResource(getStatusTintColor());
            }
        }
    }

    @Override
    public void showLoading() {
        showLoading("加载中...");
    }

    public void showLoading(String str) {
        if (hud == null) {
            hud = new KProgressHUD(context);
        }

        if (!hud.isShowing()) {
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
        }
    }

    @Override
    public void hideLoading() {
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.remove(this);
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }

    protected boolean isStatusBarTint() {
        return true;
    }


    protected int getStatusTintColor() {
        return R.color.transparent;
    }


    //点击恐怖位置隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void showToast(String str) {
        if (mToastor == null) {
            mToastor = new Toastor(context);
        }
        mToastor.showToast(str);
    }

    public void showToast(int id) {
        if (mToastor == null) {
            mToastor = new Toastor(context);
        }
        mToastor.showToast(id);
    }

    //设置头像
    public void showImageTx(String url, ImageView imageView) {
        Glide.with(context).load(url).error(R.drawable.logo).centerCrop().priority(Priority.HIGH).into(imageView);
    }

    //设置图片
    public void showImage(String url, ImageView imageView) {
        Glide.with(context).load(url).error(R.drawable.zwt).centerCrop().priority(Priority.HIGH).into(imageView);
    }

}
