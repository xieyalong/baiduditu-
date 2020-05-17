package xyl.baiduditu.api;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import xyl.baiduditu.BaseActivity;
import xyl.baiduditu.baidu.BaiDuKeyCheck;
import xyl.baiduditu.baidu.BaiDuLocation;

/**
 * 定位
 */
public class DingWeiActivity extends BaseActivity {
    MapView mMapView;
    BaiduMap mBaiduMap;
    BaiDuLocation baiDuLocation;
    BaiDuKeyCheck baiDuKeyCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
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
