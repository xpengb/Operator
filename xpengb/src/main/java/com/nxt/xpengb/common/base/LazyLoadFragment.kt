package com.nxt.xpengb.common.base

import android.os.Bundle
import android.view.View
import com.nxt.xpengb.base.BaseFragment

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/28
 *   Version: V1.0
 *   Desc   :
 */
abstract class LazyLoadFragment: BaseFragment() {

    private var loaded = false
    private var created = false
    private var visible  = false
    private var view1: View? = null

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        this.view1 = view
        created = true
        lazyLoad(view, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.visible = isVisibleToUser
        lazyLoad(view1, null)
    }

    fun lazyLoad(view: View?, savedInstanceState: Bundle?) {
        if (!visible and loaded and !created) {
            return
        }
        lazyInit(view, savedInstanceState)
        loaded = true
    }

    abstract fun lazyInit(view: View?, savedInstanceState: Bundle?)

    override fun getLayoutId(): Int {
        return getLazyLayoutId()
    }

    abstract fun getLazyLayoutId(): Int

    override fun onDestroyView() {
        super.onDestroyView()
        created = false
        loaded = false
    }
}