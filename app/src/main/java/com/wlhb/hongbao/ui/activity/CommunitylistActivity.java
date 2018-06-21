package com.wlhb.hongbao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.adapter.SpinnerListAdapter;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Communityinfo;
import com.wlhb.hongbao.bean.Communitymsglists;
import com.wlhb.hongbao.bean.Postlist;
import com.wlhb.hongbao.bean.Receivelists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.view.MarqueeTextView;
import com.wlhb.hongbao.ui.view.MySpinner;
import com.wlhb.hongbao.ui.view.StayScrollView;
import com.wlhb.hongbao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/30/030.
 */

public class CommunitylistActivity extends BaseActivity implements StayScrollView.OnScrollListener {
    @BindView(R.id.rv_sqtz)
    RecyclerView rvSqtz;
    @BindView(R.id.popw)
    View popw;
    @BindView(R.id.ll_tz)
    LinearLayout llTz;
    @BindView(R.id.tv_paixu)
    TextView tvPaixu;
    @BindView(R.id.spin_paixu)
    RelativeLayout spinPaixu;
    @BindView(R.id.tv_fenlei)
    TextView tvFenlei;
    @BindView(R.id.spin_fenlei)
    RelativeLayout spinFenlei;
    @BindView(R.id.ll_spinner)
    LinearLayout llSpinner;
    @BindView(R.id.v_top)
    LinearLayout vTop;
    @BindView(R.id.v_Bop)
    LinearLayout vBop;
    @BindView(R.id.iv_paixu)
    ImageView ivPaixu;
    @BindView(R.id.iv_fenlei)
    ImageView ivFenlei;
    @BindView(R.id.tv_sqjs)
    TextView tvSqjs;
    @BindView(R.id.mtv_ggbt1)
    MarqueeTextView mtvGgbt1;
    @BindView(R.id.mtv_ggbt2)
    MarqueeTextView mtvGgbt2;
    private PopupWindow popWindow;
    private StayScrollView stayScroll;
    private LinearLayout stayLayout;
    private LinearLayout stayScrollLayout;
    private int searchLayoutBot;
    private String[] lx_list;
    private String[] dx_list;
    private List<String> textArrays = new ArrayList<>();//设置公告滚动text
    List<Communitymsglists.DataListBean> dataListBeans = new ArrayList<>();
    private int communityid;
    private BaseQuickAdapter<Postlist.DataListBean, BaseViewHolder> sqplAdapter;
    private int adminId;
    private View contentView;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_communitylist, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitleColor(true);
        setTitle("帖子列表");
        setTitleMenu(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发帖,公告弹窗
                downPopwindow();
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
        //获取帖子列表所在的社区ID
        communityid = app.readInt("communityid", -1);
        //能顶部悬停的ScrollView
        stayScroll = (StayScrollView) findViewById(R.id.stayScrollView);
        stayScroll.setOnScrollListener(this);
        //顶部悬停时的LinearLayout
        stayLayout = (LinearLayout) findViewById(R.id.stayLayout);
        //非顶部悬停时替换此LinearLayout
        stayScrollLayout = (LinearLayout) findViewById(R.id.stayScrollLayout);
        lx_list = new String[]{"全部", "最新", "最热"};
        dx_list = new String[]{"失物招领", "杂谈怪论", "保洁需求"};

        rvSqtz.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        initAdapter();

        //获取社区公告
        Call<BaseResponse<Communityinfo>> callcommunityinfo = service.communityinfo(App.token, communityid);
        callcommunityinfo.enqueue(new BaseCallback<BaseResponse<Communityinfo>>(callcommunityinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<Communityinfo>> response) {
                BaseResponse<Communityinfo> body = response.body();
                if (body.isOK()) {
                    if (TextUtils.isEmpty(body.data.content)){
                        tvSqjs.setText("该社区还没有公告");
                    }else {
                        tvSqjs.setText(body.data.content);
                    }

                    adminId = body.data.userId;

                } else {
                    showToast(body.message);
                }
            }
        });

