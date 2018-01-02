package com.nxt.xpengb.common.widget

import android.view.Gravity
import android.content.Context
import android.graphics.PixelFormat
import android.view.WindowManager
import android.content.Intent
import android.provider.Settings
import android.view.View
import com.nxt.xpengb.R

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/23
 *   Version: V1.0
 *   Desc   :
 */
class NoNetWorkNotice constructor(context: Context) {
    private var windowManager: WindowManager? = null

    private var mView: View? = null
    private val params: WindowManager.LayoutParams
    var showing: Boolean = false

    init {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mView = View.inflate(context, R.layout.no_net_worke_layout, null)
        mView!!.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.applicationContext.startActivity(intent)
        }
        //设置LayoutParams(全局变量）相关参数
        params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
    }

    fun show() {
        showing = true
        windowManager!!.addView(mView, params)
    }

    fun cancel() {
        showing = false
        windowManager!!.removeViewImmediate(mView)
    }
}