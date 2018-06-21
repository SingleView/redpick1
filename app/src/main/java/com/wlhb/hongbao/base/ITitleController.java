package com.wlhb.hongbao.base;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by hetwen on 16/8/17.
 */
public interface ITitleController {


    void setTitle(String title);

    void setTitleMenu(boolean isShow, View.OnClickListener listener);
//
    void setTitleBack(boolean isShow);

    void setTitleBar(boolean isShow);

    void setTitleColor(Boolean b);


//
//    void setTitleLeftText(String left, View.OnClickListener listener);
//
    void setTitleRightText(String right, View.OnClickListener listener);
//
//    void setTitleRightText(String right, boolean hasBorder, View.OnClickListener listener);
//
//    void setTitleRightIcon(int res, boolean hasNew, View.OnClickListener listener);
//
//    void setTitleRightIcon(int res, View.OnClickListener listener);
//
//    void clearTitleRightNew();
//
//    void clearTitleRight();

}
