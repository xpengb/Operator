package com.nxt.xpengb.operator.location;

import android.support.annotation.NonNull;
import com.amap.api.location.AMapLocation;
import rx.Subscriber;

public abstract class LocationSubscriber extends Subscriber<AMapLocation> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        onLocatedFail(null);
    }

    @Override
    public void onNext(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            onLocatedSuccess(aMapLocation);
        } else {
            onLocatedFail(aMapLocation);
        }
    }

    public abstract void onLocatedSuccess(@NonNull AMapLocation aMapLocation);

    public abstract void onLocatedFail(AMapLocation aMapLocation);

}
