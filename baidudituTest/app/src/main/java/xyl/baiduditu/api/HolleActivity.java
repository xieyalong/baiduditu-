package xyl.baiduditu.api;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.LogUtils;

import xyl.baiduditu.BaseActivity;
import xyl.baiduditu.baidu.BaiDuKeyCheck;
import xyl.baiduditu.baidu.BaiDuLocation;

/**
 * 最基础功能
 */
public class HolleActivity extends BaseActivity {
    MapView mMapView;
    BaiduMap mBaiduMap;
    //实例经纬度，LatLng(纬度, 经度)
    LatLng  latitude1=new LatLng(40.050513, 116.30361);//黑马坐标
    LatLng  latitude2=new LatLng(40.065817, 116.349902);//传智坐标
    LatLng  latitude3=new LatLng(39.915112, 116.403963);//天安门坐标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_holle);
            findViewById(R.id.tv_fy).setOnClickListener(this);
            findViewById(R.id.tv_yd).setOnClickListener(this);
            findViewById(R.id.tv_xz).setOnClickListener(this);
            findViewById(R.id.tv_sx).setOnClickListener(this);
            findViewById(R.id.tv_fd).setOnClickListener(this);

            mMapView=findViewById(R.id.bmapView);
            //地图控制器
            mBaiduMap=mMapView.getMap();

            //1，是否显示加减的缩放按钮
            mMapView.showZoomControls(true);
            //2,是否显示比例尺
            mMapView.showScaleControl(true);
            //3,获取地图最大缩放级别
            float maxZoom=mBaiduMap.getMaxZoomLevel();
            //获取地图最小缩放级别
            float minZoom=mBaiduMap.getMinZoomLevel();
            //输出maxZoom=21.0---minZoom=4.0
            LogUtils.i(">]maxZoom="+maxZoom+"---minZoom="+minZoom);

            //MapStatusUpdate里有很多常用的方法,你给它什么状态，他就设置什么状态，
            //主要涉及的类MapStatusUpdate，MapStatusUpdateFactory，MapStatus
            //如设置设置屏幕中的地理范围,中心点以及缩放级别等
            //4.1，设置地图的中心点
            MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latitude1);
            mBaiduMap.setMapStatus(mapStatusUpdate);
            //4.2设置初始化缩放的大小
            //zoomBy(10)=20增量的关系，zoomTo(10)=10设置什么就显示什么，
            mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
            mBaiduMap.setMapStatus(mapStatusUpdate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.tv_fy:
                fuYang();
                break;
            case  R.id.tv_yd:
                yiDong();
                break;
            case  R.id.tv_xz:
                xuanZhuan();
                break;
            case  R.id.tv_sx:
                suoXiao();
                break;
            case  R.id.tv_fd:
                fangDa();
                break;

        }
    }
    //缩小 两手指往里缩小
    public  void suoXiao(){
        MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.zoomOut();
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }
    //放大 两手指往里放大
    public  void fangDa(){
        MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.zoomIn();
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    //旋转,在原来的基础上在旋转30度
    // 两个手指按住旋转，左上角会出现指南针
    public  void xuanZhuan(){
        UiSettings uiSettings=mBaiduMap.getUiSettings();
        //是否显示指南针
        uiSettings.setCompassEnabled(true);
        //获取当前的旋转度
        float rotate=mBaiduMap.getMapStatus().rotate;
        //在原来的旋转基础+30
        MapStatus mapStatus=new MapStatus.Builder().rotate(rotate+30).build();
        MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }
    //俯仰 在原来的基础上在俯仰-5度，只有负数，目前还不知道怎么用手指操作
    //两个手指 上下滑动
    public  void fuYang(){
        try {
            LogUtils.i(">]fuYang");
            float overlook=mBaiduMap.getMapStatus().overlook;
            MapStatus mapStatus=new MapStatus.Builder().overlook(overlook-5).build();
            MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.newMapStatus(mapStatus);
            mBaiduMap.setMapStatus(mapStatusUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //移动，移动就是设置不同经纬度+动画
    public  void yiDong(){
        try {
            LogUtils.i(">]yiDong");
            //直接设置经纬度，看不到动画的效果，需要设置动画的时间
            MapStatusUpdate  mapStatusUpdate=MapStatusUpdateFactory.newLatLng(latitude3);
            // 设置动画,2秒钟完成
            mBaiduMap.animateMapStatus(mapStatusUpdate,2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
    }
}
