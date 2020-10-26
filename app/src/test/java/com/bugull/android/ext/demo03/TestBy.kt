package com.bugull.android.ext.demo03

import java.lang.IllegalStateException

class A : B by C() {

    val c2: B by lazy { C() }
    fun funA() {
        c2.funB()
    }
}

interface B {
    fun funB()
}

class C : B {
    fun funC() {

    }

    override fun funB() {

    }
}





//fun Any?.asDefault(): Any = when (this) {
//    String::class.java -> {
//        ""
//    }
//    Integer::class.java -> {
//        0
//    }
//    else -> {
//
//    }
//}


