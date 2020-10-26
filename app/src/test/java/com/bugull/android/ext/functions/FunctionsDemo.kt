package com.bugull.android.ext.functions

import java.lang.Exception
import kotlin.random.Random

fun main() {
    val result: Either<Throwable, String> = randomEither()
    println(result)

    val result2: Either2<Throwable, String> = randomEither2()
    println(result2)

}

private fun randomEither() =
    if (Random.nextBoolean()) Either.Left<Throwable, String>(Exception("left is throwable"))
    else Either.Right<Throwable, String>("hello")

private fun randomEither2() =
    if (Random.nextBoolean()) Either2.Left<Throwable>(Exception("left is throwable"))
    else Either2.Right<String>("hello")