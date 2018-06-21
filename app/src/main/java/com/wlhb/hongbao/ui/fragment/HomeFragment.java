package com.wlhb.hongbao.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseFragment;
import com.wlhb.hongbao.bean.BroadcastData;
import com.wlhb.hongbao.bean.OneKm;
import com.wlhb.hongbao.bean.Receive;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.activity.AdvertisingActivity;
import com.wlhb.hongbao.ui.activity.BindingActivity;
import com.wlhb.hongbao.ui.activity.HelpCenterActivity;
import com.wlhb.hongbao.ui.activity.MissionActivity;
import com.wlhb.hongbao.ui.activity.RedPacketParticularsActivity;
import com.wlhb.hongbao.ui.activity.SystemNoticeActivity;
import com.wlhb.hongbao.ui.activity.TypeActivity;
import com.wlhb.hongbao.ui.view.MarqueeTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/3/25/025.
 */

public class HomeFragment extends BaseFragment implements BaiduMap.OnMapStatusChangeListener {


    @BindView(R.id.ll_notificationbar)
    RelativeLayout llNotificationbar;
    @BindView(R.id.bt_renwu)
    ImageView btRenwu;
    @BindView(R.id.bt_leixing)
    ImageView btLeixing;
    @BindView(R.id.bt_dingwei)
    ImageView btDingwei;
    @Nullable
    @BindView(R.id.top_menu_left_back)
    ImageView topMenuLeft;
    @BindView(R.id.top_menu_title)
    TextView topMenuTitle;
    @BindView(R.id.right_menu_title)
    TextView rightMenuTitle;
    @BindView(R.id.tv_ggbt)
    MarqueeTextView tvGgbt;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private OverlayOptions ooCircle;
    private MapStatusUpdate msuLocationMapStatusUpdate;
    private MarkerOptions markerOptionshb;
    private Marker hbMarker;
    boolean isFirstLoc = true;// 是否首次定位
    private LatLng latLng;
    private List<String> textArrays = new ArrayList<>();
    List<BroadcastData.DataListBean> dataListBeans = new ArrayList<>();
    List<OneKm> oneKms = new ArrayList<>();
    private float latitude;
    private float longitude;
    private MapStatusUpdate mMapStatusUpdate;
    private String phonemsg;
    private BitmapDescriptor bitmapDescriptorhb;
    private String fjprovince;
    private String fjcity;
    private String fjdistrict;

