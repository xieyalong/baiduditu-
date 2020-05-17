package com.baidu.location.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.baidulocationdemo.R;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.Calendar;
import java.util.Locale;

/**
 * 定位防作弊
 */
public class LocPreventCheatActivity extends Activity {

    private TextView tvResult;
    private Button btnStartLoc;
    private Button btnStopLoc;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_prevent_cheat);

        tvResult = findViewById(R.id.tv_result);
        btnStartLoc = findViewById(R.id.btn_start_loc);
        btnStopLoc = findViewById(R.id.btn_stop_loc);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000);
        option.setOpenGps(true); // 允许开启gps定位
        option.setEnableSimulateGps(false);
        option.setJudgeMockDisValue(500); // 设置gps虚拟位置点和真实位置点的距离误差，大于这个误差则代表返回的定位结果是mock数据，反之这不是，且仅当返回的定位结果是GPS定位结果
        option.setNeedRealLocWhenIsMock(true); // 当返回的定位结果是虚拟时，设置是否需要返回真实位置
        option.setCoorType("bd09ll");
        mLocationClient.setLocOption(option);

        btnStartLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.start();
            }
        });

        btnStopLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.stop();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {

            if (null == location) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvResult.setText(getResultString(location));
                }
            });
        }
    }

    private String getResultString(BDLocation location) {
        if (location != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("callback time: ");
            sb.append(getTimeStr());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());

            sb.append("\nisMock: ");
            sb.append(location.isMockGps());

            if (location.isMockGps() == BDLocation.MOCK_GPS_TYPE_TRUE
                    && null != location.getReallLocation()) {
                sb.append("\nrealLocType : ");
                sb.append(location.getReallLocation().getLocType());
                sb.append("\nrealLat : ");
                sb.append(location.getReallLocation().getLatitude());
                sb.append("\nrealLng : ");
                sb.append(location.getReallLocation().getLongitude());
            }

            return sb.toString();
        }
        return "";
    }


    private String getTimeStr() {
        int d, y, m, h, mi, s;
        Calendar cal = Calendar.getInstance();
        d = cal.get(Calendar.DATE);
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) + 1;
        h = cal.get(Calendar.HOUR_OF_DAY);
        mi = cal.get(Calendar.MINUTE);
        s = cal.get(Calendar.SECOND);
        return String.format(Locale.CHINA,"%d-%d-%d %d:%d:%d", y, m, d, h, mi, s);
    }
}
