package com.bugull.android.ext.coroutines

import android.util.Log
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KProperty

val coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
suspend fun hello() {
    coroutineScope.async {
        delay(500)
        println("async")
        throw IllegalStateException("async error")
    }
}

suspend fun hello1() {
    coroutineScope {
        delay(1000)
        println("coroutineScope")
        throw IllegalStateException("hello1 error")
    }
}

suspend fun hello2() {
    coroutineScope {
        launch {
            delay(2000)
            println("hello2 ----01")
        }
        async {
            delay(1000)
            println("hello2 ----02")
        }
    }
}

suspend fun hello3() {
    supervisorScope {
        delay(1000)
        println("coroutineScope")
        throw IllegalStateException("hello1 error")
    }
}

fun main1() {
    coroutineScope.launch {
        hello()
        try {
            coroutineScope.launch {
                hello1()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        hello2()
//        coroutineScope.launch {
        hello3()
//        }

        delay(2000)
        println("end")
    }
    Thread.sleep(10000)


}

class A {
    val a: String by weakLazy { "aa" }
}

fun <T> weakLazy(lock: Any? = null, valueBuilder: () -> T) = TestCoroutines(valueBuilder)

class TestCoroutines<T>(val valueBuilder: () -> T) {
    operator fun getValue(a: A, property: KProperty<*>): T {
        return valueBuilder()
    }
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = valueBuilder()
}




