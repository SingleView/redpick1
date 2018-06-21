package com.wlhb.hongbao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseFragment;
import com.wlhb.hongbao.ui.activity.RedPacketInformationActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class IssueFragment extends BaseFragment {
    @Nullable
    @BindView(R.id.top_menu_left_back)
    ImageView topMenuLeft;
    @BindView(R.id.top_menu_title)
    TextView topMenuTitle;
    @BindView(R.id.top_menu_right)
    ImageView rightMenu;
    @BindView(R.id.ll_fbhb)
    LinearLayout llFbhb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_issue, null);
        ButterKnife.bind(this, inflate);
        initView();
        initTitle();
        return inflate;
    }

    private void initTitle() {
        topMenuLeft.setVisibility(View.GONE);
        topMenuTitle.setText("发布");
        rightMenu.setVisibility(View.GONE);
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.ll_fbhb)
    public void onViewClicked() {

    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
