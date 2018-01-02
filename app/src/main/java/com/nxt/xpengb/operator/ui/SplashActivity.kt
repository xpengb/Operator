package com.nxt.xpengb.operator.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.operator.app.AtyContainer
import com.nxt.xpengb.operator.R
import com.nxt.xpengb.operator.ui.LoginActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/30
 *   Version: V1.0
 *   Desc   :
 */
class SplashActivity : Activity() {
    private val ANIMATION_TIME = 2000
    private val SCALE_END = 1.13f
    private var first = true
    private var logined = false
    private var handler = Handler()
    private var thread = SplashThread()
    private var splashIv: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AtyContainer.addActivity(this)
        first = SharePrefHelper.getBoolean("first", true)
        logined = SharePrefHelper.getBoolean("log_in", false)
        if (first) {
            startActivity<GuideActivity>()
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        splashIv = find<ImageView>(R.id.iv_splash)

        handler.postDelayed(thread, 1000)
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun startAnim() {
        val animatorX = ObjectAnimator.ofFloat(splashIv, "scaleX", 1f, SCALE_END)
        val animatorY = ObjectAnimator.ofFloat(splashIv, "scaleY", 1f, SCALE_END)

        val set = AnimatorSet()
        set.setDuration(ANIMATION_TIME.toLong()).play(animatorX).with(animatorY)
        set.start()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if(logined) {
                    startActivity<MainActivity>()
                }else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(thread)
    }

    override fun onDestroy() {
        super.onDestroy()
        AtyContainer.removeActivity(this)
    }

    private inner class SplashThread: Runnable {
        override fun run() {
            startAnim()
        }
    }
}