package com.bugull.android.ext.kotlin

const val HI = "hi"
val water = "water"

fun log(msg: String) {
    println(msg)
}

fun Any.logAny() = println(this.toString())

fun main() {
    log(water)
    HI.logAny()
    Dog("小黄").logAny()
    fun eat(dog: Dog) {
        log("吃${dog.name}")
    }
    eat(Dog())
}

class Dog(val name: String = "小绿")