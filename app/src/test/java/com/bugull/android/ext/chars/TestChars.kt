package com.bugull.android.ext.chars

import android.net.Uri
import java.net.URI
import kotlin.coroutines.Continuation
import kotlin.jvm.internal.Intrinsics
import kotlin.random.Random

class TestChars {
    fun test001() {
        val c: Char? = 'a'
        c?.run {
            println("1->${c::class.java}") // char
            println("2->${Character::class.java}") // class java.lang.Character
            println("3->${c.javaClass}") // char
            println("4->${c::class}") // char (Kotlin reflection is not available)
            println("5->${Character::class}") // class java.lang.Character (Kotlin reflection is not available)
            println("6->${Char::class}") // char (Kotlin reflection is not available)
            println("7->${c::class.java.equals(Character::class.java)}") // false 编译器提示更改成下面格式但是报错。--！
//            println(c::class.java == Character::class.java) // Operator '==' cannot be applied to 'Class<out Char>' and 'Class<Character>'
            println("8->${c.javaClass == Character::class.java}") // false
            println("9->${c::class == Character::class}") // true
            println(c.toStr()) // is char 用泛型就可以用==
        }

    }
}

fun main() {
//    TestChars().test001()
//    println("https://www.baidu.com?id=1".serviceUrl())
//    println("http://baidu.com".serviceUrl())
    TJ().t()

    Intrinsics.areEqual(kotlin.coroutines.Continuation::class.java, Continuation::class )
}


fun String?.serviceUrl(): String? = this?.let {
    println("it = $it")
    val parse = Uri.parse(it)
    println("uri = $parse")
    val buildUpon = parse?.buildUpon()
    println("buildUpon = $buildUpon")
    val appendQueryParameter = buildUpon?.appendQueryParameter("userid", "zeej")
    println("appendQueryParameter = $appendQueryParameter")
    val build = appendQueryParameter?.build()
    println("build = $build")
    build?.toString() ?: "1.null"
} ?: "2"

fun String?.serviceUrl2(): String? = this?.let {
    println("it = $it")
    val s = URI.create(it)

    val parse = Uri.parse(it)
    println("uri = $parse")
    val buildUpon = parse?.buildUpon()
    println("buildUpon = $buildUpon")
    val appendQueryParameter = buildUpon?.appendQueryParameter("userid", "zeej")
    println("appendQueryParameter = $appendQueryParameter")
    val build = appendQueryParameter?.build()
    println("build = $build")
    build?.toString() ?: "1.null"
} ?: "2"


fun test(){
//   for(i in 0..1.0 step 0.1){
//
//   }

    listOf(1,2).iterator()


}



