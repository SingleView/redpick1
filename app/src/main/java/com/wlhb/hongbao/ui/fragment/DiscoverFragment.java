package com.wlhb.hongbao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.adapter.SpinnerListAdapter;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseFragment;
import com.wlhb.hongbao.bean.Category;
import com.wlhb.hongbao.bean.Catelists;
import com.wlhb.hongbao.bean.Memberlists;
import com.wlhb.hongbao.bean.Packetlists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.activity.CommunityActivity;
import com.wlhb.hongbao.ui.activity.FunnymanActivity;
import com.wlhb.hongbao.ui.activity.HomepageActivity;
import com.wlhb.hongbao.ui.activity.RedPacketParticularsActivity;
import com.wlhb.hongbao.ui.view.MySpinner;
import com.wlhb.hongbao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/30/030.
 */

public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.top_menu_left_back)
    ImageView topMenuLeft;
    @BindView(R.id.top_menu_title)
    TextView topMenuTitle;
    @BindView(R.id.top_menu_right)
    ImageView rightMenu;
    @BindView(R.id.tv_fujindongtai)
    TextView tvFujindongtai;
    @BindView(R.id.xiahuaxianyi)
    View xiahuaxianyi;
    @BindView(R.id.ll_fujindongtai)
    LinearLayout llFujindongtai;
    @BindView(R.id.tv_fujinderen)
    TextView tvFujinderen;
    @BindView(R.id.xiahuaxianer)
    View xiahuaxianer;
    @BindView(R.id.ll_fujinderen)
    LinearLayout llFujinderen;
    @BindView(R.id.sv_fujindongtai)
    ScrollView svFujindongtai;
    @BindView(R.id.sv_fujinderen)
    ScrollView svFujinderen;
    @BindView(R.id.rv_fjdt)
    RecyclerView rvFjdt;
    @BindView(R.id.rv_fjdr)
    RecyclerView rvFjdr;
    @BindView(R.id.rg_fjdt)
    RadioGroup rgFjdt;
    @BindView(R.id.rg_fjdr)
    RadioGroup rgFjdr;
    @BindView(R.id.rb_fujin)
    RadioButton rbFujin;
    @BindView(R.id.rb_quanshi)
    RadioButton rbQuanshi;
    @BindView(R.id.rb_quansheng)
    RadioButton rbQuansheng;
    @BindView(R.id.rb_quanguo)
    RadioButton rbQuanguo;
    @BindView(R.id.rb_meinv)
    RadioButton rbMeinv;
    @BindView(R.id.rb_shuaige)
    RadioButton rbShuaige;
    @BindView(R.id.rv_sheqv)
    RecyclerView rvSheqv;
    @BindView(R.id.rl_sx)
    RelativeLayout rlSx;
    private BaseQuickAdapter<Packetlists.DataListBean, BaseViewHolder> dynamicAdapter;
    private BaseQuickAdapter<Memberlists.DataListBean, BaseViewHolder> personAdapter;
    private float lat;
    private float lng;
    private BaseQuickAdapter<Catelists.DataListBean, BaseViewHolder> shouyeAdapter;
    private String fjprovince;
    private String fjcity;
    private String fjdistrict;
    private View contentView;
    private PopupWindow popWindow;
    private RecyclerView rvSx;
    private BaseQuickAdapter<Category, BaseViewHolder> shaixuanAdapter;
    private int category = 0;
    private int type = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_discover, null);
        ButterKnife.bind(this, inflate);
        initView();
        initTitle();
        return inflate;
    }

    private void initView() {
        //获取附近的地址和坐标
        fjprovince = app.readString("fjprovince", "");
        fjcity = app.readString("fjcity", "");
        fjdistrict = app.readString("fjdistrict", "");
        lat = app.readFloat("latitude", -2);
        lng = app.readFloat("longitude", -2);
        initAdapter();
        rvFjdt.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvFjdr.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvSheqv.setLayoutManager(linearLayoutManager);
        //获取附近的人
        getPersonData(1);
        //获取附近动态
        getDynamicData(type, category);
        //获取社区首页分类列表
        Call<BaseResponse<Catelists>> callcatelists = service.catelists(App.token);
        callcatelists.enqueue(new BaseCallback<BaseResponse<Catelists>>(callcatelists, this) {
            @Override
            public void onResponse(Response<BaseResponse<Catelists>> response) {
                BaseResponse<Catelists> body = response.body();
                if (body.isOK()) {
                    shouyeAdapter.setNewData(body.data.dataList);
                    rvSheqv.setAdapter(shouyeAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });

        //获取筛选分类
        Call<BaseResponse<List<Category>>> calldynamic = service.category(App.token);
        calldynamic.enqueue(new BaseCallback<BaseResponse<List<Category>>>(calldynamic, this) {
            @Override
            public void onResponse(Response<BaseResponse<List<Category>>> response) {
                BaseResponse<List<Category>> body = response.body();
                if (body.isOK()) {

                    shaixuanAdapter.setNewData(body.data);

                }
            }
        });
    }

    private void initTitle() {
        topMenuLeft.setVisibility(View.GONE);
        topMenuTitle.setText("发现");
    }

    @OnClick({R.id.ll_fujindongtai, R.id.ll_fujinderen, R.id.top_menu_right})
    public void onViewCClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_fujindongtai://点击显示附近动态
                svFujindongtai.setVisibility(View.VISIBLE);
                tvFujindongtai.setTextColor(getResources().getColor(R.color.red));
                xiahuaxianyi.setVisibility(View.VISIBLE);
                svFujinderen.setVisibility(View.GONE);
                tvFujinderen.setTextColor(getResources().getColor(R.color.black));
                xiahuaxianer.setVisibility(View.GONE);
                svFujindongtai.scrollTo(0, 0);
                svFujinderen.scrollTo(0, 0);
                break;
            case R.id.ll_fujinderen://点击显示附近的人
                svFujinderen.setVisibility(View.VISIBLE);
                tvFujinderen.setTextColor(getResources().getColor(R.color.red));
                xiahuaxianer.setVisibility(View.VISIBLE);
                svFujindongtai.setVisibility(View.GONE);
                tvFujindongtai.setTextColor(getResources().getColor(R.color.black));
                xiahuaxianyi.setVisibility(View.GONE);
                svFujinderen.scrollTo(0, 0);
                svFujindongtai.scrollTo(0, 0);
                break;
        }
    }


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @OnClick({R.id.rb_fujin, R.id.rb_quanshi, R.id.rb_quansheng, R.id.rb_quanguo, R.id.rb_meinv, R.id.rb_shuaige,R.id.rl_sx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_fujin://附近
                getDynamicData(type, category);
                break;
            case R.id.rb_quanshi://全市
                type = 2;
                getDynamicData(type, category);
                break;
            case R.id.rb_quansheng://全身
                type = 3;
                getDynamicData(type, category);
                break;
            case R.id.rb_quanguo://全国
                type = 4;
                getDynamicData(type, category);
                break;
            case R.id.rb_meinv://美女
                getPersonData(1);
                break;
            case R.id.rb_shuaige://帅哥
                getPersonData(2);
                break;
            case R.id.rl_sx://点击弹出筛选对话框
                downPopwindow();
                break;
        }
    }

    //获取附近的人数据
    private void getPersonData(int i) {
        Call<BaseResponse<Memberlists>> callperson = service.memberlists(App.token, i, lng, lat);
        callperson.enqueue(new BaseCallback<BaseResponse<Memberlists>>(callperson, this) {
            @Override
            public void onResponse(Response<BaseResponse<Memberlists>> response) {
                BaseResponse<Memberlists> body = response.body();
                if (body.isOK()) {
                    personAdapter.setNewData(body.data.dataList);
                    rvFjdr.setAdapter(personAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    //获取附近动态数据
    private void getDynamicData(int type,int category) {
        Call<BaseResponse<Packetlists>> calldynamic = service.packetlists(App.token, type, fjprovince, fjcity, fjdistrict, lng, lat,category);
        calldynamic.enqueue(new BaseCallback<BaseResponse<Packetlists>>(calldynamic, this) {
            @Override
            public void onResponse(Response<BaseResponse<Packetlists>> response) {
                BaseResponse<Packetlists> body = response.body();
                if (body.isOK()) {
                    dynamicAdapter.setNewData(body.data.dataList);
                    rvFjdt.setAdapter(dynamicAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    private void initAdapter() {
        //设置附近动态适配器
        dynamicAdapter = new BaseQuickAdapter<Packetlists.DataListBean, BaseViewHolder>(R.layout.item_dongtai) {

            @Override
            protected void convert(BaseViewHolder helper, final Packetlists.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.userImage, ivTouxiang);

                helper.setText(R.id.tv_neirong, item.content + "");
                helper.setText(R.id.tv_name, item.userName + "");
                helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
                helper.setText(R.id.cb_pinglun, item.commentsNum + "");
                helper.setText(R.id.cb_zan, item.favoriteNum + "");

                BGANinePhotoLayout nplItemMomentPhotos = helper.getView(R.id.npl_item_moment_photos);
                nplItemMomentPhotos.setData((ArrayList<String>) item.listImg);

                //点击附近红包动态去红包详情
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.writeInt("packetId", item.id);
                        readyGo(RedPacketParticularsActivity.class);
                    }
                });
            }
        };
        //设置附近的人适配器
        personAdapter = new BaseQuickAdapter<Memberlists.DataListBean, BaseViewHolder>(R.layout.item_fujinderen) {

            @Override
            protected void convert(BaseViewHolder helper, final Memberlists.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
                showImageTx(item.image, ivTouxiang);
                helper.setText(R.id.tv_juli, item.distance + "");
                helper.setText(R.id.tv_neirong, item.personalMark + "");
                helper.setText(R.id.tv_name, item.username + "");

                //点击附近的人去详细信息
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.writeInt("id", item.id);
                        readyGo(HomepageActivity.class);
                    }
                });
            }
        };

        //设置顶部社区分类适配器
        shouyeAdapter = new BaseQuickAdapter<Catelists.DataListBean, BaseViewHolder>(R.layout.item_shouyeliebiao) {

            @Override
            protected void convert(final BaseViewHolder helper, final Catelists.DataListBean item) {
                CircleImageView tubiao = helper.getView(R.id.iv_sqlbtb);
                showImage(item.image, tubiao);
                helper.setText(R.id.tv_sqlbmc, item.name + "");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //区分有趣的人和其他社区
                        if (item.name.equals("有趣的人")) {
                            readyGo(FunnymanActivity.class);
                        } else {
                            app.writeInt("categoryId", item.id);
                            readyGo(CommunityActivity.class);
                        }
                    }
                });
            }
        };

        //设置筛选适配器
        shaixuanAdapter = new BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_shaixuan) {

            @Override
            protected void convert(final BaseViewHolder helper, final Category item) {
                helper.setText(R.id.tv_fenlei,item.name);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        category = item.id;
                        //点击分类刷新
                        getDynamicData(type, category);
                        popWindow.dismiss();
                    }
                });
            }
        };
    }


    //筛选弹窗
    private void downPopwindow() {
        // 这里就给具体大小的数字，要不然位置不好计算
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_saixuan, null);
        rvSx = contentView.findViewById(R.id.rv_sx);
        //瀑布流布局
        rvSx.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        rvSx.setAdapter(shaixuanAdapter);
        popWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setOutsideTouchable(true);
        popWindow.showAsDropDown(rlSx, 0, 0);
    }
}

