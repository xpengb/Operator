package com.nxt.xpengb.operator.app

import android.app.Activity

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/30
 *   Version: V1.0
 *   Desc   :
 */
object AtyContainer {

    private var activityStack = arrayListOf<Activity>()

    fun addActivity(activity: Activity) = activityStack.add(activity)

    fun removeActivity(activity: Activity) = activityStack.remove(activity)

    fun removeAllActivity() = activityStack.removeAll(activityStack)

    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }
}