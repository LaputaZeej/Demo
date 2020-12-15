package com.bugull.android.ext.bit

import androidx.core.os.bundleOf
import org.junit.Test

class TestBit {
    @Test
    fun test01() {
        var mapOf = mapOf("1" to 1)
        mapOf = mapOf.plus("2" to 2)
        mapOf = mapOf.plus("3" to 3)
        println(mapOf)

    }

    // 取后7位
    private fun getBit(data: Int, count: Int) {
        // 0x1101 1111
        // 0x0101 1111
    }


}