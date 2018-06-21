package com.wlhb.hongbao.ui.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;

/**
 * Created by tangyangkai on 16/6/12.
 */
public class SideslipRecyclerView extends RecyclerView {


    private int maxLength;
    private int mStartX = 0;
    private LinearLayout itemLayout;
    private int pos;
    private Rect mTouchFrame;
    private int xDown, xMove, yDown, yMove, mTouchSlop, xUp, yUp;
    private Scroller mScroller;
    private TextView textView;
    private ImageView imageView;
    private boolean isFirst = true;
    private static final String TAG = "lzy";
    private int dx;
    private int dy;


    public SideslipRecyclerView(Context context) {
        this(context, null);
    }

    public SideslipRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideslipRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //滑动的最大距离
        maxLength = ((int) (180 * context.getResources().getDisplayMetrics().density + 0.5f));
        //初始化Scroller
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:


                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(dy) < mTouchSlop * 5 && Math.abs(dx) > mTouchSlop) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }



}
