package com.bugull.android.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Activity.gotoSetting() {
    startActivity(Intent().apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}

fun Context.reStartApp() {
    packageManager.getLaunchIntentForPackage(packageName)?.run {
        startActivity(this.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }
}