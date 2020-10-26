package com.bugull.android.ext

import com.bugull.android.ext.chars.serviceUrl
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test1() {

        println(createTime(1, 2))
        println(createTime(11, 22))

    }

    fun createTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }

    @Test
    fun addition_isCorrect() {
        println((1 shl 21) - 2)
        println((Math.pow(2.0, 21.0)))
    }

    @Test
    fun testException() {
        try {

            when (val result = createString(11)) {
                null -> {
                    println("null")
                }
                else -> {
                    println(result)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()

        }

        Thread.sleep(3000)
        println("end of testException")

    }

    private fun createString(a: Int): String? = when (a) {
        1 -> "yes"
        else -> throw IllegalArgumentException("错啦")
    }

    @Test
    fun testInterface() {
        val x = X<String>()
        x.test("X") {}
        x.test("X", {})
        x.test("X", N {})
        x.test("X", N<String> {})
        x.testSuper("X", N<String> {})
        x.testSuper("X") {}


        val y = Y()
//        y.test("Y"){
//
//        }
        y.test<String>("Y", N {

        })
        y.test<String>("Y", {

        })
        y.test("Y", N<String> {

        })
        y.test<String>("Y") {

        }

    }

    @Test
    fun testInterface1() {
        val a = A<String>()
        val b = B()
        a.test("A", object : M<String> {
            override fun onChanged(t: String) {

            }
        })

        b.test("B", object : M<String> {
            override fun onChanged(t: String) {
                "bbb"
            }
        })

        //        a.test("a"){
//
//        }

//        b.test<String>("B"){
//
//        }


        val c = C<String>()
        val d = D()
        c.test("c", N {
            //todo
        })

        d.test<String>("d", N {

        })
    }


    @Test
    fun test01() {
//        val runnable = Runnable {  }
//        val test = com.bugull.android.ext.Test()
//        test.test1(runnable, Runnable{})
//        test.test1(runnable, {
//
//        })
//        test.test2(runnable, Runnable {  })
//        test.test2(runnable){
//
//        }
//
//       val testInKotlin =  TestInKotlin()
//        testInKotlin.test1(runnable, Runnable{})
//        testInKotlin.test2(runnable,{})
//        testInKotlin.test2(runnable, Runnable{})
//        testInKotlin.test2(runnable){}
//        testInKotlin.test3{}
//        testInKotlin.test3(Runnable {  })
//        testInKotlin.test3({})

        when (val i = 1 as Byte) {
            1.toByte() -> 1
            else -> 0
        }

        tt {
            throw Exception()
        }

    }

    fun tt(s: () -> (Unit)) {
        s()
    }

    @Test
    fun test001() {
        println("result = " + "https://www.baidu.com?id=1".serviceUrl())
    }
}


interface M<T> {
    fun onChanged(t: T)
}

//typealias M<T> = (T)->Unit

class A<T> {
    fun test(tag: String, m: M<T>) {
        // ...
    }
}

class B {
    fun <T> test(tag: String, m: M<T>) {
        // ...
    }
}

class C<T> {
    fun test(tag: String, m: N<T>) {
        // ...
    }
}

class D {
    fun <T> test(tag: String, m: N<T>) {
        // ...
    }
}




