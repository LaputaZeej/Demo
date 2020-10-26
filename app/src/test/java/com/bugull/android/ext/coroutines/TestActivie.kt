package com.bugull.android.ext.coroutines

import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

class TestCoroutine : CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.IO) {
    fun task01() {
        launch {
            while (this@TestCoroutine.isActive) {
                delay(1000)
                println("执行task01...")
            }
            println("执行task01 end")
        }
    }

    //    @Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")
//    suspend fun task03(){
//        coroutineScope {
//            throw IllegalStateException("task03 error")
//        }
//    }

    fun task02() {
        launch {
            coroutineScope {
                var index = 0
                while (this@TestCoroutine.isActive) {
                    delay(1000)
                    index++
                    if (index >= 5) {
                        val result = suspendFunc()
                        if (result) {
                            println("task02 yes")
                        } else {
                            println("task02 no")
                        }
                    }
                }
            }
        }
    }

    private suspend fun suspendFunc() = suspendCancellableCoroutine<Boolean> {
        if (Random.nextBoolean()) {
            it.resumeWithException(IllegalStateException("suspendFunc异常"))
        } else {
            it.resumeWith(Result.success(true))
        }
    }
}

fun main() = runBlocking {
    val testCoroutine = TestCoroutine()
    testCoroutine.task01()
    testCoroutine.task02()
    delay(100 * 10000)
}