        //获取社区通知条目
        Call<BaseResponse<Communitymsglists>> callcommunitymsg = service.communitymsglists(App.token, 2, communityid);
        callcommunitymsg.enqueue(new BaseCallback<BaseResponse<Communitymsglists>>(callcommunitymsg, this) {
            @Override
            public void onResponse(Response<BaseResponse<Communitymsglists>> response) {
                BaseResponse<Communitymsglists> body = response.body();
                if (body.isOK()) {
                    dataListBeans.addAll(body.data.dataList);
                    for (int i = 0; i < dataListBeans.size(); i++) {
                        if (!TextUtils.isEmpty(dataListBeans.get(i).content) && dataListBeans.get(i).content.length() > 11){
                            textArrays.add(dataListBeans.get(i).content.substring(0,9)+"..."+TimeUtils.timeslash(dataListBeans.get(i).tstamp+""));
                        }else {
                            textArrays.add(dataListBeans.get(i).content+"..."+TimeUtils.timeslash(dataListBeans.get(i).tstamp+""));
                        }
                    }

                    //给MarqueeTextView设置数据
                    mtvGgbt1.setTextArrays(textArrays);
                    if (textArrays.size() > 1){
                        //第二个MarqueeTextView由第二条数据开始
                        mtvGgbt2.setTextArrays(textArrays.subList(1, textArrays.size()));
                    }
                } else {
                    showToast(body.message);
                }
            }
        });
        getPostData(1);
    }


    //获取帖子列表
    private void getPostData(int i) {
        Call<BaseResponse<Postlist>> callpostlist = service.postlist(App.token, 0, 10,  i, communityid, "", "", "");
        callpostlist.enqueue(new BaseCallback<BaseResponse<Postlist>>(callpostlist, this) {
            @Override
            public void onResponse(Response<BaseResponse<Postlist>> response) {
                BaseResponse<Postlist> body = response.body();
                if (body.isOK()) {
                    sqplAdapter.setNewData(body.data.dataList);
                    rvSqtz.setAdapter(sqplAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置帖子列表适配器
        sqplAdapter = new BaseQuickAdapter<Postlist.DataListBean, BaseViewHolder>(R.layout.item_tiezi) {
            @Override
            protected void convert(BaseViewHolder helper, final Postlist.DataListBean item) {
                helper.setText(R.id.tv_tzbt,"(" + item.typeName + ")" + item.name);
                helper.setText(R.id.cb_pl,item.commentNum+"");
                helper.setText(R.id.cb_zan,item.likeNum+"");
                helper.setText(R.id.tv_tzsj,TimeUtils.timeslash(item.tstamp+""));
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //记录社区管理员
                        app.writeInt("admin",adminId);
                        //记录帖子ID
                        app.writeInt("postid",item.id);
                        readyGo(InvitationActivity.class);
                    }
                });
            }
        };
    }

    //帖子列表右上弹窗
    private void downPopwindow() {
        if (App.id == adminId){
            contentView = LayoutInflater.from(this).inflate(R.layout.pop_sqlbgly, null);
            LinearLayout popfat = contentView.findViewById(R.id.pop_fat);
            LinearLayout popckjy = contentView.findViewById(R.id.pop_ckjy);
            LinearLayout popfbgg = contentView.findViewById(R.id.pop_fbgg);
            popfat.setOnClickListener(new MyListener());
            popckjy.setOnClickListener(new MyListener());
            popfbgg.setOnClickListener(new MyListener());
        }else {
            contentView = LayoutInflater.from(this).inflate(R.layout.pop_sqlb, null);
            LinearLayout popfat = contentView.findViewById(R.id.pop_fat);
            LinearLayout popsqjy = contentView.findViewById(R.id.pop_sqjy);
            popfat.setOnClickListener(new MyListener());
            popsqjy.setOnClickListener(new MyListener());
        }
        // 这里就给具体大小的数字，要不然位置不好计算
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(width, height);
        int measuredHeight = contentView.getMeasuredHeight();
        int measuredWidth = contentView.getMeasuredWidth();
        popWindow = new PopupWindow(contentView, measuredWidth, measuredHeight);
        popWindow.setOutsideTouchable(true);
        popWindow.showAsDropDown(popw, 770, -70);
    }

    @OnClick({R.id.spin_paixu, R.id.spin_fenlei, R.id.ll_tz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.spin_paixu://排序下拉框
                MySpinner pxSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, lx_list);
                pxSpinner.showAsDropDown(spinPaixu, 0, 0);//显示在rl_spinner的下方
                pxSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        tvPaixu.setText(lx_list[position]);
                    }
                });
                break;
            case R.id.spin_fenlei://分类下拉框
                MySpinner flSpinner = new MySpinner(this, ViewGroup.LayoutParams.MATCH_PARENT, dx_list);
                flSpinner.showAsDropDown(spinFenlei, 0, 0);//显示在rl_spinner的下方
                flSpinner.setOnItemClickListener(new SpinnerListAdapter.onItemClickListener() {
                    @Override
                    public void click(int position, View view) {
                        //根据分类获取帖子
                        tvFenlei.setText(dx_list[position]);
                        if (dx_list[position].equals("失物招领")){
                            getPostData(1);
                        }else if (dx_list[position].equals("杂谈怪论")){
                            getPostData(2);
                        }else {
                            getPostData(3);
                        }
                    }
                });
                break;
            case R.id.ll_tz://去社区公告
                readyGo(CommunityAnnouncementActivity.class);
                break;
        }
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_fat://发帖
                    readyGo(PostedActivity.class);
                    popWindow.dismiss();
                    break;
                case R.id.pop_sqjy://发布社区意见详情
                    readyGo(SuggestActivity.class);
                    popWindow.dismiss();
                    break;
                case R.id.pop_ckjy://查看意见详情
                    readyGo(OpinionParticularsActivity.class);
                    popWindow.dismiss();
                    break;
                case R.id.pop_fbgg://发布公告
                    readyGo(AnnounceActivity.class);
                    popWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    //获取主按键的具体位置和宽高
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            searchLayoutBot = vBop.getTop();
        }
    }

    //监听滚动Y值变化，通过addView和removeView来实现悬停效果
    @Override
    public void onScroll(int scrollY) {
        if (scrollY <= searchLayoutBot) {
            if (llSpinner.getParent() != stayScrollLayout) {
                stayLayout.removeView(llSpinner);
                stayScrollLayout.addView(llSpinner);
            }
        } else {
            if (llSpinner.getParent() != stayLayout) {
                stayScrollLayout.removeView(llSpinner);
                stayLayout.addView(llSpinner);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
