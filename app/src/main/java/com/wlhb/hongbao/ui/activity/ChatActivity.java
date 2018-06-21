package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.constant.Constanthuanx;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/14/014.
 */

public class ChatActivity extends BaseActivity {

    @BindView(R.id.private_mes_container)
    FrameLayout mContainer;

    String toChatUsername;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.activity_chat, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
       setTitle("小圆畅聊");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toChatUsername = getIntent().getExtras().getString("userId");
        init();
    }

    private void init() {

        //聊天Fragment
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {

            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });

        //传入参数
        Bundle args = new Bundle();
        args.putInt(Constanthuanx.EXTRA_CHAT_TYPE, Constanthuanx.CHATTYPE_SINGLE);
        args.putString(Constanthuanx.EXTRA_USER_ID, toChatUsername);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.private_mes_container, chatFragment).commit();
    }
}