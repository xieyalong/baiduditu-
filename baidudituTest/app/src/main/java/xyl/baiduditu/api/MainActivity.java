package xyl.baiduditu.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.blankj.utilcode.util.Utils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import xyl.baiduditu.BaseActivity;
import xyl.baiduditu.baidu.BaiDuKeyCheck;
import xyl.baiduditu.baidu.BaiDuLocation;

public class MainActivity extends BaseActivity {
    MapView mMapView;
    BaiduMap mBaiduMap;
    BaiDuLocation baiDuLocation;
    BaiDuKeyCheck baiDuKeyCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            permission();
            mMapView=findViewById(R.id.bmapView);
            mBaiduMap=mMapView.getMap();
            baiDuLocation=new BaiDuLocation(this,mMapView, mBaiduMap);
            baiDuLocation.onCreate();
            BaiDuKeyCheck baiDuKeyCheck=new BaiDuKeyCheck(this);
            baiDuKeyCheck.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void start(View v){
        Intent intent=new Intent(this,LocationTypeDemo.class);
        startActivity(intent);
    }

    public  void permission(){
        AndPermission.with(this).permission(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,//读取外置存储
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写外置存储
                Manifest.permission.ACCESS_COARSE_LOCATION,//网络定位 解决定位在几内亚位置
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE//GPS定位 解决定位在几内亚位置
        ).requestCode(0).callback(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                System.out.println(">]回调成功");
            }
            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                System.out.println(">]回调失败");
            }
        }).start();
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
        baiDuLocation.onDestroy();
        baiDuKeyCheck.onDestroy();
    }
}
