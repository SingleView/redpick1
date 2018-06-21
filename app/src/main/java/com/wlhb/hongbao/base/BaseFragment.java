package com.wlhb.hongbao.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.http.APIService;
import com.wlhb.hongbao.http.Http;



/**
 * Created by Administrator on 2018/3/25/025.
 */

public abstract class BaseFragment extends Fragment implements IViewController{

    protected APIService service;
    protected App app;
    protected Context appContext;
    protected Context context;
    protected Activity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusTint();

//        ButterKnife.bind(getActivity());
        SDKInitializer.initialize(getActivity().getApplicationContext());
        service = Http.getService();
        initContext();
    }

    protected void initContext() {
        app = App.getInstance();
        appContext = App.getContext();
        context = getActivity();
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 沉浸式状态栏
     */
    private void initStatusTint() {
        if (isStatusBarTint()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SystemBarTintManager tintManager = new SystemBarTintManager(
                        getActivity());
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintResource(getStatusTintColor());
            }
        }
    }

    protected boolean isStatusBarTint() {
        return true;
    }

    protected int getStatusTintColor() {
        return R.color.transparent;
    }

    protected void readyGo(Class<? extends Activity> claz) {
        readyGo(claz, null);
    }

    /**
     * 指定Activity跳转，带参数
     *
     * @param claz 组件名
     * @param data 参数
     */
    protected void readyGo(Class<? extends Activity> claz, Bundle data) {
        Intent intent = new Intent(getActivity(), claz);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }


    protected void readyGo(Class<? extends Activity> claz, int requestCode) {
        readyGo(claz, null, requestCode);
    }

    protected void readyGo(Class<? extends Activity> claz, Bundle data, int requestCode) {
        Intent intent = new Intent(getActivity(), claz);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showToast(String str) {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showToast(str);
        }
    }

    @Override
    public void showLoading() {
        Activity activity = getActivity();
        if (activity != null
                && activity instanceof IViewController) {
            ((IViewController) activity).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        Activity activity = getActivity();
        if (activity != null
                && activity instanceof IViewController) {
            ((IViewController) activity).hideLoading();
        }
    }


    public void showImageTx(String url, ImageView imageView) {
        Glide.with(context).load(url).error(R.drawable.logo).centerCrop().priority(Priority.HIGH).into(imageView);
    }

    public void showImage(String url, ImageView imageView) {
        Glide.with(context).load(url).error(R.drawable.zwt).centerCrop().priority(Priority.HIGH).into(imageView);
    }

}
