package com.wlhb.hongbao.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;

import com.wlhb.hongbao.base.IViewController;
import com.wlhb.hongbao.http.CallManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/26/026.
 */

public abstract class AbsPresenter implements IPresenter {

    public String TAG;

    protected IViewController view;
    protected Activity activity;
    protected List<Call> callList;

    public AbsPresenter() {
        TAG = getClass().getSimpleName();
    }

    @Override
    public void attachView(IViewController view) {
        Log.d(TAG, "attachView");
        this.view = view;
        if (view instanceof Activity) {
            this.activity = (Activity) view;
        } else if (view instanceof Fragment) {
            this.activity = ((Fragment) view).getActivity();
        } else {
            throw new RuntimeException("Unsupported view type");
        }
        callList = new ArrayList<>();
    }


    @Override
    public void loadData() {
        Log.d(TAG, "loadData");
    }

    @Override
    public void detachView() {
        Log.d(TAG, "detachView");
        CallManager.cancelAll();
        activity = null;
        view = null;
    }
}
