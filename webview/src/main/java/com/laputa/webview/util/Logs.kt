package com.laputa.webview.util

import com.laputa.base.util.L

fun i(msg: Any?) {
    if (Constant.DEBUG) {
        L.i(Constant.TAG, msg?.toString() ?: "L.i:msg is null")
    }
}

fun e(msg: Any?) {
    if (Constant.DEBUG) {
        L.e(Constant.TAG, msg?.toString() ?: "L.e:msg is null")
    }
}