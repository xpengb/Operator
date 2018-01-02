package com.nxt.xpengb.operator.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.lzy.okgo.OkGo
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.operator.app.Constant
import com.squareup.leakcanary.LeakCanary

/**
 *   @author: xpengb@outlook.com
 *   @date: 2017/11/14
 *   @version: v1.0
 *   @desc:
 */
class OperatorApp : Application() {
//    private lateinit var daoMaster: DaoMaster
//    private lateinit var daoSession: DaoSession

    init {
        instance = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
//        lateinit var instance: App
        var instance: OperatorApp? = null
            private set

        var location: AMapLocation? = null
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        OkGo.init(this)
        SharePrefHelper.getInstance(this)
        initLocation()
    }

    /**
     * @return
     */
//    private fun getDaoMaster(): DaoMaster {
//        val helper = DaoMaster.DevOpenHelper(this, Constant.DB_NAME)
//        daoMaster = DaoMaster(helper.writableDb)
//        return daoMaster
//    }

    /**
     * @return
     */
//    fun getDaoSession(): DaoSession {
//        daoMaster = getDaoMaster()
//        daoSession = daoMaster.newSession()
//        return daoSession
//    }

    private fun initLocation() {
        val locationOption = AMapLocationClientOption()
        locationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        locationOption.interval = 2000
        val locationClient = AMapLocationClient(context)
        locationClient.setLocationOption(locationOption)
        locationClient.startLocation()
        locationClient.setLocationListener { aMapLocation ->
            if (aMapLocation != null) {
                if (aMapLocation.errorCode == 0) {
                    location = aMapLocation
                }
            }
        }
    }
}