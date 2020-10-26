package com.bugull.android.ext.sam

class TestA(val ab: Int)
data class TestB(val ab: Int)

fun testA(b: TestB) = TestA(b.ab)




fun <T, R> func(t: T, convert: (T) -> R): R = convert(t)


fun main() {

    func(TestB(1)) {
        testA(it)
    }

    func<TestB, TestA>(TestB(1), ::testA)


}

