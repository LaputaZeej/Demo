package com.bugull.android.ext.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

interface AA {
    fun test()
}

class BB {

    companion object BB : AA {
        override fun test() {
        }
    }
}

 suspend fun main() {
    BB.test()


    val job = GlobalScope.interval(0, 1) {
        println(it)
    }
//     GlobalScope.launch {
//         delay(1000*10)
//         job.cancel()
//     }

     job.join()



}

 fun CoroutineScope.interval(delay: Long, interval: Long, block:  (Long) -> Unit) =
    launch {
        delay(delay)
        var index = 0L
        while (index < Long.MAX_VALUE) {
            block(index)
            delay(interval)
            index++
        }
        throw IllegalStateException("interval end")
    }
