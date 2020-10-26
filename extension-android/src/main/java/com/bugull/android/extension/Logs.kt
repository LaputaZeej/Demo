package com.bugull.android.extension

import android.util.Log
import com.bugull.android.extension.Logger.DEFAULT_TAG

object Logger {
    var DEBUG = true
     const val DEFAULT_TAG = "bugullx"

    fun i(msg: Any?, tag: String = DEFAULT_TAG) {
        if (!DEBUG) return
        if (tag.isBlank()) throw IllegalArgumentException("Logger TAG not blank")
        Log.i(tag, msg?.toString() ?: "Logger msg is null")
    }

    fun d(msg: Any?, tag: String = DEFAULT_TAG) {
        if (!DEBUG) return
        Log.d(tag, msg?.toString() ?: "Logger msg is null")
    }

    fun v(msg: Any?, tag: String = DEFAULT_TAG) {
        if (!DEBUG) return
        Log.v(tag, msg?.toString() ?: "Logger msg is null")
    }

    fun e(msg: Any?, tag: String = DEFAULT_TAG) {
        if (!DEBUG) return
        Log.e(tag, msg?.toString() ?: "Logger msg is null")
    }
}

fun logger(msg: Any?, tag: String = DEFAULT_TAG) {
    Logger.d(msg, tag)
}