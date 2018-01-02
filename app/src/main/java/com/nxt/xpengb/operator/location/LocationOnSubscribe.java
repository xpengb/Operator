package com.nxt.xpengb.operator.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import rx.Observable;
import rx.Subscriber;

public class LocationOnSubscribe implements Observable.OnSubscribe<AMapLocation> {

    private final Context context;

    public LocationOnSubscribe(Context context) {
        this.context = context;
    }

    @Override
    public void call(final Subscriber<? super AMapLocation> subscriber) {
        AMapLocationListener aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                subscriber.onNext(location);
                subscriber.onCompleted();
            }
        };
        LocationClient.get(context).locate(aMapLocationListener);
    }
}