    //地图缩放级别
    private float zoomLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, inflate);
        mMapView = inflate.findViewById(R.id.bmapView);
        initTitle();
        initMap();
        return inflate;

    }

    private void initTitle() {
        topMenuLeft.setVisibility(View.GONE);
        topMenuTitle.setText("小圆");
        rightMenuTitle.setText("帮助中心");
        rightMenuTitle.setVisibility(View.VISIBLE);
    }

    private void initMap() {
        phonemsg = app.readString("phonemsg", "");
        if (phonemsg.equals("手机号暂未绑定，请使用微信登录并绑定手机号")) {
            TextView textView = new TextView(getActivity());
            textView.setText("提现需要绑定手机号,是否立即前往绑定手机号?");
            textView.setPadding(30, 30, 0, 0);
            textView.setTextSize(16);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("绑定手机号").setView(textView)
                    .setNegativeButton("暂不绑定", null);
            builder.setPositiveButton("立即绑定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(), BindingActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();
        }
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //        option.setScanSpan(60000);
        option.setIsNeedAddress(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.disableCache(false);// 禁止启用缓存定位
        // OverlayDemo overlayDemo=new OverlayDemo();
        mLocClient.setLocOption(option);
        mLocClient.start();
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        zoomLevel = 15f;
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(zoomLevel)
                .build();
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.setOnMapStatusChangeListener(this);
        //获取系统公告
        Call<BaseResponse<BroadcastData>> call = service.broadcastdata(App.token);
        call.enqueue(new BaseCallback<BaseResponse<BroadcastData>>(call, this) {
            @Override
            public void onResponse(Response<BaseResponse<BroadcastData>> response) {
                BaseResponse<BroadcastData> body = response.body();
                if (body.isOK()) {
                    dataListBeans.addAll(body.data.dataList);
                    for (int i = 0; i < dataListBeans.size(); i++) {
                        textArrays.add(dataListBeans.get(i).title);
                    }
                    tvGgbt.setTextArrays(textArrays);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tvGgbt.releaseResources();
    }

    @OnClick({R.id.ll_notificationbar, R.id.bt_renwu, R.id.bt_leixing, R.id.bt_dingwei, R.id.right_menu_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_notificationbar://点击去系统公告
                readyGo(SystemNoticeActivity.class);
                break;
            case R.id.bt_renwu://点击去任务
                readyGo(MissionActivity.class);
                break;
            case R.id.bt_leixing://点击去类型
                readyGo(TypeActivity.class);
                break;
            case R.id.bt_dingwei://点击定位
                mBaiduMap.animateMapStatus(msuLocationMapStatusUpdate);
                break;
            case R.id.right_menu_title://点击去帮助中心
                readyGo(HelpCenterActivity.class);
                break;
        }
    }

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        //当地图状态改变的时候，获取放大级别
        zoomLevel = mapStatus.zoom;
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            latitude = (float) location.getLatitude();
            longitude = (float) location.getLongitude();


            String addrStr = location.getAddrStr();
            fjprovince = addrStr.substring(2, addrStr.indexOf("省") + 1);
            fjcity = addrStr.substring(addrStr.indexOf("省") + 1, addrStr.indexOf("市") + 1);
            fjdistrict = addrStr.substring(addrStr.indexOf("市") + 1);
            String city = location.getCity();
            //记录当前地址及坐标,方便后续使用
            app.writeString("city", city);
            app.writeString("fjprovince", fjprovince);
            app.writeString("fjcity", fjcity);
            app.writeString("fjdistrict", fjdistrict);
            app.writeFloat("latitude", latitude);
            app.writeFloat("longitude", longitude);
            //首次定位
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(latitude,
                        longitude);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(latitude)
                    .longitude(longitude).build();
            mBaiduMap.setMyLocationData(locData);
            latLng = new LatLng(latitude,
                    longitude);

            //设置定位坐标,屏幕缩放大小zoom数值越小屏幕缩放越大,
            MapStatus msuMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(zoomLevel)
                    .build();
            msuLocationMapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(msuMapStatus);
            mBaiduMap.animateMapStatus(msuLocationMapStatusUpdate);

            mBaiduMap.setMyLocationEnabled(false);
            //绘制圆圈和定位点
            ooCircle = new CircleOptions().fillColor(0x184d73b3)
                    .center(latLng).stroke(new Stroke(1, 0x384d73b3))
                    .radius(2000);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.dian);
            MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(latLng);
            mBaiduMap.addOverlay(ooCircle);
            mBaiduMap.addOverlay(markerOptions);
            getPacketMark();
        }
    }

    //获取地图红包
    private void getPacketMark() {
        Call<BaseResponse<List<OneKm>>> callgetbygps = service.getbygps(App.token, longitude, latitude);
        callgetbygps.enqueue(new BaseCallback<BaseResponse<List<OneKm>>>(callgetbygps, HomeFragment.this) {
            @Override
            public void onResponse(Response<BaseResponse<List<OneKm>>> response) {
                BaseResponse<List<OneKm>> body = response.body();
                if (body.isOK()) {
                    oneKms.addAll(body.data);
                    for (int i = 0; i < oneKms.size(); i++) {
                        LatLng HblatLng = new LatLng(oneKms.get(i).lat,
                                oneKms.get(i).lng);
                        //设置给红包绑定区分ID或者其他数据
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("packetId", oneKms.get(i).id);
                        //判断红包是否已领取,未领取的进广告,已领取的直接去详情
                        if (oneKms.get(i).isReceive == 1) {
                            BitmapDescriptor bitmapDescriptorhb = BitmapDescriptorFactory.fromResource(R.drawable.hongdian);
                            //给红包MARK设置图标,坐标和绑定红包ID
                            markerOptionshb = new MarkerOptions().icon(bitmapDescriptorhb).position(HblatLng).extraInfo(mBundle);
                            hbMarker = (Marker) mBaiduMap.addOverlay(markerOptionshb);
                            final String idylq = hbMarker.getId();
                            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    if (marker.getId() == idylq) {
                                        //取出绑定的ID进入红包详情
                                        Bundle bundle = marker.getExtraInfo();
                                        int packetId = bundle.getInt("packetId");
                                        app.writeInt("packetId", packetId);
                                        readyGo(RedPacketParticularsActivity.class);
                                    }
                                    return false;
                                }
                            });
                        } else {
                            //如果是自己发的换个红包颜色
                            if (App.id == oneKms.get(i).userId) {
                                bitmapDescriptorhb = BitmapDescriptorFactory.fromResource(R.drawable.ditu_hongbaomy);
                            } else {
                                bitmapDescriptorhb = BitmapDescriptorFactory.fromResource(R.drawable.ditu_hongbao);
                            }
                            markerOptionshb = new MarkerOptions().icon(bitmapDescriptorhb).position(HblatLng).extraInfo(mBundle);
                            hbMarker = (Marker) mBaiduMap.addOverlay(markerOptionshb);
                            final String id = hbMarker.getId();
                            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(final Marker marker) {
                                    if (marker.getId() == id) {
                                        Bundle bundle = marker.getExtraInfo();
                                        int packetId = bundle.getInt("packetId");
                                        app.writeInt("packetId", packetId);
                                        Call<BaseResponse<Receive>> call = service.receive(App.token, packetId);
                                        call.enqueue(new BaseCallback<BaseResponse<Receive>>(call, HomeFragment.this) {
                                            @Override
                                            public void onResponse(Response<BaseResponse<Receive>> response) {
                                                BaseResponse<Receive> body = response.body();
                                                if (body.isOK()) {
                                                    readyGo(AdvertisingActivity.class);
                                                } else {
                                                    showToast(body.message);
                                                    //取出绑定的ID进入红包详情
                                                    Bundle bundle = marker.getExtraInfo();
                                                    int packetId = bundle.getInt("packetId");
                                                    app.writeInt("packetId", packetId);
                                                    readyGo(RedPacketParticularsActivity.class);
                                                }
                                            }
                                        });
                                    }
                                    return false;
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mLocClient.isStarted()) {
            mLocClient.start();// 开启定位
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }
        getPacketMark();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBaiduMap.clear();
        if (mMapView != null) {
            mMapView.onPause(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mBaiduMap.clear();
        mLocClient.stop();// 停止定位
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocClient.stop();
        if (mMapView != null) {
            mMapView.onDestroy(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }
    }
}

