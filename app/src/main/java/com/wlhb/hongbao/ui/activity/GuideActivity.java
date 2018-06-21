package com.wlhb.hongbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/23/023.
 */

public class GuideActivity extends BaseActivity {


    @BindView(R.id.vps)
    ViewPager vps;
    @BindView(R.id.guide_cpi)
    CirclePageIndicator mCirclePageIndicator;
    @BindView(R.id.guide_btn)
    Button mButton;
    @BindView(R.id.tv_pass)
    Button tvPass;
    private int[] images = new int[]{R.drawable.qidongye1, R.drawable.qidongye2, R.drawable.qidongye3};

    private List<ImageView> views = new ArrayList<ImageView>();


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_guide, container, false);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleBar(false);
        //设置启动页不能侧滑删除
        getSwipeBackLayout().setEnableGesture(false);
    }


    //设置启动页数据
    int[] mPicRes = new int[]{R.drawable.qidongye1, R.drawable.qidongye2, R.drawable.qidongye3};
    PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mPicRes == null ? 0 : mPicRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(mPicRes[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    //点击立即体验去登录界面
    @OnClick(R.id.guide_btn)
    public void goMainActivity(View v) {
        SpUtils.writeFirstRun(v.getContext());

        startActivity(new Intent(this, EnterActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (position == mPicRes.length - 1) { // 当页面滑动到最后一个时
                mButton.setVisibility(View.VISIBLE);
                mCirclePageIndicator.setVisibility(View.GONE);
            } else {
                mButton.setVisibility(View.INVISIBLE);
            }

            if (position == mPicRes.length - 3) { // 当页面滑动到最后一个时
                tvPass.setVisibility(View.VISIBLE);
            } else {
                tvPass.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            views.add(imageView);
        }
        vps.setAdapter(mPagerAdapter);
        vps.addOnPageChangeListener(mOnPageChangeListener);
        mOnPageChangeListener.onPageSelected(0);
        mCirclePageIndicator.setViewPager(vps);
        // 滑动点的颜色
        mCirclePageIndicator.setFillColor(Color.WHITE);
        // 固定点的颜色
        mCirclePageIndicator.setPageColor(R.color.graybj);
        mCirclePageIndicator.setStrokeWidth(0);
        // 大小
        float radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        mCirclePageIndicator.setRadius(radius);

    }

    //跳过去登录页
    @OnClick(R.id.tv_pass)
    public void onViewClicked() {
        readyGo(EnterActivity.class);
        finish();
    }

}