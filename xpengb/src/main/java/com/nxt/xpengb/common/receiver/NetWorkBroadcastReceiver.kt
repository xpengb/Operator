package com.nxt.xpengb.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nxt.xpengb.common.widget.NoNetWorkNotice

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/23
 *   Version: V1.0
 *   Desc   :
 */
class NetWorkChangeBroadcastReceiver : BroadcastReceiver {

    var context: Context? = null
    var noNetWorkNotice: NoNetWorkNotice? = null

    constructor(context: Context) {
        this.context = context
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (noNetWorkNotice == null)
            noNetWorkNotice = NoNetWorkNotice(context)
        val networkInfos = connectivityManager.allNetworkInfo
        for (networkInfo in networkInfos) {
            val state = networkInfo.state
            if (NetworkInfo.State.CONNECTED == state) {
                if (null != noNetWorkNotice && noNetWorkNotice!!.showing) {
                    noNetWorkNotice!!.cancel()
                }
                return
            }
        }

        if (null != noNetWorkNotice && !noNetWorkNotice!!.showing) {
            noNetWorkNotice!!.show()
        }
    }

    fun onDestroy() {
        if (null != noNetWorkNotice) {
            if (noNetWorkNotice!!.showing) {
                noNetWorkNotice!!.cancel()
            }
        }
    }
}
