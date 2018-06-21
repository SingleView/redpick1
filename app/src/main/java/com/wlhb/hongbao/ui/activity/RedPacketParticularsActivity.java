package com.wlhb.hongbao.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Commentlist;
import com.wlhb.hongbao.bean.PacketInfo;
import com.wlhb.hongbao.bean.Receivelists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.pay.wxpay.Constants;
import com.wlhb.hongbao.utils.TimeUtils;
import com.wlhb.hongbao.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class RedPacketParticularsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate {

    @BindView(R.id.popw)
    View popw;
    @BindView(R.id.rv_ylq)
    RecyclerView rvYlq;
    @BindView(R.id.rv_pinglun)
    RecyclerView rvPinglun;
    @BindView(R.id.tv_xuf)
    TextView tvXuf;
    @BindView(R.id.iv_tx)
    CircleImageView ivTx;
    @BindView(R.id.tv_neirong)
    TextView tvNeirong;
    @BindView(R.id.tv_yuedu)
    TextView tvYuedu;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_hbzt)
    TextView tvHbzt;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.npl_item_moment_photos)
    BGANinePhotoLayout nplItemMomentPhotos;
    @BindView(R.id.et_pl)
    EditText etPl;
    @BindView(R.id.bt_fbpl)
    Button btFbpl;
    @BindView(R.id.rl_hbxq)
    LinearLayout rlHbxq;
    @BindView(R.id.rl_my)
    RelativeLayout rlMy;
    @BindView(R.id.sv_hbxq)
    ScrollView svHbxq;
    @BindView(R.id.cb_shoucang)
    CheckBox cbShoucang;
    @BindView(R.id.cb_fenxiang)
    CheckBox cbFenxiang;
    @BindView(R.id.cb_dianzan)
    CheckBox cbDianzan;
    @BindView(R.id.tv_fanwei)
    TextView tvFanwei;
    private PopupWindow popWindow;
    private TextView tvlh;
    private static final int PRC_PHOTO_PREVIEW = 1;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
    private String content;
    private int packetId;
    private int userId;
    private BaseQuickAdapter<Receivelists.DataListBean, BaseViewHolder> lqrAdapter;
    private int quantity;
    private String userName;
    private int status;
    private String image;
    private String fromUserName;
    private EditText inputServer;
    private ItemDragAdapter hbplAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private View contentView;
    private int id;
    private RecyclerView pinglunhuifu;


    private IWXAPI api;
    private Dialog dialog;
    private TextView tv_haoyou;
    private TextView tv_pyq;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_redpacketparticulars, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("红包详情");
        setTitleMenu(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downPopwindow();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initData();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
    }

    private void initData() {
        packetId = app.readInt("packetId", -1);
        nplItemMomentPhotos.setDelegate(RedPacketParticularsActivity.this);
        rvPinglun.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        //不是本人的红包不能续费
        rvPinglun.setClickable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvYlq.setLayoutManager(linearLayoutManager);
        //获取设置红包信息
        Call<BaseResponse<PacketInfo>> callpacketinfo = service.packetinfo(App.token, packetId);
        callpacketinfo.enqueue(new BaseCallback<BaseResponse<PacketInfo>>(callpacketinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<PacketInfo>> response) {
                BaseResponse<PacketInfo> body = response.body();
                if (body.isOK()) {
                    tvNeirong.setText(body.data.content);
                    tvYuedu.setText(body.data.hit + "阅读");
                    tvZan.setText(body.data.like + "赞");
                    tvName.setText(body.data.userName + "的红包");
                    showImageTx(body.data.userImage, ivTx);
                    userId = body.data.userId;
                    if (App.id != userId) {
                        tvXuf.setVisibility(View.GONE);
                    }else {
                        tvXuf.setVisibility(View.VISIBLE);
                    }
                    quantity = body.data.quantity;
                    status = body.data.status;
                    userName = body.data.userName;
                    image = body.data.userImage;
                    tvFanwei.setText(body.data.type == 0? " 全市 ":" 两公里 ");
                    tvHbzt.setText(body.data.userReceiveMoney + "元");
                    cbDianzan.setChecked(body.data.isLike == 1);
                    cbShoucang.setChecked(body.data.isFavorite == 1);

                    nplItemMomentPhotos.setData((ArrayList<String>) body.data.listImg);
                } else {
                    showToast(body.message);
                }
            }
        });
        initAdapter();
        //获取红包领取列表
        Call<BaseResponse<Receivelists>> call = service.receivelists(App.token, packetId);
        call.enqueue(new BaseCallback<BaseResponse<Receivelists>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<Receivelists>> response) {
                BaseResponse<Receivelists> body = response.body();
                if (body.isOK()) {
                    lqrAdapter.setNewData(body.data.dataList);
                    rvYlq.setAdapter(lqrAdapter);
                }
            }
        });
        getCommentlist();
    }

    //获取更新评论
    private void getCommentlist() {
        Call<BaseResponse<Commentlist>> callcommentlist = service.commentlist(App.token, packetId);
        callcommentlist.enqueue(new BaseCallback<BaseResponse<Commentlist>>(callcommentlist, this) {

            @Override
            public void onResponse(Response<BaseResponse<Commentlist>> response) {
                BaseResponse<Commentlist> body = response.body();
                if (body.isOK()) {
                    hbplAdapter = new ItemDragAdapter(body.data.dataList);
                    mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(hbplAdapter);
                    mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
                    //如果不是自己的红包关闭评论删除
                    if (App.id == userId) {
                        mItemTouchHelper.attachToRecyclerView(rvPinglun);
                        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
                        hbplAdapter.enableSwipeItem();
                        hbplAdapter.setOnItemSwipeListener(onItemSwipeListener);
                        hbplAdapter.enableDragItem(mItemTouchHelper);
                    }

                    rvPinglun.setAdapter(hbplAdapter);

                }
            }
        });
    }

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RedPacketParticularsActivity.this);
            builder.setTitle("是否删除此条评论?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCommentlist();
                        }
                    });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Call<BaseResponse<JSON>> callcommentdel = service.commentdel(App.token, id
                    );
                    callcommentdel.enqueue(new BaseCallback<BaseResponse<JSON>>(callcommentdel, RedPacketParticularsActivity.this) {
                        @Override
                        public void onResponse(Response<BaseResponse<JSON>> response) {
                            BaseResponse<JSON> body = response.body();
                            if (body.isOK()) {
                                //成功刷新评论状态
                                getCommentlist();
                            } else {
                                //失败还原评论状态
                                getCommentlist();
                                showToast(body.message);
                            }
                        }
                    });
                }
            });
            builder.show();
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            canvas.drawColor(ContextCompat.getColor(RedPacketParticularsActivity.this, R.color.red));

        }
    };

    private void initAdapter() {
        //领取人适配器
        lqrAdapter = new BaseQuickAdapter<Receivelists.DataListBean, BaseViewHolder>(R.layout.item_lingqvren) {

            @Override
            protected void convert(BaseViewHolder helper, Receivelists.DataListBean item) {
                CircleImageView cvTouxiang = helper.getView(R.id.cv_touxiang);
                showImageTx(item.toUserImage, cvTouxiang);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //记录相关参数去红包界面
                        app.writeInt("packetId", packetId);
                        app.writeInt("id", userId);
                        app.writeInt("quantity", quantity);
                        app.writeInt("status", status);
                        app.writeString("userName", userName);
                        app.writeString("image", image);
                        readyGo(GteDetailsActivity.class);
                    }
                });
            }
        };
    }

    //带侧滑功能的Adapter
    class ItemDragAdapter extends BaseItemDraggableAdapter<Commentlist.DataListBean, BaseViewHolder> {
        public ItemDragAdapter(List data) {
            super(R.layout.item_hongbaopinglun, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final Commentlist.DataListBean item) {
            CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
            showImageTx(item.fromUserImage, ivTouxiang);
            helper.setText(R.id.tv_name, item.fromUserName);
            helper.setText(R.id.tv_neirong, item.content);
            helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
            pinglunhuifu = helper.getView(R.id.rv_pinglunhuifu);
            pinglunhuifu.setLayoutManager(new LinearLayoutManager(RedPacketParticularsActivity.this));

            //回复的评论适配器
            BaseQuickAdapter<Commentlist.DataListBean.ItemListBean, BaseViewHolder> plhfAdapter = new BaseQuickAdapter<Commentlist.DataListBean.ItemListBean, BaseViewHolder>(R.layout.item_pinglunhuifu) {

                @Override
                protected void convert(BaseViewHolder helper, Commentlist.DataListBean.ItemListBean item) {

                    helper.setText(R.id.tv_neirong, item.fromUserName+"回复:" + item.content);
                }
            };
            //设置回复数据
            plhfAdapter.setNewData(item.itemList);
            pinglunhuifu.setAdapter(plhfAdapter);
            //点击头像去评论人主页
            helper.getView(R.id.iv_touxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    app.writeInt("id",item.fromUserId);
                    readyGo(HomepageActivity.class);
                }
            });
            //点击内容回复评论
            helper.getView(R.id.tv_neirong).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = item.id;
                    fromUserName = item.fromUserName;
                    inputServer = new EditText(RedPacketParticularsActivity.this);
                    inputServer.setHint("回复" + fromUserName + ":");
                    //回复评论对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(RedPacketParticularsActivity.this);
                    builder.setTitle("回复" + fromUserName + ":").setView(inputServer)
                            .setNegativeButton("取消", null);
                    builder.setPositiveButton("回复", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String content = inputServer.getText().toString();

                            if (TextUtils.isEmpty(content)) {
                                showToast("内容不能为空");
                                return;
                            }

                            Call<BaseResponse<JSON>> callhuifu = service.comment(App.token, packetId,
                                    content,
                                    id,
                                    1
                            );
                            callhuifu.enqueue(new BaseCallback<BaseResponse<JSON>>(callhuifu, RedPacketParticularsActivity.this) {
                                @Override
                                public void onResponse(Response<BaseResponse<JSON>> response) {
                                    BaseResponse<JSON> body = response.body();
                                    if (body.isOK()) {
                                        getCommentlist();
                                    } else {
                                        showToast(body.message);
                                    }
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    //右上弹窗菜单
    private void downPopwindow() {
        // 这里就给具体大小的数字，要不然位置不好计算
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //设置自己和别人的弹窗内容
        if (App.id != userId) {
            contentView = LayoutInflater.from(this).inflate(R.layout.pop_hbxq, null);
            LinearLayout popfx = contentView.findViewById(R.id.pop_fx);
            LinearLayout poptous = contentView.findViewById(R.id.pop_tous);
            LinearLayout pophmd = contentView.findViewById(R.id.pop_hmd);
            popfx.setOnClickListener(new MyListener());
            poptous.setOnClickListener(new MyListener());
            pophmd.setOnClickListener(new MyListener());
        } else {
            contentView = LayoutInflater.from(this).inflate(R.layout.pop_hbxqmy, null);
            LinearLayout popfx = contentView.findViewById(R.id.pop_fx);
            LinearLayout poptous = contentView.findViewById(R.id.pop_tous);
            LinearLayout pophbxf = contentView.findViewById(R.id.pop_hbxf);
            LinearLayout pophbwz = contentView.findViewById(R.id.pop_hbwz);
            popfx.setOnClickListener(new MyListener());
            poptous.setOnClickListener(new MyListener());
            pophbxf.setOnClickListener(new MyListener());
            pophbwz.setOnClickListener(new MyListener());
        }
        contentView.measure(width, height);
        int measuredHeight = contentView.getMeasuredHeight();
        int measuredWidth = contentView.getMeasuredWidth();
        popWindow = new PopupWindow(contentView, measuredWidth, measuredHeight);
        popWindow.setOutsideTouchable(true);
        popWindow.showAsDropDown(popw, -300, -50);

    }


    @OnClick({R.id.iv_tx, R.id.tv_xuf, R.id.bt_fbpl, R.id.cb_fenxiang,R.id.cb_dianzan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tx://点击头像去详细信息
                app.writeInt("id", userId);
                readyGo(HomepageActivity.class);
                finish();
                break;
            case R.id.tv_xuf://点击去续费
                readyGo(RenewActivity.class);
                break;
            case R.id.cb_dianzan://点赞
                Call<BaseResponse<JSON>> calllike = service.like(App.token, packetId);
                calllike.enqueue(new BaseCallback<BaseResponse<JSON>>(calllike, this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            getZan();
                        } else {
                            showToast(body.message);
                        }
                    }
                });
                break;
            case R.id.cb_fenxiang://分享
                showdialog();
                break;
            case R.id.bt_fbpl://底部发布评论
                content = etPl.getText().toString();
                Call<BaseResponse<JSON>> callcomment = service.comment(App.token, packetId,
                        content,
                        null,
                        0
                );
                callcomment.enqueue(new BaseCallback<BaseResponse<JSON>>(callcomment, RedPacketParticularsActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            etPl.setText("");
                            getCommentlist();
                            svHbxq.smoothScrollTo(0, 0);
                        } else {
                            showToast(body.message);
                        }
                    }
                });
                break;
        }
    }

    //更新点赞
    private void getZan() {
        Call<BaseResponse<PacketInfo>> callpacketinfo = service.packetinfo(App.token, packetId);
        callpacketinfo.enqueue(new BaseCallback<BaseResponse<PacketInfo>>(callpacketinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<PacketInfo>> response) {
                BaseResponse<PacketInfo> body = response.body();
                if (body.isOK()) {
                    tvZan.setText(body.data.like + "赞");
                }
            }
        });
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        nplItemMomentPhotos = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PREVIEW) {
            Toast.makeText(this, "您拒绝了「图片预览」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }


    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_fx://分享
                    showdialog();
                    popWindow.dismiss();
                    break;
                case R.id.pop_tous://投诉红包
                    readyGo(ComplainActivity.class);
                    popWindow.dismiss();
                    break;
                case R.id.pop_hmd://拉黑
                    if (tvlh.getText() == "  拉黑该用户") {
                        tvlh.setText("  取消拉黑");
                        Call<BaseResponse<JSON>> callpull = service.pullblack(App.token, userId);
                        callpull.enqueue(new BaseCallback<BaseResponse<JSON>>(callpull, RedPacketParticularsActivity.this) {
                            @Override
                            public void onResponse(Response<BaseResponse<JSON>> response) {
                                BaseResponse<JSON> body = response.body();
                                if (body.isOK()) {
                                    showToast(body.message);
                                } else {
                                    showToast(body.message);
                                }
                            }
                        });

                    } else {
                        tvlh.setText("  拉黑该用户");
                        Call<BaseResponse<JSON>> callremove = service.removeblack(App.token, userId);
                        callremove.enqueue(new BaseCallback<BaseResponse<JSON>>(callremove, RedPacketParticularsActivity.this) {
                            @Override
                            public void onResponse(Response<BaseResponse<JSON>> response) {
                                BaseResponse<JSON> body = response.body();
                                if (body.isOK()) {
                                    showToast(body.message);
                                } else {
                                    showToast(body.message);
                                }
                            }
                        });
                    }
                    break;
                case R.id.pop_hbxf://红包续费
                    readyGo(RenewActivity.class);
                    popWindow.dismiss();
                    break;
                case R.id.pop_hbwz://红包位置
                    readyGo(GetLocationActivity.class);
                    popWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    //分享对话框
    public void showdialog() {
        View localView = LayoutInflater.from(this).inflate(
                R.layout.dialog_add_share, null);
        tv_haoyou = (TextView) localView.findViewById(R.id.tv_camera);
        tv_pyq = (TextView) localView.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_haoyou.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                WXWebpageObject wxWebpageObject = new WXWebpageObject();
                wxWebpageObject.webpageUrl = "http://www.blbuy.com.cn/hongbao/share/shareapp?inviteCode=1004621519";
                WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
                wxMediaMessage.title = "小圆";
                wxMediaMessage.description = "网领红包";
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                wxMediaMessage.thumbData = Util.bmpToByteArray(bitmap, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("text");
                req.message = wxMediaMessage;
                req.scene = SendMessageToWX.Req.WXSceneSession;//好友分享
                api.sendReq(req);
            }
        });

        tv_pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                WXWebpageObject wxWebpageObject = new WXWebpageObject();
                wxWebpageObject.webpageUrl = "http://www.blbuy.com.cn/hongbao/share/shareapp?inviteCode=1004621519";
                WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
                wxMediaMessage.title = "小圆";
                wxMediaMessage.description = "网领红包";
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                wxMediaMessage.thumbData = Util.bmpToByteArray(bitmap, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("text");
                req.message = wxMediaMessage;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;//朋友圈分享
                api.sendReq(req);
            }
        });
    }

    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(PRC_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (nplItemMomentPhotos == null) {
            return;
        }
        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (nplItemMomentPhotos.getItemCount() == 1) {
                // 预览单张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(this, downloadDir, nplItemMomentPhotos.getCurrentClickItem()));
            } else if (nplItemMomentPhotos.getItemCount() > 1) {
                // 预览多张图片

                startActivity(BGAPhotoPreviewActivity.newIntent(this, downloadDir, nplItemMomentPhotos.getData(), nplItemMomentPhotos.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cbShoucang.isChecked()) {
            Call<BaseResponse<JSON>> calllike = service.packetfavorite(App.token, packetId);
            calllike.enqueue(new BaseCallback<BaseResponse<JSON>>(calllike, this) {
                @Override
                public void onResponse(Response<BaseResponse<JSON>> response) {
                    BaseResponse<JSON> body = response.body();
                    if (body.isOK()) {
                    } else {
                        showToast(body.message);
                    }
                }
            });
        }
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
