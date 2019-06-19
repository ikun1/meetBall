package com.example.user.templatedemo.Tools;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.user.templatedemo.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationTools implements AMapLocationListener {
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public AMapLocation amapLocation;
    private  ReactEvent  myReactEvent;

    public LocationTools(ReactEvent reactEvent){
        myReactEvent = reactEvent;
        initLocation();
    }

    public void stopLocation(){
        mlocationClient.onDestroy();
    }

    public void initLocation(){

        mlocationClient = new AMapLocationClient(MainActivity.getInstance());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(60000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

    }


    @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            myReactEvent.location_change(amapLocation);
    }

    public interface ReactEvent{
        void location_change(AMapLocation amapLocation);
    }
}
