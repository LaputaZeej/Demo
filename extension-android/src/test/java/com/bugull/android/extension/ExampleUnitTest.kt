package com.bugull.android.extension

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

open class A {

    var hello: String = "hello"
        @JvmName(name = "getHelloKt")
        get
        @JvmName(name = "setHelloKt")
        set

    var Hello: String = "Hello"

    @JvmName(name = "h")
    fun setHello(name: String) {

    }

    companion object {
        @JvmStatic
        fun setHello1(value: String) {

        }
    }
}

class B : A() {


}