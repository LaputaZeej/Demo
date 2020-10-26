package com.bugull.android.ext.day20200723

import java.lang.IllegalStateException
import kotlin.reflect.KProperty


class B {
    val name: String? by defaultLazy()
}

fun main() {
    val s: String? =""
    println("s $s")
    println(B().name)
}

inline fun <reified T> defaultLazy() = DefaultLazy<T>(T::class.java)

class DefaultLazy<T>(private val clz: Class<*>) {
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (clz) {
            String::class.java -> {
                "默认值"
            }
            else -> {
                throw IllegalStateException("不支持默认类型")
            }
        } as T
    }
}


data class BookResp(val name: String?, val time: Long?, val flag: Int?)

data class BookData(val name: String, val time: Long, val read: Boolean)

fun bookData(resp: BookResp) = resp.run {
    BookData(
        name = name.asDefault(),
        time = time.asDefault(),
        read = flag == 1
    )
}

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T : Any> T?.asDefault(default: T? = null): T {
    return when (T::class.java) {
        String::class.java -> {
            default ?: "默认值"
        }
        Integer::class.java -> {
            default ?: -1
        }
        else -> {
            throw IllegalStateException("不支持此类型的默认值")
        }
    } as T
}

fun main1() {
    val s: String? = null
    println(s.asDefault())
    val s2: Int? = null
    println(s2.asDefault())

}