package com.bugull.android.ext.kotlin

import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.random.Random

val test = "hello"

class HelloWorld {
    fun test() {
        println("hello world! kotlin!")
    }
}

object Ext {
    fun hello(msg: String): Int {
        println("msg is $msg")
        return 0
    }
}

fun main() {
    val hello = HelloWorld()
    hello.test()

    var result = Ext.hello("kotlin")
}

fun test01() {
    val a = "hello"
    var b = a.length
    val c = sum1(1, 1.0f)
}

fun sum1(x: Int, y: Float) = x + y
fun sum2(x: Int, y: Double): Double = x + y

typealias Runnable = () -> Unit

fun test02() {
    val r: () -> Unit = { print("任务") }
    Thread(r).start()
}

fun test(x: Int, y: Int, cal: (Int, Int) -> Int) = cal(x, y)

fun test03() {
    val sum: (Int, Int) -> Int = { x, y -> x + y }
    test(1, 2, sum)
    val dev: (Int, Int) -> Int = { x, y -> x * y }
    test(1, 2, dev)
}

fun test04(a: Int) {
    val ex01 = 1 // 值为1
    val ex02 = -1 // 前缀操作符 值为-1
    val ex03 = 1 + 1 // 加法操作符 2
    val ex04 = listOf(1, 2, 3) // 列表表达式，返回一个list
    val ex05 = "kotlin".length // 值为6
    val ex06 = { x: Int -> x + 1 } // lambda表达式(Int)->Int
    val ex07 = fun(x: Int) { println(x) } // 匿名函数表达式 类型为(Int)->Unit
    val ex08 = if (a > 1) a else 1 // if 表达式
    val ex09 = when (a) {
        1 -> "a"
        else -> "b"
    } // when表达式
    val ex10 = try {
        1
    } catch (e: Exception) {
        2
    }
    val ex11 = 1..10
    val ex12 = "key" to "value"
}

fun test05(flag: Boolean) {
    val a = if (flag) "kotlin" else "empty"
    println(a.toUpperCase())
}


open class A {
    open fun a() {}
    fun b() {}
}

class B : A() {
    override fun a() {
        super.a()
    }
}

fun test06() {
    val a: String? = null
    val length = a?.length
    val length2 = a?.length ?: 0
    val b = null is String? // Check for instance is always 'true'
}

fun String.checkNumber(): Boolean = this.length == 6
val String.first: String
    get() = if (isEmpty()) "" else this.substring(0, 1)

fun test07() {
    val checkNumber = "kotlin".checkNumber()
    val first = "kotlin".first
}

open class Animal
class Bird(val birdName: String?) : Animal()
class Fish(val fishName: String?) : Animal()


@Suppress("IMPLICIT_CAST_TO_ANY")
fun test08(animal: Animal) {
    if (animal is Bird) {
        val name = animal.birdName
    }
    val code = Random.nextInt()
    val result1 = when (code) {
        1 -> Bird("鸟")
        2 -> Bird("鱼")
        else -> Animal()
    }
    val result2 = when (code) {
        1 -> Bird("鸟")
        2 -> Bird("鱼")
        else -> "牛"
    }

}

suspend fun loadA() = suspendCancellableCoroutine<Long> { continuation ->
    JavaCode().loadA {
        continuation.resume(Random.nextLong())
    }
}

suspend fun loadB(id: Long): String = suspendCancellableCoroutine<String> { continuation ->
    JavaCode().loadB(id) {
        continuation.resume(it)
    }
}

suspend fun test09() {
    GlobalScope.launch(Dispatchers.IO) {
        val loadA = loadA()
        val loadB = loadB(loadA)
        print(loadB)
    }
}


suspend fun loadInfo(id: String) = suspendCancellableCoroutine<String> {
    Thread.sleep(Random.nextLong(2000))
    it.resume("id=$id")
}

suspend fun test010() {
    GlobalScope.launch {
        val info1 = async { loadInfo("1") }
        val info2 = async { loadInfo("2") }
        val result = info1.await() + info2.await()
        print(result)
    }
}

fun test011(people: People?) {
    val count = people?.cat?.leg?.count ?: 0
    println("count = $count")
}

data class StudentK(val id:String,val name:String,val age:Int,val sex:Int,val address:String)

