package com.nxt.xpengb.operator.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.operator.app.AtyContainer
import com.nxt.xpengb.operator.R
import com.nxt.xpengb.operator.ui.adapter.ViewPagerAdapter
import com.nxt.xpengb.operator.util.ZoomOutPageTransformer
import com.nxt.xpengb.operator.ui.LoginActivity
import org.jetbrains.anko.find
import java.util.ArrayList

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/30
 *   Version: V1.0
 *   Desc   :
 */
class GuideActivity : Activity(), ViewPager.OnPageChangeListener {

    private var vp: ViewPager? = null
    private var vpAdapter: ViewPagerAdapter? = null
    private var views: MutableList<View>? = null
    private var dots: Array<ImageView>? = null
    private val ids = intArrayOf(R.id.dot1, R.id.dot2, R.id.dot3)
    private var btn: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AtyContainer.addActivity(this)
        setContentView(R.layout.activity_guide)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }
        initViews()
        initDots()
    }

    //初始化方法
    private fun initViews() {
        val inflater = LayoutInflater.from(this)

        //将view集合实例化
        views = ArrayList<View>()
        views!!.add(inflater.inflate(R.layout.view_guide_one, null))
        views!!.add(inflater.inflate(R.layout.view_guide_two, null))
        views!!.add(inflater.inflate(R.layout.view_guide_three, null))

        //实例化adapter
        vpAdapter = ViewPagerAdapter(views, this)
        //找到对象
        vp = find(R.id.viewpager)
        vp!!.setPageTransformer(true, ZoomOutPageTransformer())
        vp!!.adapter = vpAdapter

        btn = views!![2].find(R.id.btn_start)
        btn!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.welcome_btn_enter))
        btn!!.setOnClickListener {
            SharePrefHelper.put("first", false)

            val i = Intent(this@GuideActivity, LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit)
            finish()
        }
        vp!!.setOnPageChangeListener(this)
    }

    //对点的操作方法
    private fun initDots() {
        dots = arrayOfNulls<ImageView>(views!!.size) as Array<ImageView>
        for (i in views!!.indices) {
            dots!![i] = find(ids[i])
        }
    }

    //当页面滑动时调用
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }


    //当前新的页面被选中的时候调用
    override fun onPageSelected(position: Int) {
        for (i in ids.indices) {
            if (position == i) {
                dots!![i].setImageResource(R.mipmap.icon_point_selected)
            } else {
                dots!![i].setImageResource(R.mipmap.icon_point_normal)
            }
        }
    }

    //当滑动状态改变时调用
    override fun onPageScrollStateChanged(state: Int) {

    }

    public override fun onDestroy() {
        super.onDestroy()
        AtyContainer.removeActivity(this)
    }
}
