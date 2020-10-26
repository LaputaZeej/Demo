package com.bugull.android.ext

import kotlinx.coroutines.*

fun main() = runBlocking { // t
    println("runBlocking")
    launch {
        delay(2000L)
        println("1")
    }

    coroutineScope { // 创建一个协程作用域
        launch {
            delay(5000L)
            println("2")
        }

        delay(1000L)
        println("3") // 这一行会在内嵌 launch 之前输出
    }

    println("end") // 这一行在内嵌 launch 执行完毕后才输出
}
