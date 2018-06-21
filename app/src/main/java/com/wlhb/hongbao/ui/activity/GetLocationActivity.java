package com.wlhb.hongbao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/8/008.
 */

public class GetLocationActivity extends BaseActivity {
    @BindView(R.id.map_hbwz)
    MapView mapHbwz;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MapStatusUpdate msuLocationMapStatusUpdate;
    private BitmapDescriptor bitmapDescriptor;
    private MarkerOptions markerOptions;
    private View hbwz;
    private LatLng latLng;

    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_getlocation, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("红包位置");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initMap();
    }

    private void initMap() {
        mBaiduMap = mapHbwz.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        //地图上比例尺
        mapHbwz.showScaleControl(false);
        // 隐藏缩放控件
        mapHbwz.showZoomControls(false);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.disableCache(false);// 禁止启用缓存定位
        mLocClient.setLocOption(option);
        //设置红包MARK样式
        hbwz = View.inflate(this, R.layout.mark_hongbaowz, null);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner  implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapHbwz == null) {
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            latLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            msuLocationMapStatusUpdate = MapStatusUpdateFactory
                    .newLatLng(latLng);
            mBaiduMap.animateMapStatus(msuLocationMapStatusUpdate);
            mBaiduMap.setMyLocationEnabled(false);
            mBaiduMap.clear();

            //给地图添加MARK
            bitmapDescriptor = BitmapDescriptorFactory.fromView(hbwz);
            markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(latLng);
            mBaiduMap.addOverlay(markerOptions);



        }

        public void onReceivePoi(BDLocation poiLocation) {
            // initNavi();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLocClient.isStarted() == false) {
            mLocClient.start();// 开启定位
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapHbwz != null) {
            mapHbwz.onResume(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mBaiduMap.clear();
        if (mapHbwz != null) {
            mapHbwz.onPause(); // 使百度地图地图控件和Fragment的生命周期保持一致
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
        if (mapHbwz != null) {
            mapHbwz.onDestroy(); // 使百度地图地图控件和Fragment的生命周期保持一致
        }
    }
}
