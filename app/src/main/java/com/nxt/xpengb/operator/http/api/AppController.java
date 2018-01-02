package com.nxt.xpengb.operator.http.api;

import com.nxt.xpengb.operator.http.BaseAppResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liyu on 2016/12/1.
 */

public interface AppController {

//    @GET("http://api.caoliyu.cn/appupdate.json")
//    Observable<BaseAppResponse<UpdateInfo>> checkUpdate();

    @GET("http://api.caoliyu.cn/weatherkey.json")
    Observable<BaseAppResponse<String>> getWeatherKey();
}
