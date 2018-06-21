package com.wlhb.hongbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.wlhb.administrator.hongbao.R;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.base.BaseActivity;
import com.wlhb.hongbao.bean.JsonBean;
import com.wlhb.hongbao.bean.OneKm;
import com.wlhb.hongbao.http.BaseCallback;
import com.wlhb.hongbao.http.BaseResponse;
import com.wlhb.hongbao.ui.fragment.HomeFragment;
import com.wlhb.hongbao.utils.JsonFileReader;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class RedPacketLocationActivity extends BaseActivity implements OnGetGeoCoderResultListener {
    @BindView(R.id.spin_city)
    TextView spinCity;
    @BindView(R.id.spin_fanwei)
    TextView spinFanwei;
    @BindView(R.id.map_hbwz)
    MapView mapHbwz;
    @BindView(R.id.rl_city)
    RelativeLayout rlCity;
    @BindView(R.id.rl_fanwei)
    RelativeLayout rlFanwei;
    @BindView(R.id.bt_dingwei)
    ImageView btDingwei;
    @BindView(R.id.bt_qvxiao)
    Button btQvxiao;
    @BindView(R.id.bt_queren)
    Button btQueren;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MapStatusUpdate msuLocationMapStatusUpdate;
    private MarkerOptions markerOptions;
    private BitmapDescriptor bitmapDescriptor;
    private LatLng latLngy;
    private View inflate;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private PopupWindow popupWindow;
    GeoCoder mSearch = null;
    private LatLng latLng;
    private float latitude;
    private float longitude;
    private String address;


    @Override
    public View loadView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_redpacketlocation, container, false);
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
        initJsonData();
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
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
        //        option.setScanSpan(60000);
        mLocClient.setLocOption(option);
        option.setIsNeedAddress(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.disableCache(false);// 禁止启用缓存定位
        inflate = View.inflate(this, R.layout.mark_hongbao, null);

        // OverlayDemo overlayDemo=new OverlayDemo();
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                latLngy = mapStatus.target;
                mBaiduMap.clear();
                markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(latLngy);
                mBaiduMap.addOverlay(markerOptions);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLngy));
            }
        });
    }

    @OnClick({R.id.rl_city, R.id.rl_fanwei, R.id.bt_dingwei,R.id.bt_qvxiao,R.id.bt_queren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_city:
                showPickerView();
                break;
            case R.id.bt_dingwei:
                mBaiduMap.animateMapStatus(msuLocationMapStatusUpdate);
                break;
            case R.id.rl_fanwei:
                bottomwindow(rlFanwei);
                break;
            case R.id.bt_qvxiao:
                Intent intentqv = new Intent();
                intentqv.putExtra("address", "");
                intentqv.putExtra("latitude", "");
                intentqv.putExtra("longitude", "");
                intentqv.putExtra("scope", "");
                setResult(RESULT_OK, intentqv);
                finish();
                break;
            case R.id.bt_queren:
                Call<BaseResponse<JSON>> callgetbygps = service.checkgps(App.token,longitude,latitude);
                callgetbygps.enqueue(new BaseCallback<BaseResponse<JSON>>(callgetbygps,RedPacketLocationActivity.this) {
                    @Override
                    public void onResponse(Response<BaseResponse<JSON>> response) {
                        BaseResponse<JSON> body = response.body();
                        if (body.isOK()) {
                            String scope = spinFanwei.getText().toString();
                            Intent intent = new Intent();
                            intent.putExtra("address", address);
                            intent.putExtra("latitude", latitude);
                            intent.putExtra("longitude", longitude);
                            intent.putExtra("scope", scope);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else {
                            showToast(body.message);
                        }
                    }
                });
                break;
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text =
                        options2Items.get(options1).get(options2) +
                                options3Items.get(options1).get(options2).get(options3);
                spinCity.setText(text);
                mSearch.geocode(new GeoCodeOption().city(options2Items.get(options1).get(options2))
                        .address(options3Items.get(options1).get(options2).get(options3)));
            }
        }).setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(18)
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setSubmitColor(Color.RED)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setOutSideCancelable(true)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    //地址转经纬度
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        mBaiduMap.clear();
        markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(geoCodeResult.getLocation());
        mBaiduMap.addOverlay(markerOptions);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(geoCodeResult.getLocation()));
        latitude = (float) geoCodeResult.getLocation().latitude;
        longitude = (float) geoCodeResult.getLocation().longitude;
    }

    //经纬度转地址
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        mBaiduMap.clear();
        /**
         * ReverseGeoCodeResult:反 Geo Code 结果
         * */
        mBaiduMap.addOverlay(markerOptions);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(reverseGeoCodeResult.getLocation()));
        address = reverseGeoCodeResult.getAddress();
        spinCity.setText(address.substring(address.indexOf("省") + 1));

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapHbwz == null) {
                return;
            }
            latitude = (float) location.getLatitude();
            longitude = (float) location.getLongitude();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(latitude)
                    .longitude(longitude).build();
            mBaiduMap.setMyLocationData(locData);
            latLng = new LatLng(latitude,
                    longitude);
            msuLocationMapStatusUpdate = MapStatusUpdateFactory
                    .newLatLng(latLng);
            mBaiduMap.animateMapStatus(msuLocationMapStatusUpdate);
            mBaiduMap.setMyLocationEnabled(false);

            bitmapDescriptor = BitmapDescriptorFactory.fromView(inflate);
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
        //释放该地理编码查询对象
        mSearch.destroy();
    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.pop_fanwei, null);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(layout);

    }

    private void setButtonListeners(LinearLayout layout) {
        final Button btlgl = (Button) layout.findViewById(R.id.bt_lgl);
        final Button btqs = (Button) layout.findViewById(R.id.bt_qs);

        btlgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    spinFanwei.setText(btlgl.getText());
                    popupWindow.dismiss();
                }
            }
        });
        btqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    spinFanwei.setText(btqs.getText());
                    popupWindow.dismiss();
                }
            }
        });
    }
}
