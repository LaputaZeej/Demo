package com.bugull.android.ext.functions

sealed class Either<out A, out B> {
    class Left<A, B>(val value: A) : Either<A, B>()
    class Right<A, B>(val value: B) : Either<A, B>()
}

// 注意out产生的效果
sealed class Either2<out A,out B>{
    class Left<Error>(val value: Error) : Either2<Error, Nothing>()
    class Right<T>(val value: T) : Either2<Nothing, T>()
}