package com.bugull.android.ext

import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.reflect.typeOf

class TestInKotlin {
    fun test1(r1: Runnable, r2: Runnable) {

    }

    fun test2(r1: Any, r2: Runnable) {

    }


}

fun <T> Stream<T>.joinToString2(block: T.() -> String): String {
    return ""
    val nature = generateSequence(1) { it + 1 }
    val toList = nature.take(10).toList()
    println("自然数1-10：$toList")
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
//    Colors.A.ext(1)
//    val valueOf = Colors.valueOf("NAN")
//    println(valueOf)

   f()

    println(typeOf<List<String>>().javaClass.canonicalName  )
    val s = object :TypeToken<List<String>>(){}.rawType

}

enum class Colors {
    A, B, C, NAN;
}

fun Colors.ext(value: Int): Colors {
    return when (value) {
        1 -> Colors.A
        2 -> Colors.B
        3 -> Colors.C
        else -> /*Colors.NAN*/throw Exception("")
    }
}

fun forever1(block: () -> Unit):Unit {
    while (Random.nextBoolean()) block()
    println("Unit end 1")
    throw Exception("Error Unit end")
    println("Unit end 2")
}

fun forever2(block: () -> Unit):Nothing {
    while (Random.nextBoolean()) block()
    println("Nothing end 1")
    throw Exception("Error Nothing end") // 去掉此行，编译器会显示错误 A 'return' expression required in a function with a block body ('{...}')
    println("Nothing end 2")
    //return TODO()
}

fun f(){
    try {
        forever1 {}
    }catch (e:Exception){
        e.printStackTrace()
    }

    try {
        forever2 {}
    }catch (e:Exception){
        e.printStackTrace()
    }
}