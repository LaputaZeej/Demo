package com.bugull.android.extension

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

/**
 * 导致由于某些原因导致入口Activity重新onCreate()的bug
 * 比如：第一次安装/推送打开app/其他方式唤起app
 * 在finishAction里面结束当前activity并return
 */
inline fun Activity.checkTaskRootWhenDebug(finishAction: () -> Unit) {
    if (!isTaskRoot) {
        intent?.run {
            if (hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                finishAction()
            }
        }
    }
}

fun appLocale(context: Context): Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    context.resources.configuration.locales.get(0)
} else {
    context.resources.configuration.locale
}

/**
 * 状态栏高度
 */
val Context.statusBarHeight: Int
    get() = resources.getDimensionPixelSize(
        resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
    )

val Context.width: Int
    get() = resources.displayMetrics.widthPixels


val Context.activityManager: ActivityManager
    get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

fun dp2px(context: Context, value: Float): Int =
    (context.resources.displayMetrics.density * value + 0.5f).toInt()

fun px2dp(context: Context, value: Float): Int =
    (value * 1.0f / context.resources.displayMetrics.density * value + 0.5f).toInt()

/**
 * 判断当前Context是否运行在当前进程
 */
val Context.isRunningCurrentProcess: Boolean
    get() = this.run {
        val myPid = android.os.Process.myPid()
        activityManager.runningAppProcesses.forEach {
            return@run it.pid == myPid && this.packageName == it.processName
        }
        false
    }