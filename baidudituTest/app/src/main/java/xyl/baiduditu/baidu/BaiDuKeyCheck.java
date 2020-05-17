package xyl.baiduditu.baidu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.ToastUtils;

import xyl.baiduditu.ActivityLifecycleAbstract;
import xyl.baiduditu.BaseActivity;

public class BaiDuKeyCheck extends ActivityLifecycleAbstract {
    BaseActivity mActivity;
    BroadcastReceiver br;
    public BaiDuKeyCheck(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerSDKCheckReceiver();
    }

    /**
     * 注册广播
     */
    public  void registerSDKCheckReceiver(){
         br=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                //网略错误
                if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)){
//                   mActivity.showToast("网略错误");
                    ToastUtils.showShort("网略错误");
                }else if(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
//                    mActivity.showToast("key错误");
                    ToastUtils.showShort("key错误");
                }
            }
        };
        IntentFilter filter=new IntentFilter();
        //网略监听
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        //监听key是否正确
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        mActivity.registerReceiver(br, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消广播
        mActivity.unregisterReceiver(br);
    }
}
