package com.wlhb.hongbao.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.Postcommentslists;
import com.wlhb.hongbao.bean.Postinfo;
import com.wlhb.hongbao.bean.Postlikelists;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.utils.TimeUtils;

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
 * Created by Administrator on 2018/4/8/008.
 */

public class InvitationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate {

    @BindView(R.id.iv_touxiang)
    CircleImageView ivTouxiang;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_shijian)
    TextView tvShijian;
    @BindView(R.id.tv_tznr)
    TextView tvTznr;
    @BindView(R.id.rv_fangke)
    RecyclerView rvFangke;
    @BindView(R.id.tv_plts)
    TextView tvPlts;
    @BindView(R.id.rl_pl)
    RecyclerView rvPinglun;
    @BindView(R.id.et_pl)
    EditText etPl;
    @BindView(R.id.bt_fbpl)
    Button btFbpl;
    @BindView(R.id.popw)
    View popw;
    @BindView(R.id.npl_item_moment_photos)
    BGANinePhotoLayout nplItemMomentPhotos;
    @BindView(R.id.cb_zan)
    CheckBox cbZan;
    @BindView(R.id.sv_tiez)
    ScrollView svTiez;
    private PopupWindow popWindow;
    private static final int PRC_PHOTO_PREVIEW = 1;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private String fromUserName;
    private int ccid;
    private int postid;
    private BaseQuickAdapter<Postlikelists.DataListBean, BaseViewHolder> dzrAdapter;
    private ItemDragAdapter sqplAdapter;
    private EditText inputServer;
    private int id;
    private String content;
    private int admin;
    private RecyclerView pinglunhuifu;
    private int pid;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_invitation, container, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("帖子");
        setTitleColor(true);

        //如果用户为社区管理员弹出帖子管理弹窗
        if (App.id == admin) {
            setTitleMenu(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downPopwindow();
                }
            });
        }
    }

    //顶置帖子和删除帖子弹窗
    private void downPopwindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_tiezi, null);
        LinearLayout popdztz = contentView.findViewById(R.id.pop_dztz);
        LinearLayout popsctz = contentView.findViewById(R.id.pop_sctz);
        popdztz.setOnClickListener(new MyListener());
        popsctz.setOnClickListener(new MyListener());
        // 这里就给具体大小的数字，要不然位置不好计算
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        contentView.measure(width, height);
        int measuredHeight = contentView.getMeasuredHeight();
        int measuredWidth = contentView.getMeasuredWidth();
        popWindow = new PopupWindow(contentView, measuredWidth, measuredHeight);
        popWindow.setOutsideTouchable(true);
        //在某一view的xy轴方向显示弹窗
        popWindow.showAsDropDown(popw, -300, -50);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_dztz://顶置帖子
                    popWindow.dismiss();
                    break;
                case R.id.pop_sctz://删除帖子
                    Call<BaseResponse<JSON>> callpostdel = service.postdel(App.token, postid);
                    callpostdel.enqueue(new BaseCallback<BaseResponse<JSON>>(callpostdel, InvitationActivity.this) {
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
                    popWindow.dismiss();
                    break;
            }
        }
    }


    private void init() {
        //获取帖子ID和管理员ID
        postid = app.readInt("postid", -1);
        admin = app.readInt("admin", -1);
        initAdapter();
        //开启图片预览
        nplItemMomentPhotos.setDelegate(InvitationActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvFangke.setLayoutManager(linearLayoutManager);
        rvPinglun.setClickable(false);
        rvPinglun.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        //获取设置帖子信息
        Call<BaseResponse<Postinfo>> callpostinfo = service.postinfo(App.token, postid);
        callpostinfo.enqueue(new BaseCallback<BaseResponse<Postinfo>>(callpostinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<Postinfo>> response) {
                BaseResponse<Postinfo> body = response.body();
                if (body.isOK()) {
                    tvTznr.setText(body.data.content);
                    tvShijian.setText(TimeUtils.timeslash(body.data.tstamp + ""));
                    cbZan.setText(" " + body.data.likeNum);
                    tvName.setText(body.data.name);
                    showImageTx(body.data.image, ivTouxiang);
                    tvPlts.setText("共" + body.data.commentNum + "条评论");
                    nplItemMomentPhotos.setData((ArrayList<String>) body.data.imageList);
                } else {
                    showToast(body.message);
                }
            }
        });

        getPostlikelists();
        getCommentlist();
    }

    //获取帖子点赞列表
    private void getPostlikelists() {
        Call<BaseResponse<Postlikelists>> callpostlikelists = service.postlikelists(App.token, postid);
        callpostlikelists.enqueue(new BaseCallback<BaseResponse<Postlikelists>>(callpostlikelists, this) {
            @Override
            public void onResponse(Response<BaseResponse<Postlikelists>> response) {
                BaseResponse<Postlikelists> body = response.body();
                if (body.isOK()) {
                    dzrAdapter.setNewData(body.data.dataList);
                    rvFangke.setAdapter(dzrAdapter);
                } else {
                    showToast(body.message);
                }
            }
        });
    }

    //设置和更新赞和评论的数量
    private void getZan() {
        Call<BaseResponse<Postinfo>> callpostinfo = service.postinfo(App.token, postid);
        callpostinfo.enqueue(new BaseCallback<BaseResponse<Postinfo>>(callpostinfo, this) {
            @Override
            public void onResponse(Response<BaseResponse<Postinfo>> response) {
                BaseResponse<Postinfo> body = response.body();
                if (body.isOK()) {
                    cbZan.setText(" " + body.data.likeNum);
                    tvPlts.setText("共" + body.data.commentNum + "条评论");
                }
            }
        });
    }

    private void initAdapter() {
        //设置访客适配器
        dzrAdapter = new BaseQuickAdapter<Postlikelists.DataListBean, BaseViewHolder>(R.layout.item_fangke) {
            @Override
            protected void convert(BaseViewHolder helper, Postlikelists.DataListBean item) {
                CircleImageView ivTouxiang = helper.getView(R.id.cv_touxiang);
                showImageTx(item.image, ivTouxiang);

            }
        };
    }

    //获取帖子列表
    private void getCommentlist() {
        Call<BaseResponse<Postcommentslists>> callcommentlist = service.postcommentslists(App.token, 0, 10, postid);
        callcommentlist.enqueue(new BaseCallback<BaseResponse<Postcommentslists>>(callcommentlist, this) {

            @Override
            public void onResponse(Response<BaseResponse<Postcommentslists>> response) {
                BaseResponse<Postcommentslists> body = response.body();
                if (body.isOK()) {
                    sqplAdapter = new ItemDragAdapter(body.data.dataList);
                    mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(sqplAdapter);
                    mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
                    //如果不是管理员禁止删除评论
                    if (App.id == admin) {
                        mItemTouchHelper.attachToRecyclerView(rvPinglun);
                        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
                        sqplAdapter.enableSwipeItem();
                        sqplAdapter.setOnItemSwipeListener(onItemSwipeListener);
                        sqplAdapter.enableDragItem(mItemTouchHelper);
                    }
                    rvPinglun.setAdapter(sqplAdapter);

                }
            }
        });
    }

    //评论滑动监听
    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

        }

        @Override
        //评论滑动结束是弹出是否删除对话框
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            AlertDialog.Builder builder = new AlertDialog.Builder(InvitationActivity.this);
            builder.setTitle("是否删除此条评论?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCommentlist();
                        }
                    });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Call<BaseResponse<JSON>> callcommentdel = service.postcommentsdel(App.token, pid
                    );
                    callcommentdel.enqueue(new BaseCallback<BaseResponse<JSON>>(callcommentdel, InvitationActivity.this) {
                        @Override
                        public void onResponse(Response<BaseResponse<JSON>> response) {
                            BaseResponse<JSON> body = response.body();
                            if (body.isOK()) {
                                getCommentlist();
                                svTiez.smoothScrollTo(0, 0);
                            } else {
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
        //设置评论滑动时的背景颜色
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            canvas.drawColor(ContextCompat.getColor(InvitationActivity.this, R.color.blue));
        }
    };

    //设置评论适配器
    class ItemDragAdapter extends BaseItemDraggableAdapter<Postcommentslists.DataListBean, BaseViewHolder> {
        public ItemDragAdapter(List data) {
            super(R.layout.item_tiezipinglun, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final Postcommentslists.DataListBean item) {
            CircleImageView ivTouxiang = helper.getView(R.id.iv_touxiang);
            showImageTx(item.fromUserImage, ivTouxiang);
            helper.setText(R.id.tv_name, item.fromUserName);
            helper.setText(R.id.tv_neirong, item.content);
            helper.setText(R.id.tv_shijian, TimeUtils.timeslash(item.tstamp + ""));
            pinglunhuifu = helper.getView(R.id.rv_pinglunhuifu);
            pinglunhuifu.setLayoutManager(new LinearLayoutManager(InvitationActivity.this));

            //设置评论回复适配器
            BaseQuickAdapter<Postcommentslists.DataListBean.ItemListBean, BaseViewHolder> plhfAdapter = new BaseQuickAdapter<Postcommentslists.DataListBean.ItemListBean, BaseViewHolder>(R.layout.item_pinglunhuifu) {

                @Override
                protected void convert(BaseViewHolder helper, Postcommentslists.DataListBean.ItemListBean item) {
                    helper.setText(R.id.tv_neirong, item.fromUserName+"回复:" + item.content);
                }
            };
            plhfAdapter.setNewData(item.itemList);
            pinglunhuifu.setAdapter(plhfAdapter);
            //点击头像去评论人主页
            helper.getView(R.id.iv_touxiang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    app.writeInt("id", item.fromUserId);
                    readyGo(HomepageActivity.class);
                }
            });
            //点击内容回复评论
            helper.getView(R.id.tv_neirong).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fromUserName = item.fromUserName;
                    ccid = item.ccid;
                    id = item.id;
                    pid = item.pid;
                    //评论回复EditText
                    inputServer = new EditText(InvitationActivity.this);
                    inputServer.setHint("回复" + fromUserName + ":");
                    AlertDialog.Builder builder = new AlertDialog.Builder(InvitationActivity.this);
                    builder.setTitle("回复" + fromUserName + ":").setView(inputServer)
                            .setNegativeButton("取消", null);
                    builder.setPositiveButton("回复", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String content = inputServer.getText().toString();
                            if (TextUtils.isEmpty(content)) {
                                showToast("内容不能为空");
                                return;
                            }

                            //调用评论回复接口
                            Call<BaseResponse<JSON>> callhuifu = service.postcomments(App.token, ccid,
                                    content,
                                    1,
                                    id
                            );
                            callhuifu.enqueue(new BaseCallback<BaseResponse<JSON>>(callhuifu, InvitationActivity.this) {
                                @Override
                                public void onResponse(Response<BaseResponse<JSON>> response) {
                                    BaseResponse<JSON> body = response.body();
                                    if (body.isOK()) {
                                        //获取评论条数
                                        getZan();
                                        //回复成功重新刷新列表,移动列表至顶部最新回复的位置
                                        getCommentlist();
                                        svTiez.smoothScrollTo(0, 0);
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


    @OnClick({R.id.bt_fbpl, R.id.cb_zan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_fbpl://点击发布评论
                content = etPl.getText().toString();
                Call<BaseResponse<JSON>> callcomment = service.postcomments(App.token, postid,
                        content,
                        0,
                        null
                );
                callcomment.enqueue(new BaseCallback<BaseResponse<JSON>>(callcomment, InvitationActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            getZan();
                            //评论完清空edittext,重新获取评论列表
                            etPl.setText("");
                            getCommentlist();
                            svTiez.smoothScrollTo(0, 0);
                        } else {
                            showToast(body.message);
                        }
                    }
                });
                break;
            case R.id.cb_zan://点赞
                Call<BaseResponse<JSON>> callpostlike = service.postlike(App.token, postid);
                callpostlike.enqueue(new BaseCallback<BaseResponse<JSON>>(callpostlike, this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            //点完获取最新的点赞数
                            getZan();
                            getPostlikelists();
                        } else {
                            showToast(body.message);
                        }
                    }
                });
                break;
        }
    }
}
