package xyl.baiduditu.api;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import xyl.baiduditu.BaseActivity;

public class HomeActivity extends ListActivity {
    ClassName[] datas={
            new ClassName(HolleActivity.class, "百度基础功能"),
            new ClassName(MainActivity.class, "定位"),
            new ClassName(FuGaiWuYuanXingActivity.class, "覆盖物"),
            new ClassName(SouSuoActivity.class, "搜索"),
            new ClassName(DingWeiActivity.class, "定位"),


    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        permission();
        ArrayAdapter<ClassName> adapter=
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ClassName cn = (ClassName) l.getItemAtPosition(position);
        startActivity(new Intent(this,cn.cls));
    }

    class ClassName{
        public  Class<?> cls;
        public  String name;

        public ClassName(Class<?> cls, String name) {
            this.cls = cls;
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return this.name;//item会显示那么
        }
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
                System.out.println(">]权限回调成功");
            }
            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                System.out.println(">]回调失败");
            }
        }).start();
    }
}
