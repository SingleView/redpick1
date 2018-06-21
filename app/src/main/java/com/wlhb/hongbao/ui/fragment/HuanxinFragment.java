package com.wlhb.hongbao.ui.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.constant.Constant;
import com.wlhb.hongbao.constant.Constanthuanx;
import com.wlhb.hongbao.ui.activity.ChatActivity;


/**
 * Created by Administrator on 2018/5/18/018.
 */


public class HuanxinFragment extends EaseConversationListFragment  {


    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constanthuanx.EXTRA_CHAT_TYPE, Constanthuanx.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constanthuanx.EXTRA_CHAT_TYPE, Constanthuanx.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
                    intent.putExtra(Constanthuanx.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        super.setUpView();
    }
}
