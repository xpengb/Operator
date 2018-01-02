package com.nxt.xpengb.operator.ui

import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.amap.api.location.AMapLocation
import com.nxt.xpengb.common.base.BaseActivity
import com.nxt.xpengb.common.utils.ACache
import com.nxt.xpengb.common.utils.SizeUtils
import com.nxt.xpengb.common.utils.TimeUtils
import com.nxt.xpengb.operator.R
import com.nxt.xpengb.operator.http.ApiFactory
import com.nxt.xpengb.operator.http.BaseWeatherResponse
import com.nxt.xpengb.operator.location.RxLocation
import com.nxt.xpengb.operator.model.HeWeather
import com.nxt.xpengb.operator.ui.adapter.DividerGridItemDecoration
import com.nxt.xpengb.operator.ui.adapter.SuggestionAdapter
import com.nxt.xpengb.operator.util.WeatherUtil
import com.nxt.xpengb.operator.widget.timelineview.WeatherChartView
import kotlinx.android.synthetic.main.activity_weather.*
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 *   @author: xpengb@outlook.com
 *   @date: 2017/11/23
 *   @version: v1.0
 *   @desc:
 */
class WeatherActivity : BaseActivity() {
    private val CACHE_WEAHTHER_NAME = "weather_cache"
    private lateinit var subscription: Subscription

    override fun getLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun initView() {
        rvSuggestion.layoutManager = GridLayoutManager(this, 4)
        rvSuggestion.addItemDecoration(DividerGridItemDecoration(this))

        subscription = Observable
                .concat(getLocalCache(), getFromNetWork())
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HeWeather> {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        Snackbar.make(weather_view, "获取天气失败!", Snackbar.LENGTH_LONG)
                                .setAction("重试", {
                                    initView()
                                })
                                .setActionTextColor(resources.getColor(R.color.actionColor))
                                .show()
                    }

                    override fun onNext(t: HeWeather?) {
                        showWeather(t!!)
                    }
                })

    }

    private fun showWeather(weather: HeWeather) {
        tv_city_name.text = weather.basic.city
        tv_weather_string.text = weather.now.cond.txt
        tv_weather_aqi.text = weather.aqi.city.qlty
        tv_temp.text = String.format("%s℃", weather.now.tmp)
        val updateTime = TimeUtils.string2String(weather.basic.update.loc,
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()),
                SimpleDateFormat("HH:mm", Locale.getDefault()))
        tv_update_time.text = String.format("截止 %s", updateTime)

        contentLayout.setPadding(0, SizeUtils.dp2px(this, 16F), 0, SizeUtils.dp2px(this, 16F))
        contentLayout.removeAllViews()
        contentLayout.addView(getChartView(weather))

        val suggestion = arrayListOf<Any>(
                weather.suggestion.air,
                weather.suggestion.comf,
                weather.suggestion.cw,
                weather.suggestion.drsg,
                weather.suggestion.flu,
                weather.suggestion.sport,
                weather.suggestion.trav,
                weather.suggestion.uv)
        suggestion.filter {
            false
        }.map {
            suggestion.remove(it)
        }

        rvSuggestion.adapter = SuggestionAdapter(R.layout.item_suggestion, suggestion)
    }

    private fun getChartView(weather: HeWeather): WeatherChartView {
        val chartView = WeatherChartView(this)
        chartView.setWeather(weather)
        return chartView
    }

    private fun getLocalCache(): Observable<HeWeather> = Observable.unsafeCreate { subscriber ->
        val cacheWeather = ACache.get(this).getAsObject(CACHE_WEAHTHER_NAME) as HeWeather
        if (cacheWeather == null) {
            subscriber.onCompleted()
        } else {
            subscriber.onNext(cacheWeather)
        }
    }

    private fun getFromNetWork(): Observable<HeWeather>
            = RxLocation.get().locate(this)
            .flatMap { aMapLocation ->
                val city = if (TextUtils.isEmpty(aMapLocation.city)) "北京" else aMapLocation.city.replace("市", "")
                tv_city_name.text = city
                WeatherUtil.getInstance().weatherKey.flatMap { key ->
                    ApiFactory
                            .getWeatherController()
                            .getWeather(key, city)
                            .subscribeOn(Schedulers.io())
                }
            }
            .map { response ->
                val heWeather5 = response.heWeather[0]
                ACache.get(this).put(CACHE_WEAHTHER_NAME, heWeather5, 30 * 60)
                WeatherUtil.getInstance().saveDailyHistory(heWeather5)
                heWeather5
            }


    override fun onDestroy() {
        super.onDestroy()
        if (subscription != null && !subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }
}