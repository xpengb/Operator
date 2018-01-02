package com.nxt.xpengb.operator.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by liyu on 2016/11/2.
 */

public class LocationClient {

    private AMapLocationClient realClient;

    private static volatile LocationClient proxyClient;

    private LocationClient(Context context) {
        realClient = new AMapLocationClient(context);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setInterval(2000);
        //设置百度定位参数
        realClient.setLocationOption(option);
    }

    public static LocationClient get(Context context) {
        if (proxyClient == null) {
            synchronized (AMapLocationClient.class) {
                if (proxyClient == null) {
                    proxyClient = new LocationClient(context.getApplicationContext());
                }
            }
        }
        return proxyClient;
    }

    public void locate(final AMapLocationListener aMapLocationListener) {
        final AMapLocationListener realListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                //防止内存溢出
                aMapLocationListener.onLocationChanged(location);
                realClient.unRegisterLocationListener(this);
                stop();
            }
        };
        realClient.setLocationListener(realListener);
        if (!realClient.isStarted()) {
            realClient.startLocation();
        }
    }

    public AMapLocation getLastKnownLocation() {
        return realClient.getLastKnownLocation();
    }

    public void stop() {
        realClient.stopLocation();
    }
}
