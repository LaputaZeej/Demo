package com.bugull.android.ext.demo03

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface Consumer<in T> {
    fun consumer(t: T)
}

class Consumer2<in T>(data: T) {
    val somethingToConsume: @UnsafeVariance T = data

    fun consumerOther(consumer: Consumer2<*>) {
        print(consumer.somethingToConsume)
    }
}

class Consumer3<in T>(val data: @UnsafeVariance T) {

    fun consumerOther(consumer: Consumer3<*>) {
        print(consumer.data)
    }
}

private fun testConsumer(c: Consumer<Number>) {
    c.consumer(2.0f)
    val consumer: Consumer<Int> = c
    consumer.consumer(1)
}

private fun testConsumer(c: Consumer2<*>) {
    val consumer2 = Consumer2(1)
    val somethingToConsume = c.somethingToConsume
    consumer2.consumerOther(c)
}

fun main() {
/*    val c: Consumer<Any> = object : Consumer<Any> {
        override fun consumer(t: Any) {
            println("t:$t")
        }

    }
    testConsumer(c)*/

//    val consumer2 = Consumer2<Number>(1.0f)
//    testConsumer(consumer2)
    test()

}

class TestIn<T>(var data: Array<T>, val name: String) {
    init {
        //data = arrayOf<T>()
        val sList: List<*> = listOf<String>()
        val sList2: List<T> = listOf<T>()

    }

    inline fun <reified R> createDefault(): Array<R> {
        return arrayOf<R>()
    }
}


data class Resp<T>(val data: T)
open class Book(private val name: String = "")
data class Cpp(val value: String) : Book(value)

// 因为Resp<T> T 可能为List 也可能为非List
@Suppress("UNCHECKED_CAST")
inline fun <reified T> convertList(json: String): Resp<List<T>> {
    return Gson().fromJson<T>(json, object : TypeToken<Resp<List<T>>>() {
    }.type) as Resp<List<T>>
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> convert(json: String): Resp<T> {
    return Gson().fromJson<T>(json, object : TypeToken<Resp<T>>() {
    }.type) as Resp<T>
}

fun test() {
    val mGson: Gson = Gson()
    val cpps = (1..10).map {
        Cpp("cpp:$it")
    }
    val json2 = mGson.toJson(Resp(cpps))
    val convert = convertList<Cpp>(json2)
    val convert2 = convert<List<Cpp>>(json2)
    println(convert)
    println(convert2)

    val books = (1..10).map {
        Book("name:$it")
    }
    val json = mGson.toJson(Resp(books))
    val list = mGson.fromJson<Resp<List<Book>>>(json, object : TypeToken<Resp<List<Book>>>() {
    }.type)
    println(list)

}


fun test2() {
   val s =  k1("hello")
    val invoke = s.invoke()
    val invoke1 = k2()()
}

fun <T> k1(t: T): () -> T = { b1(t) }

fun k2()/*: () -> Unit*/ = {b2()}

fun <T> b1(t: T) = t

fun b2() {

}



