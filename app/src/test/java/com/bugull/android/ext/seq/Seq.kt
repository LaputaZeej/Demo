package com.bugull.android.ext.seq

val 斐波那契数列 = sequence<Long> {
    yield(0L)
    var current = 0L
    var next = 1L
    while (true) {
        yield(next)
        val temp = current + next
        current = next
        next = temp
    }
}

fun main() {
    斐波那契数列.take(10).forEach {
        print("$it ")
    }
    println("-----")
    斐波那契数列.take(1).forEach {
        print("$it ")
    }
    println("-----")
    斐波那契数列.take(0).forEach {
        print("$it ")
    }
}