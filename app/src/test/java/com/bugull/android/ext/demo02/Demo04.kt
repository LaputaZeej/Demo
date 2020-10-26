package com.bugull.android.ext.demo02

fun p1():String= "t1"
fun p2() = Unit
val p3 = {"t1"}
val p4 = {}

fun t0(f: () -> Unit): Unit = f()
fun t1(f: () -> Unit): Any = f()
fun t2(f: () -> Unit): Any {
    return f()
}

fun main() {
//    t0(::p1)
    t0(::p2)

//    t1(::p1)
    t1(::p2)

//    t2(::p1)
    t2(::p2)
}



