package com.bugull.android.ext.kotlin

class MMKt {
    val sa = 1

    open inner class MMMKt {
        open fun f() {
            println("f $sa")
        }
    }

    inner class K2 : MMMKt() {
        override fun f() {
            super.f()
            println("fk k2")
        }
    }
}

//val MMKt.mmmmmm
//    get() = object : MMKt.MMMKt() {
//        override fun f() {
//            super.f()
//            println("fff")
//        }
//    }


fun main() {
//    MMKt().K2().f()
//    val mmmKt = MMKt().mmmmmm
//    mmmKt.f()
//
//
//    val mmmm = object : MMMMMMMM(NNNN()) {
//        override fun f() {
//            super.f()
//        }
//    }
//    mmmm.f()

//    val ss = Haha()
//    ss.`test$s`()

    val s: String? = null
//    s.takeIf { false }.apply {
//        print("$this")
//    }
    s.apply {
        println("s = $s")
    }
    val haha = s?.takeIf { false }

    testTTTTTTTTTT<String> { "hello" }




}


fun <T : Any> testTTTTTTTTTT(block: () -> T) {
    println("t = ${block()}")
}

class NNNN {}

open class MMMMMMMM(val nnnn: NNNN) {
    open fun f() {
        println("fk")
    }
}
//
//class Haha {
//    fun `test$s`() {
//        println("ssss")
//    }
//
//}
//class haha{
//    fun t(){
//        println("ssss")
//    }
//}