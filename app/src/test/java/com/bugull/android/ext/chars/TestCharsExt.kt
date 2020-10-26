package com.bugull.android.ext.chars

inline fun <reified T : Any> T.toStr() = with(this) {
    println("${T::class.java}") // class java.lang.Character
    println("${T::class}") // class java.lang.Character (Kotlin reflection is not available)
    when {
        T::class.java == Character::class.java -> "char"  // ok
        T::class == Char::class -> "char"                 // ok
        this is Char -> "char"                            // ok
        this is Character -> "is char"                    // ok 编译器建议换成上面写法
        else -> "is other"
    }
}


