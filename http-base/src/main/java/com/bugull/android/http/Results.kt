package com.bugull.android.http

import android.util.Log


class Result<out T> internal constructor(
    private val value: Any?
) : java.io.Serializable {

    val isSuccess: Boolean get() = value !is Failure

    val isFailure: Boolean
        get() {
            return value is Failure
        }


    fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }


    companion object {

        fun <T> success(value: T?): Result<T> =
            Result(value)

        fun <T> failure(exception: Throwable?): Result<T> =
            Result(createFailure(exception))
    }

    class Failure(
        @JvmField
        val exception: Throwable?
    ) : java.io.Serializable {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

internal fun createFailure(exception: Throwable?): Any =
        Result.Failure(exception)


//fun <T>createFailures(exception: Throwable?): Result<T> =
//    Result(exception)


