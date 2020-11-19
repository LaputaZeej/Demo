package com.laputa.base.util

import android.util.Log

object L {
    @JvmStatic
    fun e(tag: String = TAG, msg: String) {

        Log.e(tag, msg)
    }

    @JvmStatic
    fun i(tag: String = TAG, msg: String) {

        Log.i(tag, msg)
    }

    private const val TAG = "[base]"
}