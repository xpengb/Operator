package com.nxt.xpengb.operator.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by liyu on 2016/11/2.
 */

public class RxLocation {
    private static RxLocation instance = new RxLocation();

    private RxLocation() {
    }

    public static RxLocation get() {
        return instance;
    }

    public Observable<AMapLocation> locate(final Activity context) {
        return new RxPermissions(context).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE).flatMap(new Func1<Boolean, Observable<AMapLocation>>() {
            @Override
            public Observable<AMapLocation> call(Boolean aBoolean) {
                return Observable.unsafeCreate(new LocationOnSubscribe(context));
            }
        });
    }

    public Observable<AMapLocation> locateLastKnown(Context context) {
        return Observable.unsafeCreate(new LocationLastKnownOnSubscribe(context));
    }

}
