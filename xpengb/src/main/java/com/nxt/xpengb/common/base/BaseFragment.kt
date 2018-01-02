package com.nxt.xpengb.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.nxt.xpengb.R
import org.jetbrains.anko.find

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/5
 *   Version: V1.0
 *   Desc   :
 */
abstract class BaseFragment: Fragment() {
    var mView: View? = null
    private var dialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        if(mView == null) {
            mView = inflater?.inflate(getLayoutId(), container, false)
            initView(mView, savedInstanceState)
//        }
        return mView
    }

    abstract fun getLayoutId(): Int

    abstract fun initView(view: View?, savedInstanceState: Bundle?)

    fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, message, duration).show()
    }

    fun Fragment.toast(message: CharSequence) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun BaseFragment.showLoading(context: Context) {
        if (dialog != null && dialog!!.isShowing) return
        dialog = ProgressDialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog!!.setMessage("请求网络中...")
        dialog!!.show()
    }

    fun BaseFragment.showLoading(context: Context, message: CharSequence) {
        if (dialog != null && dialog!!.isShowing) return
        dialog = ProgressDialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog!!.setMessage(message)
        dialog!!.show()
    }

    fun BaseFragment.dismissLoading() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}