package com.bugull.android.ext.demo02

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

inline fun test(block: () -> Any) {
    println("test")
    block.invoke()
}

fun test01(block: Action) {
    println("test01")
    block.test()
}

fun test02(block: ActionType) {
    println("test02")
    block.invoke()
}


fun main() {
    test {

    }
    test01(Action {

    })

    test02 {

    }
    val list: List<*>? = listOf(1, 2, 3)
    if (!list.isNullOrEmpty()){
        for (i in list.indices){
            val item = list[i]
        }
    }

    if (!list.isNullOrEmpty()){
        for (i in 0 until list.size){
            val item = list[i]
        }
    }

    list?.forEachIndexed{
        index,value->
        val item = list[index]
    }

    list?.indices


    //list.get(nullSize)

}

typealias ActionType = () -> Unit

fun List<*>?.nullSize(): Int {
    return if (this.isNullOrEmpty()) {
        0
    } else {
        this.size
    }
}


@ExperimentalContracts
fun List<*>?.nullSize3(): Boolean {
    contract {
        returns(false) implies (this@nullSize3 != null)
    }

    return this == null || this.isEmpty()
}

fun <T> List<T>?.firstOrNull(): T? {
    return if (this.isNullOrEmpty()) {
        null
    } else {
        this[0]
    }
}

fun List<*>?.nullSize1(): Int = this?.size ?: 0

