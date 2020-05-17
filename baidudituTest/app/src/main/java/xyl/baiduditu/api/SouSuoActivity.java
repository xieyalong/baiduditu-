package xyl.baiduditu.api;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 覆盖物
 */
public class SouSuoActivity extends AppCompatActivity implements View.OnClickListener {
    //实例经纬度，LatLng(纬度, 经度)
    LatLng latitude1=new LatLng(40.050513, 116.30361);//黑马坐标
    LatLng  latitude2=new LatLng(40.065817, 116.349902);//传智坐标
    LatLng  latitude3=new LatLng(39.915112, 116.403963);//天安门
    MapView mMapView;
    BaiduMap mBaiduMap;
    Context mContext;
    View pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mContext=this;
            setContentView(R.layout.activity_sousuo);
            mMapView=findViewById(R.id.bmapView);
            mBaiduMap=mMapView.getMap();

//            findViewById(R.id.tv_yuanxing).setOnClickListener(this);
//            findViewById(R.id.tv_wenzi).setOnClickListener(this);
//            findViewById(R.id.tv_markey).setOnClickListener(this);
            initPoiSearch();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void initPoiSearch(){
        //搜索对象
        PoiSearch poiSearch=PoiSearch.newInstance();
        //绑定上搜索的范围和内容
        poiSearch.searchInBound(getSearchParams());
        //搜索结果监听
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            //获取兴趣点详情
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //如果结果码不等于没错误=有错误
                //在开发中应该根据PoiResult.ERRORNO 提示不同的信息
                if (poiResult==null&&poiResult.error!=PoiResult.ERRORNO.NO_ERROR){
                    ToastUtils.showShort("没有搜索到结果");
                    return;
                }
//                //如果搜搜出来，就添加覆盖物
//                LogUtils.i(JSON.toJSONString(poiResult));
//                //PoiOverlay 引用不到 可能是因为包不对
//                PoiOverlay poi=new PoiOverlay(mBaiduMap){
//                    //复写omPoiClick() 它表示你点击的是当前的打点
//                    @Override
//                    public boolean onPoiClick(int index){
//                        PoiInfo poiInfo=getPoiResult().getAllPoi().get(index);
//                        return true;
//                    }
//                };
//                mBaiduMap.setOnMarkerDragListener(poi);//设置每个点的弹出框
//                poi.setData(poiResult);//获取所有覆盖物
//                poi.addToMap();//把所有覆盖物设置到地图上
//                poi.zoomToSpan();//把所有结果都放到一个屏幕上去去
//

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
            //获取兴趣点详情信息
            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }
    //设置搜索范围和搜索内容
   public   PoiBoundSearchOption getSearchParams(){
        PoiBoundSearchOption params=new PoiBoundSearchOption();
        //西南（左下角）40.048459,116.302072
        //东北（右上角）40.0506675,116.304317
        LatLngBounds bounds=new LatLngBounds.Builder()
                //索索的范围，由左下角到右上角一个范围制定的正方形区域
                .include(new LatLng(40.048459,116.302072))
                .include(new LatLng(40.0506675,116.304317))
                .build();
        params.bound(bounds);//指定索索的范围
        params.keyword("加油站");//搜索的内容
        return params;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.tv_yuanxing:
//                yuanxing();
//                break;
//            case R.id.tv_wenzi:
//                wenzi();
//                break;
//            case R.id.tv_markey:
//                markey();
//                break;

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
