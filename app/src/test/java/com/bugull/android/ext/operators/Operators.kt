package com.bugull.android.ext.operators

import android.view.View

interface Ktor<T> {

    fun test(t: T)
//    companion object {
//        inline operator fun  <reified T> invoke():Ktor<T>{
//            println("factory operator invoke")
//            return when(T::class.java){
//                KK::class.java-> KK()
//                else->TT()
//            }
//        }
//    }
}

//inline fun<reified T> Ktor():Ktor<T>{
//    println("顶层方法")
//    return when(T::class.java){
//        KK::class.java-> KK<T>()
//        else->TT<T>()
//    }
//}

class KK<T> : Ktor<T> {
    override fun test(t: T) {
    }

    operator fun invoke() {
        println("ktor")
    }
}

class TT<T> : Ktor<T> {
    override fun test(t: T) {
    }
}

//var xx :String? = null

fun main() {
//    val ktor1 = Ktor<String>()
//    val ktor2 = Ktor.Companion<String>()
//    println("$ktor1")
//    println("$ktor2")

//    val kk = Test()
//    kk.invoke()
//    kk()
    val test = Test()
    test(1)

    val a: A = { println("lambda A") }
    a.invoke(1)
    a.invoke()
    a.test("hello")
    a()
    a(1)
    a.test("hello")

    val xxx = XXX()

    var xx :String? = null
    if (!xx.isNullOrEmpty()) {
        xx.length
    }
}

typealias A = (Int) -> Unit

operator fun A.invoke(): Int {
    println("A()")
    return 1
}
 fun A.test(type:String): Int {
    println("A(String)")
    return 1
}

class Test() {
    var a = 0
    operator fun invoke(s: Int, x: Float = 0f) {
        println("test $s $x")
    }

    companion object {
        operator fun invoke(): Test {
            println("companion object")
            return Test()
        }
    }
}