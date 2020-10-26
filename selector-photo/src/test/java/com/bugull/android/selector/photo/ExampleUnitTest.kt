package com.bugull.android.selector.photo

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
        val s = mutableListOf<Demo>(Demo(0), Demo(0), Demo(1),Demo(0),Demo(1))
        println(s.toString())
        s.sortBy {
            -it.age
        }
        println(s.toString())
    }
}

data class Demo(val age:Int)