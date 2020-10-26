package com.bugull.android.ext.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun working(): Boolean {
    return suspendCancellableCoroutine<Boolean> {
        var index = 0
        var flag = true
        while (flag) {
            Thread.sleep(1000)
            println("${index++}")
            if (index == 100) {
                it.resumeWith(Result.success(true))
            }
        }
        it.resumeWith(Result.success(true))

        it.invokeOnCancellation {
            flag = false
        }


    }
}