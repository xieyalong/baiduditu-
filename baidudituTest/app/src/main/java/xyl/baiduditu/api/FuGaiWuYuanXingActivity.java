package xyl.baiduditu.api;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 覆盖物
 */
public class FuGaiWuYuanXingActivity extends AppCompatActivity implements View.OnClickListener {
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
            setContentView(R.layout.activity_fugaiwu_yuanxing);
            mMapView=findViewById(R.id.bmapView);
            mBaiduMap=mMapView.getMap();

            findViewById(R.id.tv_yuanxing).setOnClickListener(this);
            findViewById(R.id.tv_wenzi).setOnClickListener(this);
            findViewById(R.id.tv_markey).setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_yuanxing:
                yuanxing();
                break;
            case R.id.tv_wenzi:
                wenzi();
                break;
            case R.id.tv_markey:
                markey();
                break;

        }
    }
    //标志覆盖物
    public  void markey(){
        try {
            //覆盖物集合
            List<OverlayOptions> markerOptions=new ArrayList<>();
            //图标
            BitmapDescriptor icon=
                    BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);



            MarkerOptions options=new MarkerOptions();
            options.position(latitude1)//打点位置
                    .title("谢亚龙覆盖物")//title
                    .icon(icon)//图标
                    .draggable(true);//打点是否可以拖动
            markerOptions.add(options);

            MarkerOptions options2=new MarkerOptions();
            options2.position(new LatLng(latitude1.latitude+0.001, latitude1.longitude))//打点位置
                    .title("北边")//title
                    .icon(icon)//图标
                    //打点是否可以拖动， 如果能用手拖动了 那就设置mBaiduMap.setOnMarkerDragListener拖动监听器 不然pop弹出框不跟随动
                    .draggable(true);
            markerOptions.add(options2);

            MarkerOptions options3=new MarkerOptions();
            options3.position(new LatLng(latitude1.latitude, latitude1.longitude+0.001))//打点位置
                    .title("东边")//title
                    .icon(icon)//图标
                    .draggable(true);//打点是否可以拖动
            markerOptions.add(options3);

            MarkerOptions options4=new MarkerOptions();
            options4.position(new LatLng(latitude1.latitude-0.001, latitude1.longitude-0.001))//打点位置
                    .title("西南")//title
                    .icon(icon)//图标
                    .draggable(true);//打点是否可以拖动
            markerOptions.add(options4);

            //添加所有覆盖物
            mBaiduMap.addOverlays(markerOptions);


            //添加打点的点击事件弹出框
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    try {
                        //为了避免每次创建谈护框，只需要修改弹出框的经纬度即可
                        if (null==pop){//如果有就创建谈护框
                            pop=View.inflate(mContext, R.layout.view_markey, null);
    //                        MapViewLayoutParams.Builder builder=new MapViewLayoutParams.Builder();
    //                        builder.position(marker.getPosition());//设置pop的经纬度坐标
    //                        builder.yOffset(-35);//pop弹出框往上偏移25 不然会把打点的图标给遮盖住
    //                        builder.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);
    //                        MapViewLayoutParams params=builder.build();
                            mMapView.addView(pop,pop(marker));
                        }else{//如果有就改变弹出框的位置
    //                        MapViewLayoutParams.Builder builder=new MapViewLayoutParams.Builder();
    //                        builder.position(marker.getPosition());//设置pop的经纬度坐标
    //                        builder.yOffset(-35);//pop弹出框往上偏移25 不然会把打点的图标给遮盖住
    //                        builder.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);
    //                        MapViewLayoutParams params=builder.build();
                            mMapView.updateViewLayout(pop,pop(marker));
                        }
                        //设置弹出框页面数据
                        TextView tv = pop.findViewById(R.id.tv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                public  MapViewLayoutParams pop(Marker marker){
                    MapViewLayoutParams params= null;
                    try {
                        MapViewLayoutParams.Builder builder=new MapViewLayoutParams.Builder();
                        builder.position(marker.getPosition());//设置pop的经纬度坐标
                        builder.yOffset(-35);//pop弹出框往上偏移25 不然会把打点的图标给遮盖住
                        builder.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);
                        params = builder.build();
                        mMapView.updateViewLayout(pop,params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return  params;
                }
            });
            //拖动监听器，pop跟随打点的拖动而移动
            mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {

                //正在拖动
                @Override
                public void onMarkerDrag(Marker marker) {
                yidongPOP(marker);
                }
                //拖动结束
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    yidongPOP(marker);
                }
                //开始拖动
                @Override
                public void onMarkerDragStart(Marker marker) {
                    yidongPOP(marker);
                }
                public  void yidongPOP(Marker marker){
                    MapViewLayoutParams.Builder builder=new MapViewLayoutParams.Builder();
                    builder.position(marker.getPosition());//设置pop的经纬度坐标
                    builder.yOffset(-35);//pop弹出框往上偏移25 不然会把打点的图标给遮盖住
                    builder.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);
                    MapViewLayoutParams params=builder.build();
                    mMapView.updateViewLayout(pop,params);
                }
            });



            //设置默认中心点
            MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latitude1);
            mBaiduMap.setMapStatus(mapStatusUpdate);
            //设置默认方缩放比例
            mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
            mBaiduMap.setMapStatus(mapStatusUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void wenzi(){
        //位置
        TextOptions options=new TextOptions();
        options.position(latitude1)
                .text("谢亚龙")//文字内容
                .fontSize(20)//文字大小
                .fontColor(0XFF000000)//颜色 必须是8位的
                .bgColor(0XffFF0000);//背景颜色
//        .rotate(30);//旋转30度,文字旋转了30度
        mBaiduMap.addOverlay(options);

        //设置默认中心点
        MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latitude1);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        //设置默认方缩放比例
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    public void yuanxing(){

        CircleOptions options=new CircleOptions();
        options.center(latitude1)//坐标 圆心
                .radius(1000)//半径 1000 米
                .stroke(new Stroke(20,0x55F0000))//线条宽度颜色
                .fillColor(0x5500FF00);//填充颜色
        mBaiduMap.addOverlay(options);//添加一个覆盖物

        //设置默认中心点
        MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(latitude1);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        //设置默认方缩放比例
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
        mBaiduMap.setMapStatus(mapStatusUpdate);
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
