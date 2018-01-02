package com.nxt.xpengb.common.base

import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Window
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.nxt.xpengb.R
import com.nxt.xpengb.common.receiver.NetWorkChangeBroadcastReceiver
import org.jetbrains.anko.find

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/23
 *   Version: V1.0
 *   Desc   :
 */
abstract class BaseActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    private var receiver: NetWorkChangeBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerNetWorkReceiver()
        setContentView(getLayoutId())
        initStatusBarColor()

        initView()
    }

    private fun initStatusBarColor() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.statusBarColor)
                .init()
    }

    protected fun initToolBar(title: CharSequence, back: Boolean) {
        val toolbar: Toolbar = find(R.id.toolbar)
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(back)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun registerNetWorkReceiver() {
        receiver = NetWorkChangeBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver, intentFilter)
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

//    fun BaseActivity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
//        Toast.makeText(this, message, duration).show()
//    }

    fun BaseActivity.showLoading() {
        if (dialog != null && dialog!!.isShowing) return
        dialog = ProgressDialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog!!.setMessage("请求网络中...")
        dialog!!.show()
    }

    fun BaseActivity.showLoading(message: CharSequence) {
        if (dialog != null && dialog!!.isShowing) return
        dialog = ProgressDialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog!!.setMessage(message)
        dialog!!.show()
    }

    private fun BaseActivity.dismissLoading() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.dismissLoading()
        receiver!!.onDestroy()
    }
}