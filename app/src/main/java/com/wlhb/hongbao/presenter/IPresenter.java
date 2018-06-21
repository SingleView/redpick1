package com.wlhb.hongbao.presenter;

import com.wlhb.hongbao.base.IViewController;

/**
 * Created by Administrator on 2018/3/25/025.
 */

public interface IPresenter {

    public void attachView(IViewController view);

    public void loadData();

    public void detachView();


}
