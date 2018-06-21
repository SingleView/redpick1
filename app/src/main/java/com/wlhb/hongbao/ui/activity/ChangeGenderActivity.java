package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.adapter.SimpleArrayAdapter;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/8/008.
 */

public class ChangeGenderActivity extends BaseActivity {
    @BindView(R.id.sp_xb)
    Spinner spXb;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_changegender, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("选择性别");
        setTitleRightText("          提 交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = spXb.getSelectedItem().toString();

                if (gender.equals("请选择")) {
                    showToast("请选择性别");
                    return;
                }


                int xingbie = (gender.equals("保密") ? 0 :(gender.equals("男")? 1 : 2));


                //编辑性别
                Call<BaseResponse<JSON>> call = service.edit(App.token, null,null,xingbie,null,null);
                call.enqueue(new BaseCallback<BaseResponse<JSON>>(call, ChangeGenderActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {

                            finish();

                        } else {
                            showToast(body.message);
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        List<String> lx_list = new ArrayList<String>();
        lx_list.add("男");
        lx_list.add("女");
        lx_list.add("保密");
        lx_list.add("请选择");
        //适配器
        SimpleArrayAdapter arrAdapter = new SimpleArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lx_list);
        //设置样式
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spXb.setAdapter(arrAdapter);
        spXb.setSelection(lx_list.size() - 1, true);
    }

}
