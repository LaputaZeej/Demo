package com.bugull.android.ext.demo02

fun foo(f: () -> Unit) {

}

fun bar(f: () -> String) {
    // foo(f)
    foo {
        f()
    }
}

fun main() {
    val s1 = demo(::b)
    //val s2 = demo(::a)
    val s3 = demo2(::b)
//    val s4 = demo2(::a)
//    val s5 = demo2(c)
    val s6 = demo2(d)

    println("s1 =$s1")
    println("s3 =$s3")
//    println("s4 =$s4")
//    println("s5 =$s5")
    println("s6 =$s6")

    val s7 = demo2_s3(::b)
//    val s8 = demo2_s3(::a)
//    val s9 = demo2_s4(::a)
    val s10 = demo2_s4(::b)
}

fun demo(f: () -> Unit) = f()
fun demo2(f: () -> Unit): Any = f()

fun demo2_s3(f: () -> Unit): Any {
    f()
    return Unit
}

fun demo2_s4(f: () -> Unit): Any {
    return f()
}

fun demo2_02(f: () -> Unit) {
    f()
}

fun a(): String = "a"
fun b() {}
val c: () -> String = { "c" }
val d: () -> Unit = { }

fun demoK0(f: () -> Unit) {
    f()
}

fun demoK1(f: () -> Unit): Unit {
    return f()
}

fun demoK2(f: () -> Unit): Any {
    return f()
}


//fun demo2(f: () -> String) = f()


