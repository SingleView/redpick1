package com.wlhb.hongbao.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JS01 on 2016/6/7.
 */
public interface IViewController {

    public View loadView(LayoutInflater inflater, ViewGroup container);

//    public void initPresenter();
//
//    public <T> void updateView(T t);

    public void showLoading();

    public void hideLoading();

}
